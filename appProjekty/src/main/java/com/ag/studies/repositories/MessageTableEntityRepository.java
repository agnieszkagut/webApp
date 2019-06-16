package com.ag.studies.repositories;

import com.ag.studies.models.MessageTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageTableEntityRepository extends JpaRepository<MessageTableEntity, Long> {

    void deleteBySubjectEqualsIgnoreCase(String subject);

    long countBySubject(String subject);

    List<MessageTableEntity> findByParentMessageIdEqualsOrderByMessageIdDesc(Long parentMessageId);

    List<MessageTableEntity> findByCreatorIdOrderByMessageIdDesc(Long creatorId);

    List<MessageTableEntity> findBySubjectOrderByMessageIdDesc(String subject);

    MessageTableEntity findOneByMessageId(Long valueOf);

    MessageTableEntity findByParentMessageIdEquals(Long messageId);

    MessageTableEntity findByMessageIdEquals(Long messageId);
}