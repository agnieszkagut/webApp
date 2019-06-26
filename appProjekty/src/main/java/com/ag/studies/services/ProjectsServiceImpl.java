package com.ag.studies.services;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.*;
import com.ag.studies.repositories.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
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
    @Autowired
    private MessagesServiceImpl messagesService;

    @Override
    public void messageToLeader(Long creatorId, Long projectLeader, String subject, String newMessage)  throws EntityNotFoundException {
        UserTableEntity user = usertableentityRepository.getOne(projectLeader);
        if(user == null) throw new EntityNotFoundException(UserTableEntity.class, "projectLeader", projectLeader.toString());
        String email = user.getEmail();
        messagesService.addMessage(creatorId, email, subject, newMessage);
    }
    @Override
    public List<ProjectTableEntity> findAll() {
        return projecttableentityRepository.findAll();
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(id).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", id.toString());
        for(ConfigTableEntity config : configtableentityRepository.findByProjectId(id)){
            configtableentityRepository.deleteById(config.getConfigId());
        }
        projecttableentityRepository.deleteById(id);
    }

    @Override
    public long count() {
        return projecttableentityRepository.count();
    }

    @Override
    public ProjectTableEntity updateProject(Long id, String new_status) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(id).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", id.toString());
        ProjectTableEntity updatedProject = projecttableentityRepository.findById(id)
                .orElseThrow(() -> new NotFoundProjectEntity(id));
        updatedProject.setStatus(new_status);
        return projecttableentityRepository.save(updatedProject);
    }
    @Override
    public long countEmployeesByProjectId(Long projectId) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return configtableentityRepository.countByProjectId(projectId);
    }

    @Override
    public List<UserTableEntity> findEmployeesByProjectId(Long projectId) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        List<ConfigTableEntity> ConfigList = configtableentityRepository.findByProjectId(projectId);
        List<UserTableEntity> Employees = new ArrayList<UserTableEntity>();
        for(ConfigTableEntity tmp : ConfigList){
            Employees.add(usertableentityRepository.findByUserId(tmp.getUserId()));
        }
        return Employees;
    }
    @Override
    public List<UserTableEntity> findOtherEmployeesByProjectId(Long projectId) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
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
    @Override
    public UserTableEntity findProjectLeader(Long projectId) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        Long id = configtableentityRepository.findByProjectIdAndAccessLevel(projectId, Long.valueOf(1)).getUserId();
        return usertableentityRepository.getOne(id);
    }

    @Override
    public List<SponsorshipTableEntity> findSponsorships(Long projectId) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return sponsorshiptableentityRepository.findByProjectId(projectId);
    }

    @Override
    public ProjectTableEntity findOneByProjectId(Long projectId)  throws EntityNotFoundException {
        ProjectTableEntity result = projecttableentityRepository.findOneByProjectId(projectId);
        if(result == null) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return result;
    }

    @Override
    public long countTasksByProjectId(Long projectId) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return taskstableentityRepository.countByProjectId(projectId);
    }

    @Override
    public long countTasksByProjectIdByIsDone(Long projectId, Boolean i) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return taskstableentityRepository.countByProjectIdAndIsDone(projectId, i);
    }

    @Override
    public TasksTableEntity updateTask(Long taskId) throws EntityNotFoundException {
        if(!taskstableentityRepository.findById(taskId).isPresent()) throw new EntityNotFoundException(TasksTableEntity.class, "taskId", taskId.toString());
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
    public void addTask(Long projectId, String name, Date deadline) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        TasksTableEntity newTask = new TasksTableEntity();
        newTask.setProjectId(projectId);
        newTask.setIsDone(false);
        Timestamp ts=new Timestamp(deadline.getTime());
        newTask.setDeadline(ts);
        newTask.setName(name);
        try{
            taskstableentityRepository.save(newTask);
        }catch (DataIntegrityViolationException ex){
            addTask(projectId, name, deadline);
        }
    }

    @Override
    public List<UserTableEntity> findLeaders() {
        return usertableentityRepository.findByPosition("kierownik projektu");
    }

    @Override
    public void addProject(String leaderEmail, String name, String description, BigDecimal sponsorship, Date endDate) {
        ProjectTableEntity newProject = new ProjectTableEntity();
        newProject.setName(name);
        newProject.setStatus("Zarejestrowana");
        newProject.setDescription(description);
        newProject.setSponsorship(sponsorship);
        Timestamp ts=new Timestamp(endDate.getTime());
        newProject.setEndDate(ts);
        try{
            Long projectId = projecttableentityRepository.save(newProject).getProjectId();
            Long userId = (usertableentityRepository.findByEmail(leaderEmail).getUserId());
            addConfig(projectId, userId);
        }catch(DataIntegrityViolationException ex){
            addProject(leaderEmail,name,description,sponsorship,endDate);
        }
    }

    @Override
    public void addConfig(Long projectId, Long userId){
        ConfigTableEntity newConfig = new ConfigTableEntity();
        newConfig.setProjectId(projectId);
        newConfig.setUserId(userId);
        newConfig.setAccessLevel(1L);
        newConfig.setUserTableByUserId(usertableentityRepository.getOne(userId));
        newConfig.setProjectTableByProjectId(projecttableentityRepository.getOne(projectId));
        try {
            configtableentityRepository.save(newConfig);
        }catch (DataIntegrityViolationException ex){
            addConfig(projectId, userId);
        }
    }

    @Override
    public ProjectTableEntity findOnebyLeaderId(Long leaderId) throws EntityNotFoundException {
        ConfigTableEntity config = configtableentityRepository.findByUserIdAndAccessLevel(leaderId, Long.valueOf(1));
        if(config == null) throw new EntityNotFoundException(ConfigTableEntity.class, "leaderId", leaderId.toString());
        Long projectId = config.getProjectId();
        return projecttableentityRepository.getOne(projectId);
    }

    @Override
    public void updateProjectStatus(String status) {
        for(ProjectTableEntity p: projecttableentityRepository.findAll()){
            p.setStatus(status);
        }
    }

    @Override
    public Long getProjectId(Long creatorId, String recipientEmail) {
        Long id;
        if(usertableentityRepository.getOne(creatorId).getPosition().equals("kierownik programu")){
            id = configtableentityRepository.findByUserId(
                            usertableentityRepository.findByEmailEquals(
                                    recipientEmail)
                                    .getUserId())
                            .getProjectId();
        }
        else{
                id = configtableentityRepository.findByUserId(creatorId)
                                .getProjectId();
        }
        return id;
    }

    @Override
    public List<TasksTableEntity> findTasksByProjectId(Long projectId) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return taskstableentityRepository.findByProjectId(projectId);
    }

    @Override
    public void addSponsorship(Long projectId, String name, BigDecimal value) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        SponsorshipTableEntity newSponsorship = new SponsorshipTableEntity();
        newSponsorship.setProjectId(projectId);
        newSponsorship.setName(name);
        newSponsorship.setValue(value);
        try{
            sponsorshiptableentityRepository.save(newSponsorship);
        }catch (DataIntegrityViolationException ex){
            addSponsorship(projectId, name, value);
        }
    }

    @Override
    public int findUnassignedFunds(Long projectId) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        int assignedFunds = 0;
        for(SponsorshipTableEntity e : sponsorshiptableentityRepository.findByProjectId(projectId)){
            assignedFunds+=e.getValue().intValue();
        }
        return (projecttableentityRepository.findOneByProjectId(projectId).getSponsorship().intValue() - assignedFunds);
    }

    @Override
    public int countTasksDelayed(Long projectId) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        Date currentDate = new Date(new java.util.Date().getTime());
        return taskstableentityRepository.countByProjectIdAndDeadlineBefore(projectId, currentDate);
    }

    @Override
    public ProjectTableEntity updateProjectEnd(Long projectId, Date newEndDate) throws EntityNotFoundException {
        if(!projecttableentityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
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
