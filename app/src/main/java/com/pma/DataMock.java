package com.pma;



import com.pma.model.Activity;
import com.pma.model.ActivityType;
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
    private ArrayList<ActivityType> activityTypes;


    private DataMock(){
        DailySummary ds1 = new DailySummary(new Date(), 2100f, 2330f, 2600f, 2660f, 2880f);
        DailySummary ds2 = new DailySummary(new Date(), 2200f, 2230f, 2200f, 2750f, 2650f);
        DailySummary ds3 = new DailySummary(new Date(), 2800f, 2390f, 2450f, 2500f, 2740f);
        dailySummaries = new ArrayList<>();
        dailySummaries.add(ds1);
        dailySummaries.add(ds2);
        dailySummaries.add(ds3);
        /*dailySummaries.add(ds4);
        dailySummaries.add(ds5);*/

        Activity a1 = new Activity("Running", new Date(), 10, 89);
        Activity a2 = new Activity("Weightlifting", new Date(), 50, 200);
        Activity a3 = new Activity("Swimming", new Date(), 30, 250);
        Activity a4 = new Activity("Bowling", new Date(), 30, 50);
        activities = new ArrayList<>();
        activities.add(a1);
        activities.add(a2);
        activities.add(a3);
        activities.add(a4);

        groceries = new ArrayList<>();
        Grocery g1 = new Grocery(1, "Chicken breast", 129, 23, 0, 5,false);
        Grocery g2 = new Grocery(2, "Oats", 390, 17, 59, 0,false);
        Grocery g3 = new Grocery(3, "Ananas", 60, 0, 13, 0,false);
        groceries.add(g1);
        groceries.add(g2);
        groceries.add(g3);

        meals = new ArrayList<>();
        Meal m1 = new Meal(1, new Date(), null,"Breakfast", 400, 20, 30, 10);
        m1.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(1), 100));
        m1.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(2), 100));
        m1.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(0), 200));


        Meal m2 = new Meal(2, new Date(), null, "Lunch", 900, 70, 30, 10);
        m2.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(0), 100));
        m2.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(2), 100));

        Meal m3 = new Meal(2, new Date(), null, "Dinner", 700, 70, 30, 10);
        m2.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(0), 100));
        m2.addGroceryAmountPair(new GroceryAndAmountPair(groceries.get(2), 100));

        meals.add(m1);
        meals.add(m2);
        meals.add(m3);

        activityTypes = new ArrayList<>();
        ActivityType at1 = new ActivityType("Running");
        ActivityType at2 = new ActivityType("Swimming");
        ActivityType at3 = new ActivityType("Bowling");
        ActivityType at4 = new ActivityType("Cycling");
        ActivityType at5 = new ActivityType("Weightlifting");

        activityTypes.add(at1);
        activityTypes.add(at2);
        activityTypes.add(at3);
        activityTypes.add(at4);
        activityTypes.add(at5);

    }

    public static synchronized DataMock getInstance(){
        if(instance == null){
            instance = new DataMock();
        }
        return instance;
    }
}