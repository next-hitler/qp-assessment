package com.example.vegetablevendor.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.vegetablevendor.dto.LoginDto;
import com.example.vegetablevendor.models.AuthenticationRequest;
import com.example.vegetablevendor.models.User;
import com.example.vegetablevendor.repositories.UserRepository;
import ch.qos.logback.classic.Logger;

@Service
public class RBACService {

    @Autowired
    private HelperServie helperService;
    
    @Autowired
    private UserRepository userRepository;

    // private final UserRepository repository;
    
	// private final PasswordEncoder passwordEncoder;
    @Autowired
	// private JwtService jwtService;
	private AuthenticationManager authenticationManager;


    public ResponseEntity<Object> login(LoginDto logindto){

        return helperService.generateResponse("Login Successful!", HttpStatus.OK, logindto);
    }


    public Boolean validateUser(String email, String password){
        
        Optional<User> resdata =userRepository.findByEmail(email);
        if(resdata!=null){
           return true;
        }
        return false;
    }


    
}
