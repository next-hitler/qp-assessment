package com.example.vegetablevendor.services;


import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HelperServie {
    
    private static final Logger logger = LogManager.getLogger(HelperServie.class);


    public ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object data){

         Map<String, Object> map = new HashMap<>();
         map.put("message", message);
         map.put("status",status.value());
         if(data!=null){
            map.put("data",data);
         }

        return new ResponseEntity<Object>(map, status);
    }

    public ResponseEntity<Object> ErrorResponse(String message){

        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status",HttpStatus.BAD_REQUEST.value());


       return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
   }


}
