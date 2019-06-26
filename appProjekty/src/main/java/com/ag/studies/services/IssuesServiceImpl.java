package com.ag.studies.services;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.IssueHistoryTableEntity;
import com.ag.studies.models.IssueTableEntity;
import com.ag.studies.models.ProjectTableEntity;
import com.ag.studies.repositories.IssueHistoryTableEntityRepository;
import com.ag.studies.repositories.IssueTableEntityRepository;
import com.ag.studies.repositories.ProjectTableEntityRepository;
import com.ag.studies.repositories.UserTableEntityRepository;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

@Service
public class IssuesServiceImpl implements IssuesService {
    @Autowired
    private IssueTableEntityRepository issuetableentityRepository;
    @Autowired
    private IssueHistoryTableEntityRepository issuehistorytableentityRepository;
    @Autowired
    private UserTableEntityRepository usertableentityRepository;
    @Autowired
    private ProjectTableEntityRepository projecttableentityRepository;

    public static<T> List<T> getLastElements(List<T> list, int limit){
        return Lists.reverse(list).stream().limit(limit).collect(toList());
    }

    @Override
    public List<IssueTableEntity> findWithLimit(Integer limit) {
        return getLastElements(issuetableentityRepository.findAllByOrderByLastUpdated(), limit);
    }

    public IssueTableEntity addIssue(Long userId, Long projectId, String title , String inscription) throws EntityNotFoundException  {
        if(projecttableentityRepository.findById(projectId)==null) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        IssueTableEntity newIssue = new IssueTableEntity();
        newIssue.setProjectId(projectId);
        newIssue.setUserId(userId);
        newIssue.setTitle(title);
        newIssue.setStatus("Zarejestrowana");
        java.util.Date date= new java.util.Date();
        newIssue.setLastUpdated(new Timestamp(date.getTime()));
        newIssue.setProjectTableByProjectId(projecttableentityRepository.getOne(newIssue.getProjectId()));
        newIssue.setUserTableByUserId(usertableentityRepository.getOne(userId));
        try {
            issuetableentityRepository.save(newIssue);
            addIssueModification(newIssue.getIssueId(), userId, inscription);
            out.println("IssId: " + newIssue.getIssueId()
                    + "ProjectId: " + newIssue.getProjectId()
                    + "UserId: " + newIssue.getUserId()
                    + "Title: " + newIssue.getTitle()
                    + "Status: " + newIssue.getStatus()
                    + "LastUpdated: " + newIssue.getLastUpdated()
                    + "byProjectId: " + newIssue.getProjectTableByProjectId()
                    + "byUserId: " + newIssue.getUserTableByUserId());
        }catch (DataIntegrityViolationException ex){
            addIssue(userId, projectId, title, inscription);
        }
        return newIssue;
    }

    @Override
    public List<IssueHistoryTableEntity> findByIssueId(Long issueId) throws EntityNotFoundException {
        List<IssueHistoryTableEntity> result = issuehistorytableentityRepository.findByIssueIdOrderByDateModifiedDesc(issueId);
        if (result.isEmpty()) throw new EntityNotFoundException(IssueHistoryTableEntity.class, "issueId", issueId.toString());
        return result;
    }

    @Override
    public List<IssueHistory> cutIssueHistory(List<IssueHistoryTableEntity> all){
        List<IssueHistory> issueHistoryList = new ArrayList<>();
        for(IssueHistoryTableEntity iss : all){
            issueHistoryList.add(new IssueHistory(iss.getIssueHistoryId(), iss.getIssueId(), iss.getUserId(), iss.getOldValue(), iss.getNewValue(), iss.getDateModified()));
        }
        return issueHistoryList;
    }

    private Long issueHistoryId;
    private Long issueId;
    private Long userId;
    private String oldValue;
    private String newValue;
    private Timestamp dateModified;

    @Override
    public List<IssueTableEntity> findByProjectId(Long projectId) throws EntityNotFoundException{
    List<IssueTableEntity> result = issuetableentityRepository.findByProjectId(projectId);
        if (result.isEmpty()) throw new EntityNotFoundException(IssueTableEntity.class, "projectId", projectId.toString());
        return result;
    }
    @Override
    public List<IssueTableEntity> findAll() {
        return issuetableentityRepository.findAll();
    }

    @Override
    public void delete(Long id)  throws EntityNotFoundException {
        if(!issuetableentityRepository.findById(id).isPresent()) throw new EntityNotFoundException(IssueTableEntity.class, "id", id.toString());
        for(IssueHistoryTableEntity issHistory : issuehistorytableentityRepository.findByIssueId(id)){
            issuehistorytableentityRepository.deleteById(issHistory.getIssueHistoryId());
        }
        issuetableentityRepository.deleteById(id);
    }
    @Override
    public void deleteByProjectId(Long id)  throws EntityNotFoundException {
        if(issuetableentityRepository.findByProjectId(id)==null) throw new EntityNotFoundException(IssueTableEntity.class, "projectId", id.toString());
        for(IssueTableEntity iss : issuetableentityRepository.findByProjectId(id)){
            for(IssueHistoryTableEntity issHistory : issuehistorytableentityRepository.findByIssueId(iss.getIssueId())){
                issuehistorytableentityRepository.deleteById(issHistory.getIssueHistoryId());
            }
            issuetableentityRepository.deleteById(iss.getIssueId());
        }
    }

    @Override
    public void deleteAll() {
        issuetableentityRepository.deleteAll();
    }

    @Override
    public long count() {
        return issuetableentityRepository.count();
    }

    @Override
    public IssueTableEntity updateIssue(Long id, String new_type) {
        IssueTableEntity updatedIssue = issuetableentityRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntity(id));
        updatedIssue.setStatus(new_type);
        return issuetableentityRepository.save(updatedIssue);
    }

    @Override
    public void addIssueModification(Long issId, Long ussId, String newIssueValue)  throws EntityNotFoundException {
        if(!issuetableentityRepository.findById(issId).isPresent()) throw new EntityNotFoundException(IssueTableEntity.class, "issueId", issId.toString());
        IssueHistoryTableEntity newHistory = new IssueHistoryTableEntity();
        newHistory.setNewValue(newIssueValue);
        newHistory.setIssueId(issId);
        newHistory.setUserId(ussId);
        List<IssueHistoryTableEntity> Histories = new ArrayList<IssueHistoryTableEntity>();
        Histories = issuehistorytableentityRepository.findByIssueIdOrderByDateModifiedDesc(issId);
        if(Histories.size() == 0) newHistory.setOldValue("0");
        else newHistory.setOldValue(Histories.get(0).getNewValue());
        newHistory.setIssueTableByIssueId(issuetableentityRepository.getOne(issId));
        newHistory.setUserTableByUserId(usertableentityRepository.getOne(ussId));
        try {
            issuehistorytableentityRepository.save(newHistory);
        }catch (DataIntegrityViolationException ex){
            addIssueModification(issId, ussId, newIssueValue);
        }
    }

    @Override
    public List<Issue> cutIssues(List<IssueTableEntity> all) {
        List<Issue> issueList = new ArrayList<>();
        for(IssueTableEntity iss : all){
            issueList.add(new Issue(iss.getIssueId(), iss.getProjectId(), iss.getUserId(), iss.getTitle(), iss.getStatus(), iss.getDateSubmitted(), iss.getLastUpdated()));
        }
        return issueList;
    }

    private class NotFoundEntity extends RuntimeException  {
        public NotFoundEntity(Long id) {
            super("Could not find the issue with given id" + id );
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Issue{
        private Long issueId;
        private Long projectId;
        private Long userId;
        private String title;
        private String status;
        private Timestamp dateSubmitted;
        private Timestamp lastUpdated;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IssueHistory {
        private Long issueHistoryId;
        private Long issueId;
        private Long userId;
        private String oldValue;
        private String newValue;
        private Timestamp dateModified;
    }
}
