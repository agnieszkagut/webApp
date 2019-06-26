package com.ag.studies.services;

import com.ag.studies.AlreadyExistsException;
import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.ConfigTableEntity;
import com.ag.studies.models.RolesTableEntity;
import com.ag.studies.models.UserTableEntity;
import com.ag.studies.repositories.ConfigTableEntityRepository;
import com.ag.studies.repositories.ProjectTableEntityRepository;
import com.ag.studies.repositories.RolesTableEntityRepository;
import com.ag.studies.repositories.UserTableEntityRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserTableEntityRepository userTableEntityRepository;
    @Autowired
    private RolesTableEntityRepository rolesTableEntityRepository;
    @Autowired
    private ConfigTableEntityRepository configTableEntityRepository;
    @Autowired
    private ProjectTableEntityRepository projectTableEntityRepository;

    @Override
    public List<UserTableEntity> findListOfUsers() {
        return userTableEntityRepository.findAll();
    }

    @Override
    public UserTableEntity findByUsername(String username) throws EntityNotFoundException {
        UserTableEntity result = userTableEntityRepository.findByUsername(username);
        if(result == null) throw new EntityNotFoundException(UserTableEntity.class, "username", username);
        return result;
    }
    @Override
    public String addUser(String email, String username, String realname, String encodedPassword, String position) throws AlreadyExistsException {
        for(UserTableEntity u: userTableEntityRepository.findAll()){
            if(email.equals(u.getEmail())) throw new AlreadyExistsException("email", UserTableEntity.class, email);
            if(username.equals(u.getUsername())) throw new AlreadyExistsException("username", UserTableEntity.class, username);
        }
        UserTableEntity user = new UserTableEntity();
        user.setUsername(username);
        user.setRealname(realname);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setPosition(position);
        user.setAccessLevel(position);
        user.setLostPasswordRequestCount(0);
        Long userId = userTableEntityRepository.save(user).getUserId();
        addRole(username, position, userId);
        return "ok";
    }

    @Override
    public void addRole(String username, String position, Long userId){
        RolesTableEntity role = new RolesTableEntity();
        role.setUsername(username);
        if(position.equals("kierownik projektu") || position.equals("admin") || position.equals("kierownik programu")){role.setRole(position);}
        else{
            role.setRole("interesariusz");
        }
        role.setUserTableByUsername(userTableEntityRepository.getOne(userId));
        rolesTableEntityRepository.save(role);
        addRole(username, position, userId);
    }

    @Override
    public ConfigTableEntity updateUser(String userEmail, String projectName) throws EntityNotFoundException {
        UserTableEntity user = userTableEntityRepository.findByEmail(userEmail);
        if(user == null) throw new EntityNotFoundException(UserTableEntity.class, "userEmail", userEmail);
        Long userId = user.getUserId();
        ConfigTableEntity updatedConfig = configTableEntityRepository.findByUserId(userId);
        Long projectId = projectTableEntityRepository.findByNameEquals(projectName).getProjectId();
        updatedConfig.setProjectId(projectId);
        return configTableEntityRepository.save(updatedConfig);
    }
}
