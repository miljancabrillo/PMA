package com.pma.utils;



import com.pma.model.Activity;
import com.pma.model.ActivityType;
import com.pma.model.DailySummary;
import com.pma.model.Grocery;
import com.pma.model.Meal;

import java.util.ArrayList;

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


        activities = new ArrayList<>();

        activityTypes = new ArrayList<>();

    }

    public static synchronized DataMock getInstance(){
        if(instance == null){
            instance = new DataMock();
        }
        return instance;
    }
}