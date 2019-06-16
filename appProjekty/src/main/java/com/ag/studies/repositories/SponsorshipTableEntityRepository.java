package com.ag.studies.repositories;

import com.ag.studies.models.SponsorshipTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SponsorshipTableEntityRepository extends JpaRepository<SponsorshipTableEntity, Long> {
    List<SponsorshipTableEntity> findByProjectId(Long projectId);
}