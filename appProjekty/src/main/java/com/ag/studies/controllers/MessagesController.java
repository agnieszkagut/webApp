package com.ag.studies.controllers;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.MessageTableEntity;
import com.ag.studies.models.UserTableEntity;
import com.ag.studies.services.IssuesServiceImpl;
import com.ag.studies.services.MessagesServiceImpl;
import com.ag.studies.services.ProjectsServiceImpl;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(allowCredentials="true")
@RestController
@RequestMapping("/messages")
public class MessagesController {
    @Autowired
    private MessagesServiceImpl messagesService;
    @Autowired
    private IssuesServiceImpl issuesService;
    @Autowired
    private ProjectsServiceImpl projectsService;

    @GetMapping("/{messageId}/replies")
    public List<MessageTableEntity.Message> getConversation(@PathVariable(value = "messageId") Long messageId){
        return MessageTableEntity.mapToMessage(messagesService.findConversation(messageId));
    }

    @GetMapping("/user/{userId}/created")
    public List<MessageTableEntity.Message> getByCreatorId(@PathVariable(value = "userId") Long creatorId){
        return MessageTableEntity.mapToMessage(messagesService.findByCreatorIdOrderByMessageIdDesc(creatorId));
    }
    @GetMapping("/user/{userId}/received")
    public List<MessageTableEntity.Message> getByRecipientId(@PathVariable(value = "userId") Long recipientId){
        return MessageTableEntity.mapToMessage(messagesService.findByRecipientIdOrderByMessageIdDesc(recipientId));
    }
    @PostMapping("/{messageId}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addReply(@PathVariable(value = "messageId") Long parentMessageId, @RequestBody IncomingReply newMessage){
        messagesService.addReply(newMessage.getUser(),
                newMessage.getRecipientId(),
                parentMessageId,
                newMessage.getSubject(),
                newMessage.getMessage()
        );
        return ResponseEntity.ok().body("Message has been added.");
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addMessage(@RequestBody IncomingMessage newMessage) throws EntityNotFoundException {
        messagesService.addMessage(newMessage.getCreatorId(),
                newMessage.getRecipientEmail(),
                newMessage.getSubject(),
                newMessage.getMessageBody()
        );
        issuesService.addIssue(newMessage.getCreatorId(),
                projectsService.getProjectId(
                        newMessage.getCreatorId(), newMessage.getRecipientEmail()),
                newMessage.getSubject(),
                newMessage.getMessageBody()
                );
        return ResponseEntity.ok().body("Message (and issue) has been added.");
    }
    @GetMapping("/count")
    public long count(){
        return messagesService.count();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class IncomingReply{
        private Long user;
        private Long recipientId;
        private String subject;
        private String message;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class IncomingMessage{
        Long creatorId;
        String recipientEmail;
        String subject;
        String messageBody;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class NewMessage{
        Long craetorId;
        String subject;
        String message;
    }
}
