package com.ag.studies.services;


import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.UserTableEntity;

public interface LoginService {
    int login(String username) throws EntityNotFoundException;
}
