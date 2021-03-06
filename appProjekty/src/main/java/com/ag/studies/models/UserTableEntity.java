package com.ag.studies.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"date_created", "last_visited"},
        allowGetters = true)
@Table(name = "user_table", schema = "projekty")
public class UserTableEntity {
    private Long userId;
    private String username;
    private String realname;
    private String email;
    private String password;
    private String position;
    private String accessLevel;
    private Timestamp lastVisit;
    private Timestamp dateCreated;
    private Integer lostPasswordRequestCount;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 191)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "realname", nullable = false, length = 191)
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 191)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "access_level", nullable = false)
    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
    @Basic
    @Column(name = "position", nullable = false)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @CreatedDate
    @Column(name = "last_visit", nullable = false)
    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }

    @Basic
    @CreatedDate
    @Column(name = "date_created", nullable = false)
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "lost_password_request_count", nullable = true)
    public Integer getLostPasswordRequestCount() {
        return lostPasswordRequestCount;
    }

    public void setLostPasswordRequestCount(Integer lostPasswordRequestCount) {
        this.lostPasswordRequestCount = lostPasswordRequestCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTableEntity that = (UserTableEntity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(realname, that.realname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(position, that.position) &&
                Objects.equals(lastVisit, that.lastVisit) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(lostPasswordRequestCount, that.lostPasswordRequestCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, realname, email, password, position, lastVisit, dateCreated, lostPasswordRequestCount);
    }

    @Mapper
    public interface UserMapper {
        UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
        User userEntityToUser(UserTableEntity user);
        List<User> userEntityToUser(List<UserTableEntity> users);
    }

    public static List<User> mapToUser(List<UserTableEntity> users){
        return UserMapper.INSTANCE.userEntityToUser(users);
    }
    public static User mapToUser(UserTableEntity user){
        return UserMapper.INSTANCE.userEntityToUser(user);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User{
        String email;
        String realname;
    }
}
