package com.ag.studies.repositories;

import com.ag.studies.models.CalendarEntriesTableEntity;
import com.ag.studies.models.ConfigTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConfigTableEntityRepository extends JpaRepository<ConfigTableEntity, Long> {
    long countByProjectId(Long projectId);


    ConfigTableEntity findByUserId(Long id);

    List<ConfigTableEntity> findByProjectId(Long projectId);

    ConfigTableEntity findByProjectIdAndAccessLevel(Long projectId, Long i);

    ConfigTableEntity findByUserIdAndAccessLevel(Long leaderId, Long i);

    List<ConfigTableEntity> findByProjectIdNot(Long projectId);
}