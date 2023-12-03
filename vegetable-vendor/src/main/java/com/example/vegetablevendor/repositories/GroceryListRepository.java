package com.example.vegetablevendor.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.vegetablevendor.models.GroceryList;

@Repository
public interface GroceryListRepository extends MongoRepository<GroceryList,String> {

    GroceryList findAllById(String itemIds);

    
    
}
