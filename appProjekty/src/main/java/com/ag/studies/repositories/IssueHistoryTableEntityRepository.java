package com.ag.studies.repositories;

import com.ag.studies.models.IssueHistoryTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueHistoryTableEntityRepository extends JpaRepository<IssueHistoryTableEntity, Long> {
    List<IssueHistoryTableEntity> findByIssueIdOrderByDateModifiedDesc(Long issueId);

}