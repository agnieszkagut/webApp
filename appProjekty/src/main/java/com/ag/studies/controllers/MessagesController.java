package com.ag.studies.controllers;

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

    @GetMapping("/listOfUsers")
    public List<UserTableEntity> getlistOfUsers(){
        return messagesService.findlistOfUsers();
    }
    @GetMapping("/conversation/{messageId}")
    public List<MessageTableEntity> getConversation(@PathVariable(value = "messageId") Long messageId){
        return messagesService.findConversation(messageId);
    }

    @GetMapping("/bySubject/{subject}")
    public List<MessageTableEntity> getBySubject(@PathVariable(value = "subject") String subject){
        return messagesService.findBySubjectOrderByMessageIdDesc(subject);
    }
    @GetMapping("/byCreator/{creatorId}")
    public List<MessageTableEntity> getByCreatorId(@PathVariable(value = "creatorId") Long creatorId){
        return messagesService.findByCreatorIdOrderByMessageIdDesc(creatorId );
    }
    @GetMapping("/byRecipient/{recipientId}")
    public List<MessageTableEntity> getByRecipientId(@PathVariable(value = "recipientId") Long recipientId){
        return messagesService.findByRecipientIdOrderByMessageIdDesc(recipientId );
    }
    @GetMapping("/byRecipientGroup/{recipientGroupId}")
    public List<MessageTableEntity> getByRecipientGroupId(@PathVariable(value = "recipientGroupId") Long recipientGroupId){
        return messagesService.findByRecipientGroupIdOrderByMessageIdDesc(recipientGroupId );
    }
    @PostMapping("/toLeader/{projectLeader}")
    @ResponseStatus(HttpStatus.CREATED)
    public void messageToLeader(@PathVariable(value = "projectLeader") Long projectLeader, @RequestBody NewMessage newMessage){
        messagesService.messageToLeader(newMessage.getCraetorId(), projectLeader, newMessage.getSubject(), newMessage.getMessage());
    }
    @PostMapping("/newReply")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addReply(@RequestBody IncomingReply newMessage){
        messagesService.addReply(newMessage.getUser(),
                newMessage.getRecipientId(),
                newMessage.getParentMessageId(),
                newMessage.getSubject(),
                newMessage.getMessage()
        );
        return ResponseEntity.ok().body("Message has been added.");
    }
    @PostMapping("/newMessage")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addMessage(@RequestBody IncomingMessage newMessage){
        messagesService.addMessage(newMessage.getCreatorId(),
                newMessage.getRecipientEmail(),
                newMessage.getSubject(),
                newMessage.getMessageBody()
        );
        issuesService.addIssue(newMessage.getCreatorId(),
                projectsService.getProjectTitle(
                        newMessage.getCreatorId(), newMessage.getRecipientEmail()),
                newMessage.getSubject(),
                newMessage.getMessageBody()
                );
        return ResponseEntity.ok().body("Message (and issue) has been added.");
    }
    //Long userId, String projectTitle, String title , String inscription
    @DeleteMapping("/all")
    public void deleteAll(){
        messagesService.deleteAll();
    }
    @DeleteMapping("/bySubject/{subject}")
    public void deleteById(@PathVariable(value = "subject") String subject){
        messagesService.delete(subject);
    }

    @GetMapping("/count")
    public long count(){
        return messagesService.count();
    }
    @GetMapping("/count/{subject}")
    public long count(@PathVariable(value = "subject") String subject){
        return messagesService.count(subject);
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class IncomingReply{
        private Long user;
        private Long recipientId;
        private Long parentMessageId;
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
