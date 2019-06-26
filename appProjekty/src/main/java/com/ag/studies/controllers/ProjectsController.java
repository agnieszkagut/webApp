package com.ag.studies.controllers;

import com.ag.studies.EntityNotFoundException;
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

    @PostMapping("/{projectId}/leader/message}")
    @ResponseStatus(HttpStatus.CREATED)
    public void messageToLeader(@PathVariable(value = "projectId") Long projectId, @RequestBody MessagesController.NewMessage newMessage) throws EntityNotFoundException {
        projectComponentService.messageToLeader(newMessage.getCraetorId(), projectComponentService.findProjectLeader(projectId).getUserId(), newMessage.getSubject(), newMessage.getMessage());
    }
    @GetMapping
    public List<ProjectTableEntity> getAllProjects(){
        return projectComponentService.findAll();
    }
    @GetMapping("/leader/{leaderId}")
    public ProjectTableEntity getProjectPerLeader(@PathVariable(value = "leaderId") Long leaderId) throws EntityNotFoundException{
        return projectComponentService.findOnebyLeaderId(leaderId);
    }
    @GetMapping("/{projectId}")
    public ProjectTableEntity getOneProject(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
        return projectComponentService.findOneByProjectId(projectId);
    }
    @GetMapping("/{projectId}/employees")
    public List<UserTableEntity> getEmployees(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
        return projectComponentService.findEmployeesByProjectId(projectId);
    }
    @GetMapping("/{projectId}/employees/other")
    public List<UserTableEntity> getOtherEmployees(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
        return projectComponentService.findOtherEmployeesByProjectId(projectId);
    }
    @GetMapping("/{projectId}/tasks")
    public List<TasksTableEntity> getTasks(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
        return projectComponentService.findTasksByProjectId(projectId);
    }
    @GetMapping("/leaders")
    public List<UserTableEntity> getLeaders(){
        return projectComponentService.findLeaders();
    }
    @GetMapping("/{projectId}/leader")
    public UserTableEntity getProjectLeader(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
        return projectComponentService.findProjectLeader(projectId);
    }
    @GetMapping("/{projectId}/sponsorship/unassigned")
    public int getUnassignedFunds(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
        return projectComponentService.findUnassignedFunds(projectId);
    }
    @GetMapping("/{projectId}/tasks/delayed/count")
    public int countTasksDelayed(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
        return projectComponentService.countTasksDelayed(projectId);
    }
    @GetMapping("/{projectId}/sponsorship")
    public List<SponsorshipTableEntity> getSponsorships(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
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
    @PostMapping("/{projectId}/sponsorship")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSponsorship(@PathVariable(value = "projectId") Long projectId, @RequestBody NewSponsorship newSponsorship) throws EntityNotFoundException{
        projectComponentService.addSponsorship(projectId ,newSponsorship.getName(), newSponsorship.getValue());
    }

    @PostMapping("/{projectId}/task")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@PathVariable(value = "projectId") Long projectId, @RequestBody NewTask newTask) throws EntityNotFoundException{
        projectComponentService.addTask(projectId, newTask.getName() , newTask.getDeadline());
    }
    @DeleteMapping("/{projectId}")
    public void deleteById(@PathVariable(value = "projectId") Long id) throws EntityNotFoundException{
        projectComponentService.delete(id);
    }

    @GetMapping("/count")
    public long count(){
        return projectComponentService.count();
    }
    @GetMapping("/{projectId}/employees/count")
    public long countEmployees(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
        return projectComponentService.countEmployeesByProjectId(projectId);
    }
    @GetMapping("/{projectId}/tasks/count")
    public long countTasks(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
        return projectComponentService.countTasksByProjectId(projectId);
    }
    @GetMapping("/{projectId}/tasks/done/count")
    public long countTasksDone(@PathVariable(value = "projectId") Long projectId) throws EntityNotFoundException{
        return projectComponentService.countTasksByProjectIdByIsDone( projectId, true);
    }
    @PutMapping("/{projectId}")
    public ProjectTableEntity updateProject(@PathVariable(value = "projectId") Long id, @RequestBody String new_status) throws EntityNotFoundException{
        return projectComponentService.updateProject(id, new_status);
    }
    @PutMapping("/tasks/{taskId}/done")
    public TasksTableEntity updateTask(@PathVariable(value = "taskId") Long taskId) throws EntityNotFoundException{
        return projectComponentService.updateTask(taskId);
    }
    @PutMapping("/{projectId}/endDate")
    public ProjectTableEntity updateProjectEnd(@PathVariable(value = "projectId") Long projectId, @RequestBody Date newEndDate) throws EntityNotFoundException{
        return projectComponentService.updateProjectEnd(projectId, newEndDate);
    }
    @PutMapping("/status/{status}")
    public void updateProjectStatus(@PathVariable(value = "status") String status){
        projectComponentService.updateProjectStatus(status);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class NewSponsorship{
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
