package com.ag.studies.repositories;

import com.ag.studies.models.TasksTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TasksTableEntityRepository extends JpaRepository<TasksTableEntity, Long> {
    long countByProjectId(Long projectId);

    long countByProjectIdAndIsDone(Long projectId, Boolean i);

    List<TasksTableEntity> findByProjectId(Long projectId);

    int countByDeadlineAfter(LocalDate currentDate);
}