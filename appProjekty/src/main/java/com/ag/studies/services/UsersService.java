package com.ag.studies.services;

import com.ag.studies.AlreadyExistsException;
import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.ConfigTableEntity;
import com.ag.studies.models.UserTableEntity;

import java.util.List;

public interface UsersService {
    UserTableEntity findByUsername(String username) throws EntityNotFoundException;
    String addUser(String email, String username, String realname, String encodedPassword, String position) throws AlreadyExistsException;
    List<UserTableEntity> findListOfUsers();
    ConfigTableEntity updateUser(String userEmail, String projectName) throws EntityNotFoundException;
    void addRole(String username, String position, Long userId);
}
