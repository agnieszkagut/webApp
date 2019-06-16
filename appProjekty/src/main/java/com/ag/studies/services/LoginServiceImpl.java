package com.ag.studies.services;

import com.ag.studies.models.RolesTableEntity;
import com.ag.studies.models.UserTableEntity;
import com.ag.studies.repositories.RolesTableEntityRepository;
import com.ag.studies.repositories.UserTableEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.System.out;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserTableEntityRepository usertableentityRepository;
    @Autowired
    private RolesTableEntityRepository rolestableentityRepository;

    @Override
    public String login(String username) {
        return usertableentityRepository.findByUsername(username).getPosition();
    }
    @Override
    public UserTableEntity findByUsername(String username){
        return usertableentityRepository.findByUsername(username);
    }
    @Override
    public String addUser(String email, String username, String realname, String encodedPassword, String position) {
        for(UserTableEntity u: usertableentityRepository.findAll()){
            if(email.equals(u.getEmail()) || username.equals(u.getUsername())) return "error";
        }
        UserTableEntity user = new UserTableEntity();
        Long id = usertableentityRepository.count()+1;
        user.setUserId(id);
        user.setUsername(username);
        user.setRealname(realname);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setPosition(position);
        user.setAccessLevel(position);
        user.setLostPasswordRequestCount(0);
        usertableentityRepository.save(user);
        RolesTableEntity role = new RolesTableEntity();
        role.setRole_id(rolestableentityRepository.count()+1);
        role.setUsername(username);
        if(position.equals("kierownik projektu") || position.equals("admin") || position.equals("kierownik programu")){role.setRole(position);}
        else{
            role.setRole("interesariusz");
        }
        role.setUserTableByUsername(usertableentityRepository.getOne(id));
        rolestableentityRepository.save(role);
        return "ok";
    }
}
