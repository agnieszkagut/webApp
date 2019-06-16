package com.ag.studies.services;

import com.ag.studies.models.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ProjectsSevice {

    List<ProjectTableEntity> findAll();

    void delete(Long id);

    long count();

    long countByStatus(String status);

    ProjectTableEntity updateProject(Long id, String new_status);

    long countEmployeesByProjectId(Long projectId);

    List<UserTableEntity> findEmployeesByProjectId(Long projectId);

    UserTableEntity findProjectLeader(Long projectId);

    ConfigTableEntity updateUser(String userEmail, String projectName);

    List<SponsorshipTableEntity> findSponsorships(Long projectId);

    ProjectTableEntity findOnebyProjectId(Long projectId);

    long countTasksByProjectId(Long projectId);

    List<TasksTableEntity> findTasksByProjectId(Long projectId);

    void addSponsorship(Long projectId, String name, BigDecimal value);

    int findUnassignedFunds(Long projectId);

    int countTasksDelayed(Long projectId);

    ProjectTableEntity updateProjectEnd(Long projectId, Date newEndDate);

    long countTasksByProjectIdByIsDone(Long projectId, Boolean i);

    TasksTableEntity updateTask(Long taskId);

    void addTask(Long projectId, String name, Date deadline);

    List<UserTableEntity> findLeaders();

    void addProject(String leaderEmail, String name, String description, BigDecimal sponsorship, Date endDate);

    ProjectTableEntity findOnebyLeaderId(Long leaderId);

    void updateProjectStatus(String status);

    String getProjectTitle(Long creatorId, String recipientEmail);

    List<UserTableEntity> findOtherEmployeesByProjectId(Long projectId);
}
