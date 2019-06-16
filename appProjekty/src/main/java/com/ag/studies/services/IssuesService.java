package com.ag.studies.services;

import com.ag.studies.models.IssueHistoryTableEntity;
import com.ag.studies.models.IssueTableEntity;

import java.util.List;

public interface IssuesService {
    List<IssueTableEntity> findLastThree();
    List<IssueTableEntity> findLastThreeByStatus(String status);

    IssueTableEntity addIssue(Long userId, String projectTitle, String title, String inscription);

    List<IssueHistoryTableEntity> findByIssueId(Long id);
    List<IssueTableEntity> findByProjectId(Long id);
    List<IssueTableEntity> findByProjectIdAndByType(Long id, String type);
    List<IssueTableEntity> findAll();

    void delete(Long id);
    void deleteByProjectId(Long id);
    void deleteByProjectIdAndByType(Long id, String type);
    void deleteAll();

    long count();
    long countByProjectId(Long id);
    long countByProjectIdAndByType(Long id, String type);

    IssueTableEntity updateIssue(Long id, String new_type);

    void addIssueModification(Long issId, Long ussId, String newIssueModification);

}
