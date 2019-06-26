package com.ag.studies.services;

import com.ag.studies.models.MessageRecipientTableEntity;
import com.ag.studies.models.MessageTableEntity;
import com.ag.studies.repositories.MessageRecipientTableEntityRepository;
import com.ag.studies.repositories.MessageTableEntityRepository;
import com.ag.studies.repositories.UserTableEntityRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {
    @Autowired
    private MessageTableEntityRepository messageTableEntityRepository;
    @Autowired
    private MessageRecipientTableEntityRepository messageRecipientTableEntityRepository;
    @Autowired
    private UserTableEntityRepository userTableEntityRepository;

    @Override
    public List<MessageTableEntity> findConversation(Long messageId) {
        List<MessageTableEntity> conversation = new ArrayList<MessageTableEntity>();
        while(messageTableEntityRepository.findByMessageIdEquals(messageId).getParentMessageId() != null){
            messageId = messageTableEntityRepository.findByMessageIdEquals(messageId).getParentMessageId();
        }
        conversation.add(messageTableEntityRepository.getOne(messageId));
        while(messageTableEntityRepository.findByParentMessageIdEquals(messageId) != null){
            messageId = messageTableEntityRepository.findByParentMessageIdEquals(messageId).getMessageId();
            conversation.add(messageTableEntityRepository.getOne(messageId));
        }
        return conversation;
    }

    @Override
    public void addReply(Long user, Long recipientId, Long parentMessageId, String subject, String message) {
        MessageTableEntity newMessage = new MessageTableEntity();
        newMessage.setCreatorId(user);
        newMessage.setParentMessageId(parentMessageId);
        newMessage.setSubject(subject);
        newMessage.setMessageBody(message);
        newMessage.setIsRead((short) 0);
        newMessage.setUserTableByCreatorId(userTableEntityRepository.getOne(user));
        if(parentMessageId!=0)newMessage.setMessageTableByParentMessageId(messageTableEntityRepository.getOne(parentMessageId));
        else newMessage.setMessageTableByParentMessageId(null);
        Long messageId = messageTableEntityRepository.save(newMessage).getMessageId();
        addReplyRecipient(messageId, recipientId);
    }

    @Override
    public void addReplyRecipient(Long messageId, Long recipientId){
        MessageRecipientTableEntity newRecipient = new MessageRecipientTableEntity();
        newRecipient.setRecipientId(recipientId);
        newRecipient.setMessageId(messageId);
        newRecipient.setUserTableByRecipientId(userTableEntityRepository.getOne(recipientId));
        newRecipient.setMessageTableByMessageId(messageTableEntityRepository.getOne(messageId));
        messageRecipientTableEntityRepository.save(newRecipient);
    }

    @Override
    public void addMessage(Long creatorId, String recipientEmail, String subject, String messageBody) {
        MessageTableEntity newMessage = new MessageTableEntity();
        newMessage.setCreatorId(creatorId);
        newMessage.setSubject(subject);
        newMessage.setMessageBody(messageBody);
        newMessage.setIsRead((short) 0);
        newMessage.setUserTableByCreatorId(userTableEntityRepository.getOne(creatorId));
        Long messageId = messageTableEntityRepository.save(newMessage).getMessageId();
        addMessageRecipient(recipientEmail, messageId);
    }

    @Override
    public void addMessageRecipient(String recipientEmail, Long messageId){
        MessageRecipientTableEntity newRecipient = new MessageRecipientTableEntity();
        newRecipient.setRecipientId(userTableEntityRepository.findByEmailEquals(recipientEmail).getUserId());
        newRecipient.setMessageId(messageId);
        newRecipient.setUserTableByRecipientId(userTableEntityRepository.findByEmailEquals(recipientEmail));
        newRecipient.setMessageTableByMessageId(messageTableEntityRepository.getOne(messageId));
        messageRecipientTableEntityRepository.save(newRecipient);
    }

    @Override
    public List<MessageTableEntity> findByCreatorIdOrderByMessageIdDesc(Long creatorId) {
        return messageTableEntityRepository.findByCreatorIdOrderByMessageIdDesc(creatorId);
    }

    @Override
    public List<MessageTableEntity> findByRecipientIdOrderByMessageIdDesc(Long recipientId) {
        List<MessageTableEntity> ListOfMessages = new ArrayList<MessageTableEntity>();
        List<MessageRecipientTableEntity> ListOfRecipientConnections = messageRecipientTableEntityRepository.findByRecipientId(recipientId);
        for(MessageRecipientTableEntity i : ListOfRecipientConnections) ListOfMessages.add(messageTableEntityRepository.findOneByMessageId(i.getMessageId()));
        ListOfMessages.sort(new SortByMessageId());
        return ListOfMessages;
    }

    @Override
    public List<MessageTableEntity> findByRecipientGroupIdOrderByMessageIdDesc(Long recipientGroupId) {
        List<MessageTableEntity> ListOfMessages = new ArrayList<MessageTableEntity>();
        List<MessageRecipientTableEntity> ListOfRecipientConnections = messageRecipientTableEntityRepository.findByUserGroupId(recipientGroupId);
        for(MessageRecipientTableEntity i : ListOfRecipientConnections) ListOfMessages.add(messageTableEntityRepository.findOneByMessageId(i.getMessageId()));
        ListOfMessages.sort(new SortByMessageId());
        return ListOfMessages;
    }

    @Override
    public List<MessageTableEntity> findBySubjectOrderByMessageIdDesc(String subject) {

        return messageTableEntityRepository.findBySubjectOrderByMessageIdDesc(subject);
    }

    @Override
    public void deleteAll() {
        messageTableEntityRepository.deleteAll();
    }

    @Override
    public void delete(String subject) {
        messageTableEntityRepository.deleteBySubjectEqualsIgnoreCase(subject);
    }

    @Override
    public long count() {
        return messageTableEntityRepository.count();
    }

    @Override
    public long count(String subject) {
        return messageTableEntityRepository.countBySubject(subject);
    }

}

class SortByMessageId implements Comparator<MessageTableEntity> {
    public int compare(MessageTableEntity a, MessageTableEntity b) {
        if ( a.getMessageId() < b.getMessageId() ) return -1;
        else if ( a.getMessageId() == b.getMessageId() ) return 0;
        else return 1;
    }

}
