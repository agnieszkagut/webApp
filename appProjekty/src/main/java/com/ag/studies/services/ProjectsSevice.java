package com.ag.studies.services;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ProjectsSevice {

    void messageToLeader(Long creatorId, Long projectLeader, String subject, String newMessage) throws EntityNotFoundException;

    List<ProjectTableEntity> findAll();

    void delete(Long id) throws EntityNotFoundException;

    long count();

    ProjectTableEntity updateProject(Long id, String new_status) throws EntityNotFoundException;

    long countEmployeesByProjectId(Long projectId) throws EntityNotFoundException;

    List<UserTableEntity> findEmployeesByProjectId(Long projectId) throws EntityNotFoundException;

    UserTableEntity findProjectLeader(Long projectId) throws EntityNotFoundException;

    List<SponsorshipTableEntity> findSponsorships(Long projectId) throws EntityNotFoundException;

    ProjectTableEntity findOneByProjectId(Long projectId) throws EntityNotFoundException;

    long countTasksByProjectId(Long projectId) throws EntityNotFoundException;

    List<TasksTableEntity> findTasksByProjectId(Long projectId) throws EntityNotFoundException;

    void addSponsorship(Long projectId, String name, BigDecimal value) throws EntityNotFoundException;

    int findUnassignedFunds(Long projectId) throws EntityNotFoundException;

    int countTasksDelayed(Long projectId) throws EntityNotFoundException;

    ProjectTableEntity updateProjectEnd(Long projectId, Date newEndDate) throws EntityNotFoundException;

    long countTasksByProjectIdByIsDone(Long projectId, Boolean i) throws EntityNotFoundException;

    TasksTableEntity updateTask(Long taskId) throws EntityNotFoundException;

    void addTask(Long projectId, String name, Date deadline) throws EntityNotFoundException;

    List<UserTableEntity> findLeaders();

    void addProject(String leaderEmail, String name, String description, BigDecimal sponsorship, Date endDate);

    void addConfig(Long projectId, Long userId);

    ProjectTableEntity findOnebyLeaderId(Long leaderId) throws EntityNotFoundException;

    void updateProjectStatus(String status);

    Long getProjectId(Long creatorId, String recipientEmail);

    List<UserTableEntity> findOtherEmployeesByProjectId(Long projectId) throws EntityNotFoundException;
}
