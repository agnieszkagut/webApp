package com.ag.studies.models;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tasks_table", schema = "projekty")
public class TasksTableEntity {
    private Long taskId;
    private Long projectId;
    private String name;
    private Timestamp deadline;
    private Boolean isDone;
    private ProjectTableEntity tasksTableByProjectId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "project_id", nullable = false)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "deadline", nullable = false)
    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    @Basic
    @Column(name = "is_done", nullable = false)
    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksTableEntity that = (TasksTableEntity) o;
        return Objects.equals(taskId, that.taskId) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(deadline, that.deadline) &&
                Objects.equals(isDone, that.isDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, projectId, name, deadline, isDone);
    }

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false, insertable = false, updatable = false)
    public ProjectTableEntity getTasksTableByProjectId() {
        return tasksTableByProjectId;
    }

    public void setTasksTableByProjectId(ProjectTableEntity tasksTableByProjectId) {
    }
}
