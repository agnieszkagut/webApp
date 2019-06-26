package com.ag.studies.controllers;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.IssueHistoryTableEntity;
import com.ag.studies.models.IssueTableEntity;
import com.ag.studies.services.IssuesServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(allowCredentials="true")
@RestController
@RequestMapping("/issues")
public class IssuesController {
    @Autowired
    private IssuesServiceImpl issuesService;

    @GetMapping
    public List<IssuesServiceImpl.Issue> getAll(@RequestParam(value = "limit", required = false) Optional<Integer> limit){
        if(limit.isPresent())   return issuesService.cutIssues(issuesService.findWithLimit(limit.get()));
        else    return issuesService.cutIssues(issuesService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addIssue(@RequestBody IncomingIssue newIssue) throws EntityNotFoundException{
        issuesService.addIssue(newIssue.getUser(),
                newIssue.getProjectId(),
                newIssue.getTitle(),
                 newIssue.getInscription()
        );
        return ResponseEntity.ok().body("Issue has been added.");
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class IncomingIssue{
        private Long user;
        private Long projectId;
        private String title;
        private String inscription;

        public Long getUser() {
            return user;
        }

        public void setUser(Long user) {
            this.user = user;
        }

        public Long getProjectId() {
            return projectId;
        }

        public void setProjectId(Long projectId) {
            this.projectId = projectId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInscription() {
            return inscription;
        }

        public void setInscription(String inscription) {
            this.inscription = inscription;
        }
    }

    @GetMapping("/{issueId}")
    public List<IssuesServiceImpl.IssueHistory> getById(@PathVariable(value = "issueId") Long id) throws EntityNotFoundException {
        return issuesService.cutIssueHistory(issuesService.findByIssueId(id));
    }
    @GetMapping("/project/{projectId}")
    public List<IssuesServiceImpl.Issue> getByProjectId(@PathVariable(value = "projectId") Long id) throws EntityNotFoundException{
        return issuesService.cutIssues(issuesService.findByProjectId(id));
    }

    @DeleteMapping("/{issueId}")
    public void deleteById(@PathVariable(value = "issueId") Long id) throws EntityNotFoundException{
        issuesService.delete(id);
    }
    @DeleteMapping("/project/{projectId}")
    public void deleteByProjectId(@PathVariable(value = "projectId") Long id)  throws EntityNotFoundException{
        issuesService.deleteByProjectId(id);
    }
    @DeleteMapping
    public void deleteAll(){
        issuesService.deleteAll();
    }


    @GetMapping("/count")
    public long count(){
        return issuesService.count();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{issueId}/replies", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addIssueModification(@PathVariable(value = "issueId") Long issId, @RequestBody IncomingIssueReply newIssueModification)   throws EntityNotFoundException {
        issuesService.addIssueModification(issId, newIssueModification.getUserId(), newIssueModification.getText());
        return ResponseEntity.ok().body("Modification has been added.");
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class IncomingIssueReply {
        private Long userId;
        private String text;
    }
    @PutMapping("/{issueId}/type")
    public IssueTableEntity updateIssue(@PathVariable(value = "issueId") Long id, @RequestBody String new_type){
        return issuesService.updateIssue(id, new_type);
    }
}
