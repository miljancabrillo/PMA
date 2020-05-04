package com.pma.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meal {


    private int id;
    private Date dateAndTime;
    private ArrayList<GroceryAndAmountPair> groceryAndAmountPairs;
    private String type;

    //ovo se mozda bude racunalo al za sada ovako
    private float totalKcal;
    private float totalProtein;
    private float totalCarb;
    private float totalFat;

    public String getDateString(){
        return new SimpleDateFormat("dd-MM-yyyy").format(dateAndTime);
    }

    public String getTimeString(){
        return new SimpleDateFormat("hh:mm").format(dateAndTime);
    }

    public void addGroceryAmountPair(GroceryAndAmountPair pair){
        if(groceryAndAmountPairs == null){
            groceryAndAmountPairs = new ArrayList<>();
        }
        groceryAndAmountPairs.add(pair);
    }
}
