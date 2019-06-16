package com.ag.studies.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"date_modified"},
        allowGetters = true)
@Table(name = "issue_history_table", schema = "projekty")
public class IssueHistoryTableEntity {
    private Long issueHistoryId;
    private Long issueId;
    private Long userId;
    private String oldValue;
    private String newValue;
    private Timestamp dateModified;
    private IssueTableEntity issueTableByIssueId;
    private UserTableEntity userTableByUserId;

    @Id
    @Column(name = "issue_history_id", nullable = false)
    public Long getIssueHistoryId() {
        return issueHistoryId;
    }

    public void setIssueHistoryId(Long issueHistoryId) {
        this.issueHistoryId = issueHistoryId;
    }

    @Basic
    @Column(name = "issue_id", nullable = false)
    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
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
    @Column(name = "old_value", nullable = false, length = 255)
    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    @Basic
    @Column(name = "new_value", nullable = false, length = 255)
    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Basic
    @CreatedDate
    @Column(name = "date_modified", nullable = false)
    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueHistoryTableEntity that = (IssueHistoryTableEntity) o;
        return Objects.equals(issueHistoryId, that.issueHistoryId) &&
                Objects.equals(issueId, that.issueId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(oldValue, that.oldValue) &&
                Objects.equals(newValue, that.newValue) &&
                Objects.equals(dateModified, that.dateModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueHistoryId, issueId, userId, oldValue, newValue, dateModified);
    }

    @ManyToOne
    @JoinColumn(name = "issue_id", referencedColumnName = "issue_id", nullable = false, insertable=false, updatable=false)
    public IssueTableEntity getIssueTableByIssueId() {
        return issueTableByIssueId;
    }

    public void setIssueTableByIssueId(IssueTableEntity issueTableByIssueId) {
        this.issueTableByIssueId = issueTableByIssueId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    public UserTableEntity getUserTableByUserId() {
        return userTableByUserId;
    }

    public void setUserTableByUserId(UserTableEntity userTableByUserId) {
        this.userTableByUserId = userTableByUserId;
    }
}
