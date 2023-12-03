package com.example.vegetablevendor.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vegetablevendor.payload.request.LoginRequest;
import com.example.vegetablevendor.payload.response.JwtResponse;
import com.example.vegetablevendor.repositories.RoleRepository;
import com.example.vegetablevendor.repositories.UserRepository;
import com.example.vegetablevendor.security.jwt.JwtUtils;
import com.example.vegetablevendor.security.services.UserDetailsImpl;
import com.example.vegetablevendor.services.HelperServie;

// @CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    private HelperServie helperservice;
   
    @Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		// return ResponseEntity.ok(new JwtResponse(jwt, 
		// 										 userDetails.getId(), 
		// 										 userDetails.getUsername(), 
		// 										 userDetails.getEmail(), 
		// 										 roles));

        return helperservice.generateResponse("Login Successful",HttpStatus.OK, new JwtResponse(jwt, 
        userDetails.getId(), 
        userDetails.getUsername(), 
        userDetails.getEmail(), 
        roles));
    }

	

    
}
