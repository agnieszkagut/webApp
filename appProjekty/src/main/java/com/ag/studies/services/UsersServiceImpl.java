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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserTableEntityRepository usertableentityRepository;
    @Autowired
    private RolesTableEntityRepository rolestableentityRepository;
    @Autowired
    private ConfigTableEntityRepository configtableentityRepository;
    @Autowired
    private ProjectTableEntityRepository projecttableentityRepository;

    @Override
    public List<UserTableEntity> findListOfUsers() {
        return usertableentityRepository.findAll();
    }

    @Override
    public List<User> cutUsers(List<UserTableEntity> all){
        List<User> userList = new ArrayList<>();
        for(UserTableEntity us : all){
            userList.add(new User(us.getEmail(), us.getRealname()));
        }
        return userList;
    }

    @Override
    public Config cutConfig(ConfigTableEntity updateUser) {
        return new Config(updateUser.getConfigId(), updateUser.getUserId(), updateUser.getProjectId(), updateUser.getAccessLevel());
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


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User{
        String email;
        String realname;
    }

    @Override
    public UserTableEntity findByUsername(String username) throws EntityNotFoundException {
        UserTableEntity result = usertableentityRepository.findByUsername(username);
        if(result == null) throw new EntityNotFoundException(UserTableEntity.class, "username", username);
        return result;
    }
    @Override
    public String addUser(String email, String username, String realname, String encodedPassword, String position) throws AlreadyExistsException {
        for(UserTableEntity u: usertableentityRepository.findAll()){
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
        try{
            Long userId = usertableentityRepository.save(user).getUserId();
            addRole(username, position, userId);
        }catch (DataIntegrityViolationException ex){
            addUser(email, username, realname, encodedPassword, position);
        }

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
        role.setUserTableByUsername(usertableentityRepository.getOne(userId));
        try {
            rolestableentityRepository.save(role);
        }catch (DataIntegrityViolationException ex){
            addRole(username, position, userId);
        }
    }

    @Override
    public ConfigTableEntity updateUser(String userEmail, String projectName) throws EntityNotFoundException {
        UserTableEntity user = usertableentityRepository.findByEmail(userEmail);
        if(user == null) throw new EntityNotFoundException(UserTableEntity.class, "userEmail", userEmail);
        Long userId = user.getUserId();
        ConfigTableEntity updatedConfig = configtableentityRepository.findByUserId(userId);
        Long projectId = projecttableentityRepository.findByNameEquals(projectName).getProjectId();
        updatedConfig.setProjectId(projectId);
        return configtableentityRepository.save(updatedConfig);
    }
}
