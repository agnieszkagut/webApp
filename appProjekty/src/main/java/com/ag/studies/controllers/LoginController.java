package com.ag.studies.controllers;

import com.ag.studies.models.UserTableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ag.studies.services.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/login")
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/{username}")
    public int login(@PathVariable(value = "username") String username){

        String text = loginService.login(username);
        int userRole=4;
        if(text.equals("admin")) userRole = 0;
        else {
            if(text.equals("kierownik programu")) userRole = 1;
            else{
                if(text.equals("kierownik projektu")) userRole = 2;
                else userRole = 3;
            }
        }
        logger.info("Zalogowano użytkownika: "+ username);
        return userRole;
    }

    @GetMapping("/userInfo/{username}")
    public UserTableEntity getUser(@PathVariable(value = "username") String username){
        return loginService.findByUsername(username);
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody NewUser userEntity){
        String encodedPassword = bCryptPasswordEncoder.encode(userEntity.getPassword());
        String logInStatus = loginService.addUser(userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getRealname(),
                encodedPassword,
                userEntity.getPosition());
        logger.warn("Efekt dodawania użytkownika: "+ logInStatus);
        return logInStatus;
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
