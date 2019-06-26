package com.ag.studies.controllers;

import com.ag.studies.EntityNotFoundException;
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

    @GetMapping("/{username}")
    public int login(@PathVariable(value = "username") String username) throws EntityNotFoundException {
        return loginService.login(username);
    }


}
