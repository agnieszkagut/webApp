package com.ag.studies.services;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.UserTableEntity;
import com.ag.studies.repositories.UserTableEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private enum Role {
        admin(0), kierownik_programu(1), kierownik_projektu(2);
        private int value;
        Role(int value) { this.value = value; }
    }


    @Autowired
    private UserTableEntityRepository userTableEntityRepository;

    @Override
    public int login(String username) throws EntityNotFoundException {
        if(userTableEntityRepository.findByUsername(username) == null) throw new EntityNotFoundException(UserTableEntity.class, "username", username);
        String text = userTableEntityRepository.findByUsername(username).getPosition();
        try{
            return Role.valueOf(text.replaceAll(" ", "_")).value;
        }
        //Low-level user, with position not listed in the enum
        catch(IllegalArgumentException ex){
            return 3;
        }
    }
}
