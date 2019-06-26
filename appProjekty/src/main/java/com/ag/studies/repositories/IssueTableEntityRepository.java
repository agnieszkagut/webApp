package com.ag.studies.repositories;

import com.ag.studies.models.IssueHistoryTableEntity;
import com.ag.studies.models.IssueTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueTableEntityRepository extends JpaRepository<IssueTableEntity, Long> {
    List<IssueTableEntity> findByProjectId(Long id);

    void deleteByProjectId(Long projectIid);

    long countByProjectId(Long id);

    List<IssueTableEntity> findByProjectIdAndStatus(Long id, String status);

    void deleteByProjectIdAndStatus(Long id, String status);

    long countByProjectIdAndStatus(Long id, String status);

    List<IssueTableEntity> findAllByOrderByLastUpdated();

    List<IssueTableEntity> findByStatusOrderByLastUpdated(String status);

}