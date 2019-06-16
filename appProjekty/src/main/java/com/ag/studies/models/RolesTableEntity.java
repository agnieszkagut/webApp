package com.ag.studies.models;

import javax.persistence.*;

@Entity
@Table(name = "roles_table", schema = "projekty")
public class RolesTableEntity {
    private String role;
    private String username;
    private Long role_id;
    private UserTableEntity userTableByUsername;

    @Id
    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
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
    @Column(name = "role", nullable = false, length = 32)
    public String getRole() {
        return role;
    }

    public void setRole(String accessLevel) {
        this.role = accessLevel;
    }

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false, insertable=false, updatable=false)
    public UserTableEntity getUserTableByUsername() {
        return userTableByUsername;
    }

    public void setUserTableByUsername(UserTableEntity userTableByUsername) {
        this.userTableByUsername = userTableByUsername;
    }


}
