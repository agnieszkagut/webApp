package com.ag.studies.services;


import com.ag.studies.models.UserTableEntity;

public interface LoginService {
    String login(String username);
    UserTableEntity findByUsername(String username);
    String addUser(String email, String username, String realname, String encodedPassword, String position);
}
