package com.ag.studies.controllers;

import com.ag.studies.AlreadyExistsException;
import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.ConfigTableEntity;
import com.ag.studies.models.UserTableEntity;
import com.ag.studies.services.UsersServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UsersController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UsersServiceImpl usersService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public List<UserTableEntity.User> getListOfUsers(){

        return UserTableEntity.mapToUser(usersService.findListOfUsers());
    }

    @GetMapping("/userInfo/{username}")
    public UserInfo getUser(@PathVariable(value = "username") String username) throws EntityNotFoundException {
        UserTableEntity user = usersService.findByUsername(username);
        UserInfo userInfo = new UserInfo(user.getUserId(), user.getUsername(), user.getPosition());
        return userInfo;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UserInfo{
        Long userId;
        String username;
        String position;
    }

    @PutMapping("/{username}")
    public ConfigTableEntity.Config updateUser(@PathVariable(value = "username") String username, @RequestBody String project) throws EntityNotFoundException{
        return ConfigTableEntity.mapToConfig(usersService.updateUser(username, project));
    }

    @PostMapping
    public void addUser(@RequestBody NewUser userEntity) throws EntityNotFoundException, AlreadyExistsException{
        String encodedPassword = bCryptPasswordEncoder.encode(userEntity.getPassword());
        String logInStatus = usersService.addUser(userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getRealname(),
                encodedPassword,
                userEntity.getPosition());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class NewUser{
        String email;
        String username;
        String realname;
        String password;
        String position;
    }
}
