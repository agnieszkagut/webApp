package com.ag.studies.services;

import com.ag.studies.models.IssueHistoryTableEntity;
import com.ag.studies.models.IssueTableEntity;
import com.ag.studies.repositories.IssueHistoryTableEntityRepository;
import com.ag.studies.repositories.IssueTableEntityRepository;
import com.ag.studies.repositories.ProjectTableEntityRepository;
import com.ag.studies.repositories.UserTableEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

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

    @Override
    public List<IssueTableEntity> findLastThree() {
        List<IssueTableEntity> ListOfThree = new ArrayList<>();
        List<IssueTableEntity> ListOfAll = issuetableentityRepository.findAllByOrderByLastUpdated();
        if(ListOfAll.size() > 0)ListOfThree.add(ListOfAll.get(ListOfAll.size() - 1));
        if(ListOfAll.size() > 1)ListOfThree.add(ListOfAll.get(ListOfAll.size() - 2));
        if(ListOfAll.size() > 2)ListOfThree.add(ListOfAll.get(ListOfAll.size() - 3));
        return ListOfThree;
    }
    @Override
    public List<IssueTableEntity> findLastThreeByStatus(String status) {
        List<IssueTableEntity> ListOfThree = new ArrayList<>();
        List<IssueTableEntity> ListOfAll = issuetableentityRepository.findByStatusOrderByLastUpdated(status);
        if(ListOfAll.size() > 0)ListOfThree.add(ListOfAll.get(ListOfAll.size() - 1));
        if(ListOfAll.size() > 1)ListOfThree.add(ListOfAll.get(ListOfAll.size() - 2));
        if(ListOfAll.size() > 2)ListOfThree.add(ListOfAll.get(ListOfAll.size() - 3));
        return ListOfThree;
    }

    public IssueTableEntity addIssue(Long userId, String projectTitle, String title , String inscription) {
        IssueTableEntity newIssue = new IssueTableEntity();
        newIssue.setIssueId(issuetableentityRepository.count()+1);
        newIssue.setProjectId(projecttableentityRepository.findByNameEquals(projectTitle).getProjectId());
        newIssue.setUserId(userId);
        newIssue.setTitle(title);
        newIssue.setStatus("Zarejestrowana");
        java.util.Date date= new java.util.Date();
        newIssue.setLastUpdated(new Timestamp(date.getTime()));
        newIssue.setProjectTableByProjectId(projecttableentityRepository.getOne(newIssue.getProjectId()));
        newIssue.setUserTableByUserId(usertableentityRepository.getOne(userId));
        issuetableentityRepository.save(newIssue);
        addIssueModification(newIssue.getIssueId(), userId, inscription);
        out.println("IssId: "+newIssue.getIssueId()
                +"ProjectId: "+newIssue.getProjectId()
                +"UserId: "+newIssue.getUserId()
                +"Title: "+newIssue.getTitle()
                +"Status: "+newIssue.getStatus()
                +"LastUpdated: "+newIssue.getLastUpdated()
                +"byProjectId: "+newIssue.getProjectTableByProjectId()
                +"byUserId: "+newIssue.getUserTableByUserId());
        return newIssue;
    }

    @Override
    public List<IssueHistoryTableEntity> findByIssueId(Long issueId) {
        return issuehistorytableentityRepository.findByIssueIdOrderByDateModifiedDesc(issueId);
    }


    @Override
    public List<IssueTableEntity> findByProjectId(Long id) {
        return issuetableentityRepository.findByProjectId(id);
    }
    @Override
    public List<IssueTableEntity> findByProjectIdAndByType(Long id, String status) {
        return issuetableentityRepository.findByProjectIdAndStatus(id, status);
    }
    @Override
    public List<IssueTableEntity> findAll() {
        return issuetableentityRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        issuetableentityRepository.deleteById(id);
    }
    @Override
    public void deleteByProjectId(Long id) {
        issuetableentityRepository.deleteByProjectId(id);
    }
    @Override
    public void deleteByProjectIdAndByType(Long id, String status) {
        issuetableentityRepository.deleteByProjectIdAndStatus(id, status);
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
    public long countByProjectId(Long id) {
        return issuetableentityRepository.countByProjectId(id);
    }
    @Override
    public long countByProjectIdAndByType(Long id, String status) {
        return issuetableentityRepository.countByProjectIdAndStatus(id, status);
    }

    @Override
    public IssueTableEntity updateIssue(Long id, String new_type) {
        IssueTableEntity updatedIssue = issuetableentityRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntity(id));;
        updatedIssue.setStatus(new_type);
        return issuetableentityRepository.save(updatedIssue);
    }

    @Override
    public void addIssueModification(Long issId, Long ussId, String newIssueValue) {
        IssueHistoryTableEntity newHistory = new IssueHistoryTableEntity();
        newHistory.setIssueHistoryId(issuehistorytableentityRepository.count()+1);
        newHistory.setNewValue(newIssueValue);
        newHistory.setIssueId(issId);
        newHistory.setUserId(ussId);
        List<IssueHistoryTableEntity> Histories = new ArrayList<IssueHistoryTableEntity>();
        Histories = issuehistorytableentityRepository.findByIssueIdOrderByDateModifiedDesc(issId);
        if(Histories.size() == 0) newHistory.setOldValue("0");
        else newHistory.setOldValue(Histories.get(0).getNewValue());
        newHistory.setIssueTableByIssueId(issuetableentityRepository.getOne(issId));
        newHistory.setUserTableByUserId(usertableentityRepository.getOne(ussId));
        issuehistorytableentityRepository.save(newHistory);
    }

    private class NotFoundEntity extends RuntimeException  {
        public NotFoundEntity(Long id) {
            super("Could not find the issue with given id" + id );
        }
    }
}
