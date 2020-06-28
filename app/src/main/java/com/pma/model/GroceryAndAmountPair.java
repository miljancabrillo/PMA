package com.pma.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class GroceryAndAmountPair {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @Ignore
    private Grocery grocery;
    private int groceryId;
    private int mealId;
    private float amount;
    private boolean isSynced = false;

    public GroceryAndAmountPair(Grocery grocery, float amount){
        this.grocery = grocery;
        this.groceryId = grocery.getId();
        this.amount = amount;
    }

    public  float getKcals(){
        return amount * grocery.getKcalPer100gr()/100;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        GroceryAndAmountPair pair = (GroceryAndAmountPair) obj;
        if(pair.getGrocery().equals(this.grocery) && pair.getAmount() == this.amount) return true;
        return false;
    }

    public void setSynced(boolean synced){
        this.isSynced = synced;
    }
}
