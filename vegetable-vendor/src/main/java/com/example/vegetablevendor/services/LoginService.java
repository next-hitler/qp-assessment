package com.example.vegetablevendor.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.vegetablevendor.models.User;
import com.example.vegetablevendor.repositories.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    private HelperServie helperService;
    
    public ResponseEntity<Object> authenticateUser(String email){
        Optional<User> user = userRepository.findByEmail(email);

        if(user!=null){

        }

        return helperService.generateResponse("Login Successfull!",HttpStatus.OK, null);

    }
}
