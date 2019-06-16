package com.ag.studies.repositories;

import com.ag.studies.models.ProjectTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTableEntityRepository extends JpaRepository<ProjectTableEntity, Long> {
    long countByStatus(String status);

    ProjectTableEntity findOneByProjectId(Long projectId);

    ProjectTableEntity findByNameEquals(String project);
}