package com.ag.studies.services;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.IssueHistoryTableEntity;
import com.ag.studies.models.IssueTableEntity;
import com.ag.studies.models.ProjectTableEntity;
import com.ag.studies.models.UserTableEntity;
import com.ag.studies.repositories.IssueHistoryTableEntityRepository;
import com.ag.studies.repositories.IssueTableEntityRepository;
import com.ag.studies.repositories.ProjectTableEntityRepository;
import com.ag.studies.repositories.UserTableEntityRepository;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class IssuesServiceImpl implements IssuesService {
    @Autowired
    private IssueTableEntityRepository issueTableEntityRepository;
    @Autowired
    private IssueHistoryTableEntityRepository issueHistoryTableEntityRepository;
    @Autowired
    private UserTableEntityRepository userTableEntityRepository;
    @Autowired
    private ProjectTableEntityRepository projectTableEntityRepository;

    public static<T> List<T> getLastElements(List<T> list, int limit){
        return Lists.reverse(list).stream().limit(limit).collect(toList());
    }

    @Override
    public List<IssueTableEntity> findWithLimit(Integer limit) {
        return getLastElements(issueTableEntityRepository.findAllByOrderByLastUpdated(), limit);
    }

    public IssueTableEntity addIssue(Long userId, Long projectId, String title , String inscription) throws EntityNotFoundException  {
        if(projectTableEntityRepository.findById(projectId)==null) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        if(userTableEntityRepository.findById(userId)==null) throw new EntityNotFoundException(UserTableEntity.class, "userId", userId.toString());
        IssueTableEntity newIssue = new IssueTableEntity();
        newIssue.setProjectId(projectId);
        newIssue.setUserId(userId);
        newIssue.setTitle(title);
        newIssue.setStatus("Zarejestrowana");
        java.util.Date date= new java.util.Date();
        newIssue.setLastUpdated(new Timestamp(date.getTime()));
        newIssue.setProjectTableByProjectId(projectTableEntityRepository.getOne(newIssue.getProjectId()));
        newIssue.setUserTableByUserId(userTableEntityRepository.getOne(userId));
        issueTableEntityRepository.save(newIssue);
        addIssueModification(newIssue.getIssueId(), userId, inscription);
        return newIssue;
    }

    @Override
    public List<IssueHistoryTableEntity> findByIssueId(Long issueId) throws EntityNotFoundException {
        List<IssueHistoryTableEntity> result = issueHistoryTableEntityRepository.findByIssueIdOrderByDateModifiedDesc(issueId);
        if (result.isEmpty()) throw new EntityNotFoundException(IssueHistoryTableEntity.class, "issueId", issueId.toString());
        return result;
    }

    private Long issueHistoryId;
    private Long issueId;
    private Long userId;
    private String oldValue;
    private String newValue;
    private Timestamp dateModified;

    @Override
    public List<IssueTableEntity> findByProjectId(Long projectId) throws EntityNotFoundException{
    List<IssueTableEntity> result = issueTableEntityRepository.findByProjectId(projectId);
        if (result.isEmpty()) throw new EntityNotFoundException(IssueTableEntity.class, "projectId", projectId.toString());
        return result;
    }
    @Override
    public List<IssueTableEntity> findAll() {
        return issueTableEntityRepository.findAll();
    }

    @Override
    public void delete(Long id)  throws EntityNotFoundException {
        if(!issueTableEntityRepository.findById(id).isPresent()) throw new EntityNotFoundException(IssueTableEntity.class, "id", id.toString());
        for(IssueHistoryTableEntity issHistory : issueHistoryTableEntityRepository.findByIssueId(id)){
            issueHistoryTableEntityRepository.deleteById(issHistory.getIssueHistoryId());
        }
        issueTableEntityRepository.deleteById(id);
    }
    @Override
    public void deleteByProjectId(Long id)  throws EntityNotFoundException {
        if(issueTableEntityRepository.findByProjectId(id)==null) throw new EntityNotFoundException(IssueTableEntity.class, "projectId", id.toString());
        for(IssueTableEntity iss : issueTableEntityRepository.findByProjectId(id)){
            for(IssueHistoryTableEntity issHistory : issueHistoryTableEntityRepository.findByIssueId(iss.getIssueId())){
                issueHistoryTableEntityRepository.deleteById(issHistory.getIssueHistoryId());
            }
            issueTableEntityRepository.deleteById(iss.getIssueId());
        }
    }

    @Override
    public void deleteAll() {
        issueTableEntityRepository.deleteAll();
    }

    @Override
    public long count() {
        return issueTableEntityRepository.count();
    }

    @Override
    public IssueTableEntity updateIssue(Long id, String new_type) {
        IssueTableEntity updatedIssue = issueTableEntityRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntity(id));
        updatedIssue.setStatus(new_type);
        return issueTableEntityRepository.save(updatedIssue);
    }

    @Override
    public void addIssueModification(Long issId, Long ussId, String newIssueValue)  throws EntityNotFoundException {
        if(!issueTableEntityRepository.findById(issId).isPresent()) throw new EntityNotFoundException(IssueTableEntity.class, "issueId", issId.toString());
        IssueHistoryTableEntity newHistory = new IssueHistoryTableEntity();
        newHistory.setNewValue(newIssueValue);
        newHistory.setIssueId(issId);
        newHistory.setUserId(ussId);
        List<IssueHistoryTableEntity> Histories = new ArrayList<IssueHistoryTableEntity>();
        Histories = issueHistoryTableEntityRepository.findByIssueIdOrderByDateModifiedDesc(issId);
        if(Histories.size() == 0) newHistory.setOldValue("0");
        else newHistory.setOldValue(Histories.get(0).getNewValue());
        newHistory.setIssueTableByIssueId(issueTableEntityRepository.getOne(issId));
        newHistory.setUserTableByUserId(userTableEntityRepository.getOne(ussId));
        issueHistoryTableEntityRepository.save(newHistory);
    }

    private class NotFoundEntity extends RuntimeException  {
        public NotFoundEntity(Long id) {
            super("Could not find the issue with given id" + id );
        }
    }



}
