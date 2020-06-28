package com.pma.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Meal {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date dateAndTime;
    @Ignore
    private ArrayList<GroceryAndAmountPair> groceryAndAmountPairs = new ArrayList<>();

    private String type;

    //ovo se mozda bude racunalo al za sada ovako
    private float totalKcal;
    private float totalProtein;
    private float totalCarb;
    private float totalFat;
    private boolean isSynced = false;

    public String getDateString(){
        return new SimpleDateFormat("dd-MM-yyyy").format(dateAndTime);
    }

    public String getTimeString(){
        return new SimpleDateFormat("HH:mm").format(dateAndTime);
    }

    public void addGroceryAmountPair(GroceryAndAmountPair pair){
        if(groceryAndAmountPairs == null){
            groceryAndAmountPairs = new ArrayList<>();
        }
        groceryAndAmountPairs.add(pair);
        totalKcal += pair.getAmount() * pair.getGrocery().getKcalPer100gr()/100;
        totalCarb += pair.getAmount() * pair.getGrocery().getCarbPer100gr()/100;
        totalProtein += pair.getAmount() * pair.getGrocery().getProteinPer100gr()/100;
        totalFat += pair.getAmount() * pair.getGrocery().getFatPer100gr()/100;
    }

    public void removeGroceryAmountPair(GroceryAndAmountPair pair){
        this.groceryAndAmountPairs.remove(pair);

        totalKcal -= pair.getAmount() * pair.getGrocery().getKcalPer100gr()/100;
        totalCarb -= pair.getAmount() * pair.getGrocery().getCarbPer100gr()/100;
        totalProtein -= pair.getAmount() * pair.getGrocery().getProteinPer100gr()/100;
        totalFat -= pair.getAmount() * pair.getGrocery().getFatPer100gr()/100;
    }

    public void setMealId(long id){
        for (GroceryAndAmountPair pair : groceryAndAmountPairs) {
            pair.setMealId((int)id);
        }
    }

    public void setSynced(boolean synced){
        this.isSynced = synced;
    }
}
