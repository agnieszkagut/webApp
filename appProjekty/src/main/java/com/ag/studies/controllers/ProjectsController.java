package com.ag.studies.controllers;

import com.ag.studies.models.*;
import com.ag.studies.services.ProjectsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/projects")
public class ProjectsController {
    @Autowired
    private ProjectsServiceImpl projectComponentService;

    @GetMapping("/all")
    public List<ProjectTableEntity> getAllProjects(){
        return projectComponentService.findAll();
    }
    @GetMapping("/byLeader/{leaderId}")
    public ProjectTableEntity getProjectPerLeader(@PathVariable(value = "leaderId") Long leaderId){
        return projectComponentService.findOnebyLeaderId(leaderId);
    }
    @GetMapping("/{projectId}")
    public ProjectTableEntity getOneProject(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.findOnebyProjectId(projectId);
    }
    @GetMapping("/employees/{projectId}")
    public List<UserTableEntity> getEmployees(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.findEmployeesByProjectId(projectId);
    }
    @GetMapping("/otherEmployees/{projectId}")
    public List<UserTableEntity> getOtherEmployees(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.findOtherEmployeesByProjectId(projectId);
    }
    @GetMapping("/tasks/{projectId}")
    public List<TasksTableEntity> getTasks(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.findTasksByProjectId(projectId);
    }
    @GetMapping("/leaders")
    public List<UserTableEntity> getLeaders(){
        return projectComponentService.findLeaders();
    }
    @GetMapping("/projectLeader/{projectId}")
    public UserTableEntity getProjectLeader(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.findProjectLeader(projectId);
    }
    @GetMapping("/unassignedFunds/{projectId}")
    public int getUnassignedFunds(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.findUnassignedFunds(projectId);
    }
    @GetMapping("/countTasksDelayed/{projectId}")
    public int countTasksDelayed(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.countTasksDelayed(projectId);
    }
    @GetMapping("/projectSponsorship/{projectId}")
    public List<SponsorshipTableEntity> getSponsorships(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.findSponsorships(projectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProject(@RequestBody NewProject newProject){
        projectComponentService.addProject(newProject.getLeaderEmail(),
                newProject.getName(),
                newProject.getDescription(),
                newProject.getSponsorship(),
                newProject.getEndDate());
    }
    @PostMapping("/sponsorship")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSponsorship(@RequestBody NewSponsorship newSponsorship){
        projectComponentService.addSponsorship(newSponsorship.getProjectId() ,newSponsorship.getName(), newSponsorship.getValue());
    }

    @PostMapping("/task/{projectId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@PathVariable(value = "projectId") Long projectId, @RequestBody NewTask newTask){
        projectComponentService.addTask(projectId, newTask.getName() , newTask.getDeadline());
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") Long id){
        projectComponentService.delete(id);
    }

    @GetMapping("/count")
    public long count(){
        return projectComponentService.count();
    }
    @GetMapping("/count/{status}")
    public long countByStatus(@PathVariable(value = "status") String status){
        return projectComponentService.countByStatus(status);
    }
    @GetMapping("/countEmployees/{projectId}")
    public long countEmployees(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.countEmployeesByProjectId(projectId);
    }
    @GetMapping("/countTasks/{projectId}")
    public long countTasks(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.countTasksByProjectId(projectId);
    }
    @GetMapping("/countTasksDone/{projectId}")
    public long countTasksDone(@PathVariable(value = "projectId") Long projectId){
        return projectComponentService.countTasksByProjectIdByIsDone( projectId, true);
    }
    @PutMapping("/{id}")
    public ProjectTableEntity updateProject(@PathVariable(value = "id") Long id, @RequestBody String new_status){
        return projectComponentService.updateProject(id, new_status);
    }
    @PutMapping("/isDone/{taskId}")
    public TasksTableEntity updateTask(@PathVariable(value = "taskId") Long taskId){
        return projectComponentService.updateTask(taskId);
    }
    @PutMapping("/endDate/{projectId}")
    public ProjectTableEntity updateProjectEnd(@PathVariable(value = "projectId") Long projectId, @RequestBody Date newEndDate){
        return projectComponentService.updateProjectEnd(projectId, newEndDate);
    }
    @PutMapping("/status/{status}")
    public void updateProjectStatus(@PathVariable(value = "status") String status){
        projectComponentService.updateProjectStatus(status);
    }
    @PutMapping("/user")
    public ConfigTableEntity updateUser(@RequestBody Change change){
        return projectComponentService.updateUser(change.getUser(), change.getProject());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Change{
        String user;
        String project;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class NewSponsorship{
        Long projectId;
        String name;
        BigDecimal value;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class NewProject{
        String name;
        String leaderEmail;
        String description;
        BigDecimal sponsorship;
        Date endDate;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class NewTask{
        String name;
        Date deadline;
    }
}
