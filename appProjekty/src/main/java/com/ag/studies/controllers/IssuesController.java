package com.ag.studies.controllers;

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
@CrossOrigin(allowCredentials="true")
@RestController
@RequestMapping("/issues")
public class IssuesController {
    @Autowired
    private IssuesServiceImpl issuesService;

    @GetMapping("/lastThreeByStatus")
    public List<IssueTableEntity> getLastThreeIssuesByStatus(@PathVariable(value = "status") String status){
        return issuesService.findLastThreeByStatus(status);
    }
    @GetMapping("/lastThree")
    public List<IssueTableEntity> getLastThreeIssues(){
        return issuesService.findLastThree();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addIssue(@RequestBody IncomingIssue newIssue){
        issuesService.addIssue(newIssue.getUser(),
                newIssue.getProject(),
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
        private String project;
        private String title;
        private String inscription;

        public Long getUser() {
            return user;
        }

        public void setUser(Long user) {
            this.user = user;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
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

    @GetMapping("/{id}")
    public List<IssueHistoryTableEntity> getById(@PathVariable(value = "id") Long id){
        return issuesService.findByIssueId(id);
    }
    @GetMapping("/all")
    public List<IssueTableEntity> getAll(){
        return issuesService.findAll();
    }
    @GetMapping("/issuesByProject/{project_id}")
    public List<IssueTableEntity> getByProjectId(@PathVariable(value = "project_id") Long id){
        return issuesService.findByProjectId(id);
    }

    @GetMapping("/issuesByProject/{project_id}/{type}")
    public List<IssueTableEntity> getByProjectIdByType(@PathVariable(value = "project_id") Long id, @PathVariable(value = "type") String type){
        return issuesService.findByProjectIdAndByType(id, type);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id){
        issuesService.delete(id);
    }
    @DeleteMapping("/issuesByProject/{project_id}")
    public void deleteByProjectId(@PathVariable(value = "project_id") Long id){
        issuesService.deleteByProjectId(id);
    }
    @DeleteMapping("/issuesByProject/{project_id}/{type}")
    public void deleteByProjectIdByType(@PathVariable(value = "project_id") Long id, @PathVariable(value = "type") String type){
        issuesService.deleteByProjectIdAndByType(id, type);
    }
    @DeleteMapping("/all")
    public void deleteAll(){
        issuesService.deleteAll();
    }


    @GetMapping("/count")
    public long count(){
        return issuesService.count();
    }
    @GetMapping("/issuesByProject/{project_id}/count")
    public long countByProject(@PathVariable(value = "project_id") Long id){
        return issuesService.countByProjectId(id);
    }
    @GetMapping("/issuesByProject/{project_id}/{type}/count")
    public long countByProjectByType(@PathVariable(value = "project_id") Long id, @PathVariable(value = "type") String type){
        return issuesService.countByProjectIdAndByType(id, type);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/modifyIssue/{issId}/{ussId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addIssueModification(@PathVariable(value = "issId") Long issId, @PathVariable(value = "ussId") Long ussId, @RequestBody String newIssueModification){
        issuesService.addIssueModification(issId, ussId, newIssueModification);
        return ResponseEntity.ok().body("Modification has been added.");
    }

    @PutMapping("/type/{id}")
    public IssueTableEntity updateIssue(@PathVariable(value = "id") Long id, @RequestBody String new_type){
        return issuesService.updateIssue(id, new_type);
    }
}
