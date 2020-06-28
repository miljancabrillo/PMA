package com.pma.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class SynchronizationService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private List<Activity> activities;
    private List<ActivityType> activityTypes;
    private List<Grocery> groceries;
    private List<GroceryAndAmountPair> groceryAndAmountPairs;
    private List<Location> locations;
    private List<Meal> meals;
    private List<User> users;
    private String ip = "192.168.43.50";


    public SynchronizationService() {
        super("");
    }

    public SynchronizationService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Automacko opaljivanje xd", Toast.LENGTH_SHORT).show();
            }
        });

        return;

        /*ActivityTask task1 = new ActivityTask();
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
        task7.execute();*/

    }

    private class ActivityTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://" + ip + ":11000/syncActivity";
            activities = Database.getInstance(SynchronizationService.this).activityDao().getNotSyncedActivities();
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<ArrayList<Activity>> entity = new HttpEntity<>((ArrayList<Activity>) activities, headers);
                ResponseEntity<Activity> response = restTemplate.exchange(url, HttpMethod.POST, entity, null);
                HttpStatus status = response.getStatusCode();
                if (status == HttpStatus.OK) {
                    for (int i = 0; i < activities.size(); i++) {
                        activities.get(i).setSynced(true);
                        Database.getInstance(SynchronizationService.this).activityDao().update(activities.get(i));
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }

            return null;
        }
    }

    private class ActivityTypeTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://" + ip + ":11000/syncActivityType";
            activityTypes = Database.getInstance(SynchronizationService.this).activityTypeDao().getNotSyncedActivityTypes();
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<ActivityType> entity = new HttpEntity<ActivityType>((ActivityType) activityTypes, headers);
                ResponseEntity<ActivityType> response = restTemplate.exchange(url, HttpMethod.POST, entity, ActivityType.class);
                HttpStatus status = response.getStatusCode();
                if (status == HttpStatus.OK) {
                    for (int i = 0; i < activityTypes.size(); i++) {
                        activityTypes.get(i).setSynced(true);
                        Database.getInstance(SynchronizationService.this).activityTypeDao().update(activityTypes.get(i));
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                return null;
            }


            return null;
        }
    }

    private class GroceryTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://" + ip + ":11000/syncGrocery";
            groceries = Database.getInstance(SynchronizationService.this).groceryDao().getNotSyncedGroceries();
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<Grocery> entity = new HttpEntity<Grocery>((Grocery) groceries, headers);
                ResponseEntity<Grocery> response = restTemplate.exchange(url, HttpMethod.POST, entity, Grocery.class);
                HttpStatus status = response.getStatusCode();
                if (status == HttpStatus.OK) {
                    for (int i = 0; i < groceries.size(); i++) {
                        groceries.get(i).setSynced(true);
                        Database.getInstance(SynchronizationService.this).groceryDao().update(groceries.get(i));
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                return null;
            }


            return null;
        }
    }

    private class GroceryAndAmountPairTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://" + ip + ":11000/syncGroceryAndAmount";
            groceryAndAmountPairs = Database.getInstance(SynchronizationService.this).pairDao().getNotSyncedGroceryAndAmountPairs();
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<GroceryAndAmountPair> entity = new HttpEntity<GroceryAndAmountPair>((GroceryAndAmountPair) groceryAndAmountPairs, headers);
                ResponseEntity<GroceryAndAmountPair> response = restTemplate.exchange(url, HttpMethod.POST, entity, GroceryAndAmountPair.class);
                HttpStatus status = response.getStatusCode();
                if (status == HttpStatus.OK) {
                    for (int i = 0; i < groceryAndAmountPairs.size(); i++) {
                        groceryAndAmountPairs.get(i).setSynced(true);
                        Database.getInstance(SynchronizationService.this).pairDao().update(groceryAndAmountPairs.get(i));
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                return null;
            }


            return null;
        }
    }

    private class LocationTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://" + ip + ":11000/syncLocation";
            locations = Database.getInstance(SynchronizationService.this).locationDao().getNotSyncedLocations();
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<Location> entity = new HttpEntity<Location>((Location) locations, headers);
                ResponseEntity<Location> response = restTemplate.exchange(url, HttpMethod.POST, entity, Location.class);
                HttpStatus status = response.getStatusCode();
                if (status == HttpStatus.OK) {
                    for (int i = 0; i < locations.size(); i++) {
                        locations.get(i).setSynced(true);
                        Database.getInstance(SynchronizationService.this).locationDao().update(locations.get(i));
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                return null;
            }

            return null;
        }
    }

    private class MealTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://" + ip + ":11000/syncMeal";
            meals = Database.getInstance(SynchronizationService.this).mealDao().getNotSyncedMeals();
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<Meal> entity = new HttpEntity<Meal>((Meal) meals, headers);
                ResponseEntity<Meal> response = restTemplate.exchange(url, HttpMethod.POST, entity, Meal.class);
                HttpStatus status = response.getStatusCode();
                if (status == HttpStatus.OK) {
                    for (int i = 0; i < meals.size(); i++) {
                        meals.get(i).setSynced(true);
                        Database.getInstance(SynchronizationService.this).mealDao().update(meals.get(i));
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                return null;
            }


            return null;
        }
    }

    private class UserTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://" + ip + "/syncUser";
            users = Database.getInstance(SynchronizationService.this).userDao().getNotSyncedUsers();
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<User> entity = new HttpEntity<User>((User) users, headers);
                ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, entity, User.class);
                HttpStatus status = response.getStatusCode();
                if (status == HttpStatus.OK) {
                    for (int i = 0; i < users.size(); i++) {
                        users.get(i).setSynced(true);
                        Database.getInstance(SynchronizationService.this).userDao().update(users.get(i));
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                return null;
            }

            return null;
        }
    }
}
