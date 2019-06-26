package com.ag.studies.services;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.*;
import com.ag.studies.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ProjectTableEntityRepository projectTableEntityRepository;
    @Autowired
    private SponsorshipTableEntityRepository sponsorshipTableEntityRepository;
    @Autowired
    private ConfigTableEntityRepository configTableEntityRepository;
    @Autowired
    private UserTableEntityRepository userTableEntityRepository;
    @Autowired
    private TasksTableEntityRepository tasksTableEntityRepository;
    @Autowired
    private MessagesServiceImpl messagesService;

    @Override
    public void messageToLeader(Long creatorId, Long projectLeader, String subject, String newMessage)  throws EntityNotFoundException {
        UserTableEntity user = userTableEntityRepository.getOne(projectLeader);
        if(user == null) throw new EntityNotFoundException(UserTableEntity.class, "projectLeader", projectLeader.toString());
        String email = user.getEmail();
        messagesService.addMessage(creatorId, email, subject, newMessage);
    }
    @Override
    public List<ProjectTableEntity> findAll() {
        return projectTableEntityRepository.findAll();
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(id).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", id.toString());
        for(ConfigTableEntity config : configTableEntityRepository.findByProjectId(id)){
            configTableEntityRepository.deleteById(config.getConfigId());
        }
        projectTableEntityRepository.deleteById(id);
    }

    @Override
    public long count() {
        return projectTableEntityRepository.count();
    }

    @Override
    public ProjectTableEntity updateProject(Long id, String new_status) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(id).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", id.toString());
        ProjectTableEntity updatedProject = projectTableEntityRepository.findById(id)
                .orElseThrow(() -> new NotFoundProjectEntity(id));
        updatedProject.setStatus(new_status);
        return projectTableEntityRepository.save(updatedProject);
    }
    @Override
    public long countEmployeesByProjectId(Long projectId) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return configTableEntityRepository.countByProjectId(projectId);
    }

    @Override
    public List<UserTableEntity> findEmployeesByProjectId(Long projectId) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        List<ConfigTableEntity> ConfigList = configTableEntityRepository.findByProjectId(projectId);
        List<UserTableEntity> Employees = new ArrayList<UserTableEntity>();
        for(ConfigTableEntity tmp : ConfigList){
            Employees.add(userTableEntityRepository.findByUserId(tmp.getUserId()));
        }
        return Employees;
    }
    @Override
    public List<UserTableEntity> findOtherEmployeesByProjectId(Long projectId) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        List<ConfigTableEntity> ConfigList = configTableEntityRepository.findByProjectIdNot(projectId);
        List<Long> Indexes = new ArrayList<Long>();
        for(ConfigTableEntity tmp : ConfigList){
            if(userTableEntityRepository.getOne(tmp.getUserId()).getPosition().equals("kierownik projektu")
                    || userTableEntityRepository.getOne(tmp.getUserId()).getPosition().equals("kierownik programu")
                    || userTableEntityRepository.getOne(tmp.getUserId()).getPosition().equals("admin")) {

            }
            else Indexes.add(tmp.getUserId());
        }
        List<UserTableEntity> Employees = new ArrayList<UserTableEntity>();
        for(Long index : Indexes){
            Employees.add(userTableEntityRepository.findByUserId(index));
        }
        return Employees;
    }
    @Override
    public UserTableEntity findProjectLeader(Long projectId) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        Long id = configTableEntityRepository.findByProjectIdAndAccessLevel(projectId, Long.valueOf(1)).getUserId();
        return userTableEntityRepository.getOne(id);
    }

    @Override
    public List<SponsorshipTableEntity> findSponsorships(Long projectId) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return sponsorshipTableEntityRepository.findByProjectId(projectId);
    }

    @Override
    public ProjectTableEntity findOneByProjectId(Long projectId)  throws EntityNotFoundException {
        ProjectTableEntity result = projectTableEntityRepository.findOneByProjectId(projectId);
        if(result == null) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return result;
    }

    @Override
    public long countTasksByProjectId(Long projectId) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return tasksTableEntityRepository.countByProjectId(projectId);
    }

    @Override
    public long countTasksByProjectIdByIsDone(Long projectId, Boolean i) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return tasksTableEntityRepository.countByProjectIdAndIsDone(projectId, i);
    }

    @Override
    public TasksTableEntity updateTask(Long taskId) throws EntityNotFoundException {
        if(!tasksTableEntityRepository.findById(taskId).isPresent()) throw new EntityNotFoundException(TasksTableEntity.class, "taskId", taskId.toString());
        TasksTableEntity updatedTask = tasksTableEntityRepository.getOne(taskId);
        if(updatedTask.getIsDone()){
            updatedTask.setIsDone(false);
        }
        else{
            updatedTask.setIsDone(true);
        }
        tasksTableEntityRepository.save(updatedTask);
        return updatedTask;
    }

    @Override
    public void addTask(Long projectId, String name, Date deadline) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        TasksTableEntity newTask = new TasksTableEntity();
        newTask.setProjectId(projectId);
        newTask.setIsDone(false);
        Timestamp ts=new Timestamp(deadline.getTime());
        newTask.setDeadline(ts);
        newTask.setName(name);
        tasksTableEntityRepository.save(newTask);
    }

    @Override
    public List<UserTableEntity> findLeaders() {
        return userTableEntityRepository.findByPosition("kierownik projektu");
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
        Long projectId = projectTableEntityRepository.save(newProject).getProjectId();
        Long userId = (userTableEntityRepository.findByEmail(leaderEmail).getUserId());
        addConfig(projectId, userId);
    }

    @Override
    public void addConfig(Long projectId, Long userId){
        ConfigTableEntity newConfig = new ConfigTableEntity();
        newConfig.setProjectId(projectId);
        newConfig.setUserId(userId);
        newConfig.setAccessLevel(1L);
        newConfig.setUserTableByUserId(userTableEntityRepository.getOne(userId));
        newConfig.setProjectTableByProjectId(projectTableEntityRepository.getOne(projectId));
        configTableEntityRepository.save(newConfig);
    }

    @Override
    public ProjectTableEntity findOnebyLeaderId(Long leaderId) throws EntityNotFoundException {
        ConfigTableEntity config = configTableEntityRepository.findByUserIdAndAccessLevel(leaderId, Long.valueOf(1));
        if(config == null) throw new EntityNotFoundException(ConfigTableEntity.class, "leaderId", leaderId.toString());
        Long projectId = config.getProjectId();
        return projectTableEntityRepository.getOne(projectId);
    }

    @Override
    public void updateProjectStatus(String status) {
        for(ProjectTableEntity p: projectTableEntityRepository.findAll()){
            p.setStatus(status);
        }
    }

    @Override
    public Long getProjectId(Long creatorId, String recipientEmail) {
        Long id;
        if(userTableEntityRepository.getOne(creatorId).getPosition().equals("kierownik programu")){
            id = configTableEntityRepository.findByUserId(
                            userTableEntityRepository.findByEmailEquals(
                                    recipientEmail)
                                    .getUserId())
                            .getProjectId();
        }
        else{
                id = configTableEntityRepository.findByUserId(creatorId)
                                .getProjectId();
        }
        return id;
    }

    @Override
    public List<TasksTableEntity> findTasksByProjectId(Long projectId) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        return tasksTableEntityRepository.findByProjectId(projectId);
    }

    @Override
    public void addSponsorship(Long projectId, String name, BigDecimal value) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        SponsorshipTableEntity newSponsorship = new SponsorshipTableEntity();
        newSponsorship.setProjectId(projectId);
        newSponsorship.setName(name);
        newSponsorship.setValue(value);
        sponsorshipTableEntityRepository.save(newSponsorship);
    }

    @Override
    public int findUnassignedFunds(Long projectId) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        int assignedFunds = 0;
        for(SponsorshipTableEntity e : sponsorshipTableEntityRepository.findByProjectId(projectId)){
            assignedFunds+=e.getValue().intValue();
        }
        return (projectTableEntityRepository.findOneByProjectId(projectId).getSponsorship().intValue() - assignedFunds);
    }

    @Override
    public int countTasksDelayed(Long projectId) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        Date currentDate = new Date(new java.util.Date().getTime());
        return tasksTableEntityRepository.countByProjectIdAndDeadlineBefore(projectId, currentDate);
    }

    @Override
    public ProjectTableEntity updateProjectEnd(Long projectId, Date newEndDate) throws EntityNotFoundException {
        if(!projectTableEntityRepository.findById(projectId).isPresent()) throw new EntityNotFoundException(ProjectTableEntity.class, "projectId", projectId.toString());
        ProjectTableEntity updatedProject = new ProjectTableEntity();
        Timestamp ts=new Timestamp(newEndDate.getTime());
        updatedProject.setEndDate(ts);
        projectTableEntityRepository.save(updatedProject);
        return updatedProject;
    }

    private class NotFoundProjectEntity extends RuntimeException  {
        public NotFoundProjectEntity(Long id) {
            super("Could not find the project with given id" + id );
        }
    }
}
