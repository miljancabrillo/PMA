package com.pma;


import com.pma.model.Activity;
import com.pma.model.DailySummary;
import com.pma.model.Grocery;
import com.pma.model.GroceryAndAmountPair;
import com.pma.model.Meal;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class DataMock {

    private static DataMock instance;
    private ArrayList<DailySummary> dailySummaries;
    private ArrayList<Activity> activities;
    private ArrayList<Grocery> groceries;
    private ArrayList<Meal> meals;


    private DataMock(){
        DailySummary ds1 = new DailySummary(new Date(), 2100, 2330);
        DailySummary ds2 = new DailySummary(new Date(), 2200, 2230);
        DailySummary ds3 = new DailySummary(new Date(), 2800, 2390);
        dailySummaries = new ArrayList<>();
        dailySummaries.add(ds1);
        dailySummaries.add(ds2);
        dailySummaries.add(ds3);

        Activity a1 = new Activity("Running", new Date(), 10, 89);
        Activity a2 = new Activity("Weightlifting", new Date(), 50, 200);
        Activity a3 = new Activity("Swimming", new Date(), 30, 250);
        activities = new ArrayList<>();
        activities.add(a1);
        activities.add(a2);
        activities.add(a3);

        groceries = new ArrayList<>();
        Grocery g1 = new Grocery(1, "Chicken breast", 129, 23, 0, 5);
        Grocery g2 = new Grocery(2, "Oats", 390, 17, 59, 0);
        Grocery g3 = new Grocery(3, "Ananas", 60, 0, 13, 0);
        groceries.add(g1);
        groceries.add(g2);
        groceries.add(g3);

        meals = new ArrayList<>();
        Meal m1 = new Meal(1, new Date(), null, 400, 20, 30, 10);
        m1.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(1), 100));
        m1.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(2), 100));

        Meal m2 = new Meal(2, new Date(), null, 900, 70, 30, 10);
        m2.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(0), 100));
        m2.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(2), 100));

        meals.add(m1);
        meals.add(m2);

    }

    public static synchronized DataMock getInstance(){
        if(instance == null){
            instance = new DataMock();
        }
        return instance;
    }
}