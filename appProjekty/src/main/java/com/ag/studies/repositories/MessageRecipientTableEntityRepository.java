package com.ag.studies.repositories;

import com.ag.studies.models.MessageRecipientTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRecipientTableEntityRepository extends JpaRepository<MessageRecipientTableEntity, Long> {
    List<MessageRecipientTableEntity> findByRecipientId(Long recipientId);

    List<MessageRecipientTableEntity> findByUserGroupId(Long recipientGroupId);
}