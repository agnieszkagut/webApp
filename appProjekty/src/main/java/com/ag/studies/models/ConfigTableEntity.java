package com.ag.studies.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "config_table", schema = "projekty", catalog = "")
public class ConfigTableEntity {
    private Long configId;
    private Long userId;
    private Long projectId;
    private Long accessLevel;
    private UserTableEntity userTableByUserId;
    private ProjectTableEntity projectTableByProjectId;

    @Id
    @Column(name = "config_id", nullable = false)
    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
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
    @Column(name = "project_id", nullable = false)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "access_level", nullable = false)
    public Long getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Long accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigTableEntity that = (ConfigTableEntity) o;
        return Objects.equals(configId, that.configId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(accessLevel, that.accessLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configId, userId, projectId, accessLevel);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable=false, updatable=false)
    public UserTableEntity getUserTableByUserId() {
        return userTableByUserId;
    }

    public void setUserTableByUserId(UserTableEntity userTableByUserId) {
        this.userTableByUserId = userTableByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false, insertable=false, updatable=false)
    public ProjectTableEntity getProjectTableByProjectId() {
        return projectTableByProjectId;
    }

    public void setProjectTableByProjectId(ProjectTableEntity projectTableByProjectId) {
        this.projectTableByProjectId = projectTableByProjectId;
    }
}
