package com.ag.studies.services;

import com.ag.studies.models.*;
import com.ag.studies.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectsServiceImpl implements ProjectsSevice {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectTableEntityRepository projecttableentityRepository;
    @Autowired
    private SponsorshipTableEntityRepository sponsorshiptableentityRepository;
    @Autowired
    private ConfigTableEntityRepository configtableentityRepository;
    @Autowired
    private UserTableEntityRepository usertableentityRepository;
    @Autowired
    private TasksTableEntityRepository taskstableentityRepository;



    public List<ProjectTableEntity> findAll() {
        return projecttableentityRepository.findAll();
    }


    public void delete(Long id) {
        projecttableentityRepository.deleteById(id);
    }


    public long count() {
        return projecttableentityRepository.count();
    }


    public long countByStatus(String status) {
        return projecttableentityRepository.countByStatus(status);
    }


    public ProjectTableEntity updateProject(Long id, String new_status) {
        ProjectTableEntity updatedProject = projecttableentityRepository.findById(id)
                .orElseThrow(() -> new NotFoundProjectEntity(id));
        updatedProject.setStatus(new_status);
        return projecttableentityRepository.save(updatedProject);
    }

    public long countEmployeesByProjectId(Long projectId) {
        return configtableentityRepository.countByProjectId(projectId);
    }


    public List<UserTableEntity> findEmployeesByProjectId(Long projectId) {
        List<ConfigTableEntity> ConfigList = configtableentityRepository.findByProjectId(projectId);
        List<UserTableEntity> Employees = new ArrayList<UserTableEntity>();
        for(ConfigTableEntity tmp : ConfigList){
            Employees.add(usertableentityRepository.findByUserId(tmp.getUserId()));
        }
        return Employees;
    }

    public List<UserTableEntity> findOtherEmployeesByProjectId(Long projectId) {
        List<ConfigTableEntity> ConfigList = configtableentityRepository.findByProjectIdNot(projectId);
        List<Long> Indexes = new ArrayList<Long>();
        for(ConfigTableEntity tmp : ConfigList){
            if(usertableentityRepository.getOne(tmp.getUserId()).getPosition().equals("kierownik projektu")
                    || usertableentityRepository.getOne(tmp.getUserId()).getPosition().equals("kierownik programu")
                    || usertableentityRepository.getOne(tmp.getUserId()).getPosition().equals("admin")) {

            }
            else Indexes.add(tmp.getUserId());
        }
        List<UserTableEntity> Employees = new ArrayList<UserTableEntity>();
        for(Long index : Indexes){
            Employees.add(usertableentityRepository.findByUserId(index));
        }
        return Employees;
    }

    public UserTableEntity findProjectLeader(Long projectId) {
        Long id = configtableentityRepository.findByProjectIdAndAccessLevel(projectId, Long.valueOf(1)).getUserId();
        return usertableentityRepository.getOne(id);
    }


    public ConfigTableEntity updateUser(String userEmail, String projectName) {
        Long userId = usertableentityRepository.findByEmail(userEmail).getUserId();
        ConfigTableEntity updatedConfig = configtableentityRepository.findByUserId(userId);
        Long projectId = projecttableentityRepository.findByNameEquals(projectName).getProjectId();
        updatedConfig.setProjectId(projectId);
        return configtableentityRepository.save(updatedConfig);
    }


    public List<SponsorshipTableEntity> findSponsorships(Long projectId) {
        return sponsorshiptableentityRepository.findByProjectId(projectId);
    }

    @Override
    public ProjectTableEntity findOnebyProjectId(Long projectId) {
        return projecttableentityRepository.findOneByProjectId(projectId);
    }

    @Override
    public long countTasksByProjectId(Long projectId) {
        return taskstableentityRepository.countByProjectId(projectId);
    }

    @Override
    public long countTasksByProjectIdByIsDone(Long projectId, Boolean i) {
        return taskstableentityRepository.countByProjectIdAndIsDone(projectId, i);
    }

    @Override
    public TasksTableEntity updateTask(Long taskId) {
        TasksTableEntity updatedTask = taskstableentityRepository.getOne(taskId);
        if(updatedTask.getIsDone()){
            updatedTask.setIsDone(false);
        }
        else{
            updatedTask.setIsDone(true);
        }
        taskstableentityRepository.save(updatedTask);
        return updatedTask;
    }

    @Override
    public void addTask(Long projectId, String name, Date deadline) {
        Long taskId = taskstableentityRepository.count()+1;
        TasksTableEntity newTask = new TasksTableEntity();
        newTask.setTaskId(taskId);
        newTask.setIsDone(false);
        Timestamp ts=new Timestamp(deadline.getTime());
        newTask.setDeadline(ts);
        newTask.setName(name);
        taskstableentityRepository.save(newTask);
    }

    @Override
    public List<UserTableEntity> findLeaders() {
        return usertableentityRepository.findByPosition("kierownik projektu");
    }

    @Override
    public void addProject(String leaderEmail, String name, String description, BigDecimal sponsorship, Date endDate) {
        ProjectTableEntity newProject = new ProjectTableEntity();
        Long projectId = (projecttableentityRepository.count()+1);
        newProject.setProjectId(projectId);
        newProject.setName(name);
        newProject.setStatus("Zarejestrowana");
        newProject.setDescription(description);
        newProject.setSponsorship(sponsorship);
        Timestamp ts=new Timestamp(endDate.getTime());
        newProject.setEndDate(ts);
        projecttableentityRepository.save(newProject);
        ConfigTableEntity newConfig = new ConfigTableEntity();
        newConfig.setConfigId(configtableentityRepository.count()+1);
        newConfig.setProjectId(projectId);
        Long userId = (usertableentityRepository.findByEmail(leaderEmail).getUserId());
        newConfig.setUserId(userId);
        newConfig.setAccessLevel(1L);
        newConfig.setUserTableByUserId(usertableentityRepository.getOne(userId));
        newConfig.setProjectTableByProjectId(projecttableentityRepository.getOne(projectId));
        configtableentityRepository.save(newConfig);
    }

    @Override
    public ProjectTableEntity findOnebyLeaderId(Long leaderId) {
        Long projectId = configtableentityRepository.findByUserIdAndAccessLevel(leaderId, Long.valueOf(1)).getProjectId();
        return projecttableentityRepository.getOne(projectId);
    }

    @Override
    public void updateProjectStatus(String status) {
        for(ProjectTableEntity p: projecttableentityRepository.findAll()){
            p.setStatus(status);
        }
    }

    @Override
    public String getProjectTitle(Long creatorId, String recipientEmail) {
        String title;
        if(usertableentityRepository.getOne(creatorId).getPosition().equals("kierownik programu")){
            title = projecttableentityRepository.getOne(
                    configtableentityRepository.findByUserId(
                            usertableentityRepository.findByEmailEquals(
                                    recipientEmail)
                                    .getUserId())
                            .getProjectId())
                    .getName();
        }
        else{
                title = projecttableentityRepository.getOne(
                        configtableentityRepository.findByUserId(creatorId)
                                .getProjectId())
                        .getName();
        }
        return title;
    }

    @Override
    public List<TasksTableEntity> findTasksByProjectId(Long projectId) {
        return taskstableentityRepository.findByProjectId(projectId);
    }

    @Override
    public void addSponsorship(Long projectId, String name, BigDecimal value) {
        Long sponsorhshipId = sponsorshiptableentityRepository.count()+1;
        SponsorshipTableEntity newSponsorship = new SponsorshipTableEntity();
        newSponsorship.setSponsorshipId(sponsorhshipId);
        newSponsorship.setProjectId(projectId);
        newSponsorship.setName(name);
        newSponsorship.setValue(value);
        sponsorshiptableentityRepository.save(newSponsorship);
    }

    @Override
    public int findUnassignedFunds(Long projectId) {
        int assignedFunds = 0;
        for(SponsorshipTableEntity e : sponsorshiptableentityRepository.findByProjectId(projectId)){
            assignedFunds+=e.getValue().intValue();
        }
        return (projecttableentityRepository.findOneByProjectId(projectId).getSponsorship().intValue() - assignedFunds);
    }

    @Override
    public int countTasksDelayed(Long projectId) {
        LocalDate currentDate = LocalDate.now();
        return taskstableentityRepository.countByDeadlineAfter(currentDate);
    }

    @Override
    public ProjectTableEntity updateProjectEnd(Long projectId, Date newEndDate) {
        ProjectTableEntity updatedProject = new ProjectTableEntity();
        Timestamp ts=new Timestamp(newEndDate.getTime());
        updatedProject.setEndDate(ts);
        projecttableentityRepository.save(updatedProject);
        return updatedProject;
    }

    private class NotFoundProjectEntity extends RuntimeException  {
        public NotFoundProjectEntity(Long id) {
            super("Could not find the project with given id" + id );
        }
    }
}
