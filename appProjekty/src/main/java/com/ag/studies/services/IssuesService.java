package com.ag.studies.services;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.IssueHistoryTableEntity;
import com.ag.studies.models.IssueTableEntity;

import java.util.List;

public interface IssuesService {
    List<IssueTableEntity> findWithLimit(Integer limit);

    IssueTableEntity addIssue(Long userId, Long projectId, String title, String inscription) throws EntityNotFoundException;

    List<IssueHistoryTableEntity> findByIssueId(Long id) throws EntityNotFoundException;
    List<IssueTableEntity> findByProjectId(Long id) throws EntityNotFoundException;
    List<IssueTableEntity> findAll();

    void delete(Long id) throws EntityNotFoundException;
    void deleteByProjectId(Long id) throws EntityNotFoundException;
    void deleteAll();

    long count();

    IssueTableEntity updateIssue(Long id, String new_type);

    void addIssueModification(Long issId, Long ussId, String newIssueModification) throws EntityNotFoundException;
}
