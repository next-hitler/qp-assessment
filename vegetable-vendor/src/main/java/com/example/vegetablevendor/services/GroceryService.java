package com.example.vegetablevendor.services;



import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.vegetablevendor.models.GroceryList;
import com.example.vegetablevendor.repositories.GroceryListRepository;

@Service
public class GroceryService {

    @Autowired
    private GroceryListRepository groceryListRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    // public String addGrocery(GroceryList item){
    //      return "Item Added";
    // }

    public GroceryList updateGroceryList(GroceryList newItems,String type){
        if(type.equals("ITEMS")){
            newItems.setGroceryItem(newItems.getGroceryItem());
            newItems.setProductPrice(newItems.getProductPrice());
            // newItems.setQuantity(newItems.getQuantity());

        }
        if(type.equals("INVENTORY")){
            newItems.setQuantity(newItems.getQuantity());
        }
        return groceryListRepository.save(newItems);
        
    }
    
    public void deleteGrocery(String itemId){
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("_id").is(new ObjectId(itemId)));
        query.addCriteria(criteria);
        mongoTemplate.remove(query,"groceryList");

    }
}
