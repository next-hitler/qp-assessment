package com.example.vegetablevendor.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class BookingResponse {
    
    private List<String> itemsBooked;
    private float finalCost;
    
}
