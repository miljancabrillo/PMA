package com.pma.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import com.pma.dao.Database;
import com.pma.model.Activity;
import com.pma.model.ActivityType;
import com.pma.model.Grocery;
import com.pma.model.GroceryAndAmountPair;
import com.pma.model.Location;
import com.pma.model.Meal;
import com.pma.model.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class SynchronizationService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SynchronizationService(String name) {
        super(name);
    }

    private List<Activity> activities;
    private List<ActivityType> activityTypes;
    private List<Grocery> groceries;
    private List<GroceryAndAmountPair> groceryAndAmountPairs;
    private List<Location> locations;
    private List<Meal> meals;
    private List<User> users;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        ActivityTask task1 = new ActivityTask();
        task1.execute();

        ActivityTypeTask task2 = new ActivityTypeTask();
        task2.execute();

        GroceryTask task3 = new GroceryTask();
        task3.execute();

        GroceryAndAmountPairTask task4 = new GroceryAndAmountPairTask();
        task4.execute();

        LocationTask task5 = new LocationTask();
        task5.execute();

        MealTask task6 = new MealTask();
        task6.execute();

        UserTask task7 = new UserTask();
        task7.execute();

    }

    private class ActivityTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://localhost:11000/syncActivity";
            activities = Database.getInstance(SynchronizationService.this).activityDao().getNotSyncedActivities();
            RestTemplate restTemplate = new RestTemplate();
            try{
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<Activity> entity = new HttpEntity<Activity>((Activity) activities, headers);
                restTemplate.exchange(url, HttpMethod.POST, entity, Activity.class);

            }catch (Exception e) {
                e.getMessage();
                return null;
            }

            for (int i =0; i<activities.size(); i++){
                activities.get(i).setIsSynced(true);
                Database.getInstance(SynchronizationService.this).activityDao().update(activities.get(i));
            }

            return null;
        }
    }

    private class ActivityTypeTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://localhost:11000/syncActivityType";
            activityTypes = Database.getInstance(SynchronizationService.this).activityTypeDao().getNotSyncedActivityTypes();
            RestTemplate restTemplate = new RestTemplate();
            try{
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<ActivityType> entity = new HttpEntity<ActivityType>((ActivityType) activityTypes, headers);
                restTemplate.exchange(url, HttpMethod.POST, entity, ActivityType.class);

            }catch (Exception e) {
                e.getMessage();
                return null;
            }

            for (int i =0; i<activityTypes.size(); i++){
                activityTypes.get(i).setIsSynced(true);
                Database.getInstance(SynchronizationService.this).activityTypeDao().update(activityTypes.get(i));
            }

            return null;
        }
    }

    private class GroceryTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://localhost:11000/syncGrocery";
            groceries = Database.getInstance(SynchronizationService.this).groceryDao().getNotSyncedGroceries();
            RestTemplate restTemplate = new RestTemplate();
            try{
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<Grocery> entity = new HttpEntity<Grocery>((Grocery) groceries, headers);
                restTemplate.exchange(url, HttpMethod.POST, entity, Grocery.class);

            }catch (Exception e) {
                e.getMessage();
                return null;
            }

            for (int i =0; i<groceries.size(); i++){
                groceries.get(i).setIsSynced(true);
                Database.getInstance(SynchronizationService.this).groceryDao().update(groceries.get(i));
            }

            return null;
        }
    }

    private class GroceryAndAmountPairTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://localhost:11000/syncGroceryAndAmount";
            groceryAndAmountPairs = Database.getInstance(SynchronizationService.this).pairDao().getNotSyncedGroceryAndAmountPairs();
            RestTemplate restTemplate = new RestTemplate();
            try{
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<GroceryAndAmountPair> entity = new HttpEntity<GroceryAndAmountPair>((GroceryAndAmountPair) groceryAndAmountPairs, headers);
                restTemplate.exchange(url, HttpMethod.POST, entity, GroceryAndAmountPair.class);

            }catch (Exception e) {
                e.getMessage();
                return null;
            }

            for (int i =0; i<groceryAndAmountPairs.size(); i++){
                groceryAndAmountPairs.get(i).setIsSynced(true);
                Database.getInstance(SynchronizationService.this).pairDao().update(groceryAndAmountPairs.get(i));
            }

            return null;
        }
    }

    private class LocationTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://localhost:11000/syncLocation";
            locations = Database.getInstance(SynchronizationService.this).locationDao().getNotSyncedLocations();
            RestTemplate restTemplate = new RestTemplate();
            try{
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<Location> entity = new HttpEntity<Location>((Location) locations, headers);
                restTemplate.exchange(url, HttpMethod.POST, entity, Location.class);

            }catch (Exception e) {
                e.getMessage();
                return null;
            }

            for (int i =0; i<locations.size(); i++){
                locations.get(i).setIsSynced(true);
                Database.getInstance(SynchronizationService.this).locationDao().update(locations.get(i));
            }

            return null;
        }
    }

    private class MealTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://localhost:11000/syncMeal";
            meals = Database.getInstance(SynchronizationService.this).mealDao().getNotSyncedMeals();
            RestTemplate restTemplate = new RestTemplate();
            try{
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<Meal> entity = new HttpEntity<Meal>((Meal) meals, headers);
                restTemplate.exchange(url, HttpMethod.POST, entity, Meal.class);

            }catch (Exception e) {
                e.getMessage();
                return null;
            }

            for (int i =0; i<meals.size(); i++){
                meals.get(i).setIsSynced(true);
                Database.getInstance(SynchronizationService.this).mealDao().update(meals.get(i));
            }

            return null;
        }
    }

    private class UserTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://localhost:11000/syncUser";
            users = Database.getInstance(SynchronizationService.this).userDao().getNotSyncedUsers();
            RestTemplate restTemplate = new RestTemplate();
            try{
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<User> entity = new HttpEntity<User>((User) users, headers);
                restTemplate.exchange(url, HttpMethod.POST, entity, User.class);

            }catch (Exception e) {
                e.getMessage();
                return null;
            }

            for (int i =0; i<users.size(); i++){
                users.get(i).setIsSynced(true);
                Database.getInstance(SynchronizationService.this).userDao().update(users.get(i));
            }

            return null;
        }
    }
}
