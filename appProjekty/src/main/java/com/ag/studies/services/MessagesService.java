package com.ag.studies.services;

import com.ag.studies.models.MessageTableEntity;
import com.ag.studies.models.UserTableEntity;

import java.util.List;

public interface MessagesService {

    List<MessageTableEntity> findByCreatorIdOrderByMessageIdDesc(Long creatorId);

    List<MessageTableEntity> findByRecipientIdOrderByMessageIdDesc(Long recipientId);

    List<MessageTableEntity> findByRecipientGroupIdOrderByMessageIdDesc(Long recipientGroupId);

    List<MessageTableEntity> findBySubjectOrderByMessageIdDesc(String subject);

    void deleteAll();

    void delete(String subject);

    long count();

    long count(String subject);

    List<MessageTableEntity> findConversation(Long messageId);

    void addReply(Long user, Long recipientId, Long parentMessageId, String subject, String message);

    void addReplyRecipient(Long messageId, Long recipientId);

    void addMessage(Long creatorId, String recipientEmail, String subject, String messageBody);

    void addMessageRecipient(String recipientEmail, Long messageId);

    List<MessagesServiceImpl.Message> cutMessages(List<MessageTableEntity> all);
}
