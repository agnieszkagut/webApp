package com.ag.studies.services;

import com.ag.studies.models.MessageRecipientTableEntity;
import com.ag.studies.models.MessageTableEntity;
import com.ag.studies.models.UserTableEntity;
import com.ag.studies.repositories.MessageRecipientTableEntityRepository;
import com.ag.studies.repositories.MessageTableEntityRepository;
import com.ag.studies.repositories.UserTableEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static java.lang.System.out;

@Service
public class MessagesServiceImpl implements MessagesService {
    @Autowired
    private MessageTableEntityRepository messagetableentityRepository;
    @Autowired
    private MessageRecipientTableEntityRepository messagerecipienttableentityRepository;
    @Autowired
    private UserTableEntityRepository usertableentityRepository;

    @Override
    public List<MessageTableEntity> findConversation(Long messageId) {
        List<MessageTableEntity> conversation = new ArrayList<MessageTableEntity>();
        while(messagetableentityRepository.findByMessageIdEquals(messageId).getParentMessageId() != null){
            messageId = messagetableentityRepository.findByMessageIdEquals(messageId).getParentMessageId();
        }
        conversation.add(messagetableentityRepository.getOne(messageId));
        while(messagetableentityRepository.findByParentMessageIdEquals(messageId) != null){
            messageId = messagetableentityRepository.findByParentMessageIdEquals(messageId).getMessageId();
            conversation.add(messagetableentityRepository.getOne(messageId));
        }
        return conversation;
    }

    @Override
    public void addReply(Long user, Long recipientId, Long parentMessageId, String subject, String message) {
        MessageTableEntity newMessage = new MessageTableEntity();
        Long Id = (messagetableentityRepository.count()+1);
        newMessage.setMessageId(Id);
        newMessage.setCreatorId(user);
        newMessage.setParentMessageId(parentMessageId);
        newMessage.setSubject(subject);
        newMessage.setMessageBody(message);
        newMessage.setIsRead((short) 0);
        newMessage.setUserTableByCreatorId(usertableentityRepository.getOne(user));
        if(parentMessageId!=0)newMessage.setMessageTableByParentMessageId(messagetableentityRepository.getOne(parentMessageId));
        else newMessage.setMessageTableByParentMessageId(null);
        messagetableentityRepository.save(newMessage);
        MessageRecipientTableEntity newRecipient = new MessageRecipientTableEntity();
        newRecipient.setMessageRecipientId(messagerecipienttableentityRepository.count()+1);
        newRecipient.setRecipientId(recipientId);
        newRecipient.setMessageId(newMessage.getMessageId());
        newRecipient.setUserTableByRecipientId(usertableentityRepository.getOne(recipientId));
        newRecipient.setMessageTableByMessageId(messagetableentityRepository.getOne(Id));
        messagerecipienttableentityRepository.save(newRecipient);
    }

    @Override
    public List<UserTableEntity> findlistOfUsers() {
        return usertableentityRepository.findAll();
    }

    @Override
    public void addMessage(Long creatorId, String recipientEmail, String subject, String messageBody) {
        MessageTableEntity newMessage = new MessageTableEntity();
        Long Id = (messagetableentityRepository.count()+1);
        newMessage.setMessageId(Id);
        newMessage.setCreatorId(creatorId);
        newMessage.setSubject(subject);
        newMessage.setMessageBody(messageBody);
        newMessage.setIsRead((short) 0);
        newMessage.setUserTableByCreatorId(usertableentityRepository.getOne(creatorId));
        messagetableentityRepository.save(newMessage);
        MessageRecipientTableEntity newRecipient = new MessageRecipientTableEntity();
        newRecipient.setMessageRecipientId(messagerecipienttableentityRepository.count()+1);
        out.println(recipientEmail);
        newRecipient.setRecipientId(usertableentityRepository.findByEmailEquals(recipientEmail).getUserId());
        out.println(newRecipient.getRecipientId());
        newRecipient.setMessageId(newMessage.getMessageId());
        newRecipient.setUserTableByRecipientId(usertableentityRepository.findByEmailEquals(recipientEmail));
        newRecipient.setMessageTableByMessageId(messagetableentityRepository.getOne(Id));
        messagerecipienttableentityRepository.save(newRecipient);
    }

    @Override
    public void messageToLeader(Long creatorId, Long projectLeader, String subject, String newMessage) {
        String email = usertableentityRepository.getOne(projectLeader).getEmail();
        addMessage(creatorId, email, subject, newMessage);
    }

    @Override
    public List<MessageTableEntity> findByCreatorIdOrderByMessageIdDesc(Long creatorId) {
        return messagetableentityRepository.findByCreatorIdOrderByMessageIdDesc(creatorId);
    }

    @Override
    public List<MessageTableEntity> findByRecipientIdOrderByMessageIdDesc(Long recipientId) {
        List<MessageTableEntity> ListOfMessages = new ArrayList<MessageTableEntity>();
        List<MessageRecipientTableEntity> ListOfRecipientConnections = messagerecipienttableentityRepository.findByRecipientId(recipientId);
        for(MessageRecipientTableEntity i : ListOfRecipientConnections) ListOfMessages.add(messagetableentityRepository.findOneByMessageId(i.getMessageId()));
        ListOfMessages.sort(new SortByMessageId());
        return ListOfMessages;
    }

    @Override
    public List<MessageTableEntity> findByRecipientGroupIdOrderByMessageIdDesc(Long recipientGroupId) {
        List<MessageTableEntity> ListOfMessages = new ArrayList<MessageTableEntity>();
        List<MessageRecipientTableEntity> ListOfRecipientConnections = messagerecipienttableentityRepository.findByUserGroupId(recipientGroupId);
        for(MessageRecipientTableEntity i : ListOfRecipientConnections) ListOfMessages.add(messagetableentityRepository.findOneByMessageId(i.getMessageId()));
        ListOfMessages.sort(new SortByMessageId());
        return ListOfMessages;
    }

    @Override
    public List<MessageTableEntity> findBySubjectOrderByMessageIdDesc(String subject) {

        return messagetableentityRepository.findBySubjectOrderByMessageIdDesc(subject);
    }

    @Override
    public void deleteAll() {
        messagetableentityRepository.deleteAll();
    }

    @Override
    public void delete(String subject) {
        messagetableentityRepository.deleteBySubjectEqualsIgnoreCase(subject);
    }

    @Override
    public long count() {
        return messagetableentityRepository.count();
    }

    @Override
    public long count(String subject) {
        return messagetableentityRepository.countBySubject(subject);
    }
}

class SortByMessageId implements Comparator<MessageTableEntity> {
    public int compare(MessageTableEntity a, MessageTableEntity b) {
        if ( a.getMessageId() < b.getMessageId() ) return -1;
        else if ( a.getMessageId() == b.getMessageId() ) return 0;
        else return 1;
    }

}
