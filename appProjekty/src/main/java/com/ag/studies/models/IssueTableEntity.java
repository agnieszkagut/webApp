package com.ag.studies.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"date_submitted"},
        allowGetters = true)
@Table(name = "issue_table", schema = "projekty")
public class IssueTableEntity {
    private Long issueId;
    private Long projectId;
    private Long userId;
    private String title;
    private String status;
    private Timestamp dateSubmitted;
    private Timestamp lastUpdated;
    private ProjectTableEntity projectTableByProjectId;
    private UserTableEntity userTableByUserId;

    @Id
    @Column(name = "issue_id", nullable = false)
    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
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
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "status", nullable = false, length = 32)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @CreatedDate
    @Column(name = "date_submitted", nullable = false)
    public Timestamp getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Timestamp dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    @Basic
    @Column(name = "last_updated", nullable = false)
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueTableEntity that = (IssueTableEntity) o;
        return Objects.equals(issueId, that.issueId) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(title, that.title) &&
                Objects.equals(dateSubmitted, that.dateSubmitted) &&
                Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueId, projectId, userId, status, title, dateSubmitted, lastUpdated);
    }

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false, insertable=false, updatable=false)
    public ProjectTableEntity getProjectTableByProjectId() {
        return projectTableByProjectId;
    }

    public void setProjectTableByProjectId(ProjectTableEntity projectTableByProjectId) {
        this.projectTableByProjectId = projectTableByProjectId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    public UserTableEntity getUserTableByUserId() {
        return userTableByUserId;
    }

    public void setUserTableByUserId(UserTableEntity userTableByUserId) {
        this.userTableByUserId = userTableByUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
