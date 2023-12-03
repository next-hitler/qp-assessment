package com.example.vegetablevendor.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vegetablevendor.models.GroceryList;
import com.example.vegetablevendor.payload.request.BookingItems;
import com.example.vegetablevendor.payload.response.BookingResponse;
import com.example.vegetablevendor.repositories.GroceryListRepository;
import com.example.vegetablevendor.services.GroceryService;
import com.example.vegetablevendor.services.HelperServie;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/grocery")
public class GroceryController {
    
    @Autowired
    private GroceryListRepository groceryListRepository;

    @Autowired
    private GroceryService groceryService;

    @Autowired
    private HelperServie helperService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/admin/fetchAllGrocery")
    @Secured("ADMIN")
    public ResponseEntity<Object> viewGrocery(){
        
        List<GroceryList> result = groceryListRepository.findAll();
        return helperService.generateResponse("Gorcery List Fetched", HttpStatus.OK, result);
    }
    
    @PostMapping("/admin/addGrocery")
   @Secured("ADMIN")
    public ResponseEntity<Object> addGrocery(@RequestBody GroceryList item){
        
        groceryListRepository.save(item);
        
        return helperService.generateResponse("Item added To Grocery List", HttpStatus.OK, null);

    }

    @PostMapping("/admin/updateGrocery")
   @Secured("ADMIN")
    public ResponseEntity<Object> update(@RequestBody GroceryList updateItem){
         
        Optional<GroceryList> updatingItem = groceryListRepository.findById(updateItem.getId());

        if(updatingItem == null){
            return helperService.ErrorResponse("Item Not Found");
        }else{
            GroceryList ItemsNew = groceryService.updateGroceryList(updateItem,"ITEMS");
            System.out.println(ItemsNew);
            return helperService.generateResponse("Item Details updated", HttpStatus.OK, null);
        }
        
    }

    @PostMapping("/admin/updateInventory")
   @Secured("ADMIN")
    public ResponseEntity<Object> updateInventory(@RequestBody GroceryList updateInventory){
         
        Optional<GroceryList> updatingItem = groceryListRepository.findById(updateInventory.getId());

        if(updatingItem == null){
            return helperService.ErrorResponse("Item Not Found");
        }else{
            GroceryList ItemsNew = groceryService.updateGroceryList(updateInventory,"INVENTORY");
            System.out.println(ItemsNew);
            return helperService.generateResponse("Inventory updated", HttpStatus.OK, null);
        }
        
    }

    @PostMapping("/admin/deleteGrocery")
    @Secured("ADMIN")
    public ResponseEntity<Object> deleteGrocery(@RequestBody Map<String,String> body){
        
        String itemId = body.get("id");
        groceryService.deleteGrocery(itemId);
    
        return helperService.generateResponse("Item Deleted",HttpStatus.OK, null);

    }

    //User Responsibilities
  
    @GetMapping("/user/fetchAvailableGrocery")
    @Secured("USER")
    public ResponseEntity<Object> availableGrocery(){
        
        Query query = new Query(Criteria.where("quantity").gt(0));

        List<GroceryList> result = mongoTemplate.find(query,GroceryList.class,"groceryList");
        return helperService.generateResponse("Available Grocery Fetched", HttpStatus.OK, result);
    }

    @PostMapping("/user/bookingGrocery")
    @Secured("USER")
    public ResponseEntity<Object> bookingGrocery(@RequestBody BookingItems id) {
        List<String> availableItems = new ArrayList<String>();
        Float totalPriceItems=0f;
        for (String itemId: id.getId()){
            GroceryList data = groceryListRepository.findAllById(itemId);
            availableItems.add(data.getGroceryItem());
            totalPriceItems=totalPriceItems+data.getProductPrice();
        }

        BookingResponse response = new BookingResponse();
        response.setItemsBooked(availableItems);
        response.setFinalCost(totalPriceItems);
        
        

        if (availableItems.isEmpty()) {
            return helperService.ErrorResponse("No available items found for booking.");
            // ResponseEntity.status(HttpStatus.NOT_FOUND).body("No available items found for booking.");
        }

        // For simplicity, let's assume booking involves setting 'available' to false.
        // availableItems.forEach(item -> item.setAvailable(false));
        // groceryListRepository.saveAll(availableItems);

        return helperService.generateResponse("Items booked successfully.",HttpStatus.OK,response);
    }
}
