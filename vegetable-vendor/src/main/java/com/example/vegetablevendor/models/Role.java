package com.example.vegetablevendor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "role")
@Data
public class Role {
  @Id
  private String id;

  private ERole name;

}
