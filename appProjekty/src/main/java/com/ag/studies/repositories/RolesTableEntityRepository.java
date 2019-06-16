package com.ag.studies.repositories;

import com.ag.studies.models.RolesTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesTableEntityRepository extends JpaRepository<RolesTableEntity, Long> {
}