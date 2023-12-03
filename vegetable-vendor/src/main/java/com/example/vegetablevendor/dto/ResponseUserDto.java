package com.example.vegetablevendor.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseUserDto {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private List<Object> rolesPremission;
    private String token;

}
