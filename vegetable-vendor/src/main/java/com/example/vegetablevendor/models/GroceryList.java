package com.example.vegetablevendor.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "groceryList")
public class GroceryList {

    @Id
    private String id;

    
    private String groceryItem;
    private float productPrice;
    private Integer quantity; 
    
}
