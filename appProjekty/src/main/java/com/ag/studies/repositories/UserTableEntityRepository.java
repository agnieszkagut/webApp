package com.ag.studies.repositories;

import com.ag.studies.models.RolesTableEntity;
import com.ag.studies.models.UserTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTableEntityRepository extends JpaRepository<UserTableEntity, Long> {

    UserTableEntity findByEmail(String email);

    UserTableEntity findByUserId(Long index);

    UserTableEntity findByEmailEquals(String recipientEmail);

    UserTableEntity findByUsername(String username);

    List<UserTableEntity> findByPosition(String kierownik_projektu);
}