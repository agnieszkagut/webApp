package com.ag.studies.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "config_table", schema = "projekty")
public class ConfigTableEntity {
    private Long configId;
    private Long userId;
    private Long projectId;
    private Long accessLevel;
    private UserTableEntity userTableByUserId;
    private ProjectTableEntity projectTableByProjectId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    @Mapper
    public interface ConfigMapper {
        ConfigMapper INSTANCE = Mappers.getMapper( ConfigMapper.class );
        Config configEntityToConfig(ConfigTableEntity config);
        List<Config> configEntityToConfig(List<ConfigTableEntity> configs);
    }

    public static List<Config> mapToConfig(List<ConfigTableEntity> configs){
        return ConfigMapper.INSTANCE.configEntityToConfig(configs);
    }
    public static Config mapToConfig(ConfigTableEntity config){
        return ConfigMapper.INSTANCE.configEntityToConfig(config);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config{
        private Long configId;
        private Long userId;
        private Long projectId;
        private Long accessLevel;
    }
}
