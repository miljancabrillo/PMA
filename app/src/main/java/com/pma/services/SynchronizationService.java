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
import com.pma.model.MealPairRelation;
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
    private String ip = "192.168.1.19";


    public SynchronizationService() {
        super("");
    }

    public SynchronizationService(String name) {
        super(name);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        ActivityTask task1 = new ActivityTask();
        task1.execute();

    }

    private class ActivityTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "http://" + ip + ":11000/syncUser";
            users = Database.getInstance(SynchronizationService.this).userDao().getNotSyncedUsers();
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<ArrayList<User>> entity = new HttpEntity<ArrayList<User>>((ArrayList<User>) users, headers);
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

            url = "http://" + ip + ":11000/syncGrocery";
            groceries = Database.getInstance(SynchronizationService.this).groceryDao().getNotSyncedGroceries();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<ArrayList<Grocery>> entity = new HttpEntity<ArrayList<Grocery>>((ArrayList<Grocery>) groceries, headers);
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

            url = "http://" + ip + ":11000/syncActivityType";
            activityTypes = Database.getInstance(SynchronizationService.this).activityTypeDao().getNotSyncedActivityTypes();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<ArrayList<ActivityType>> entity = new HttpEntity<ArrayList<ActivityType>>((ArrayList<ActivityType>) activityTypes, headers);
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

            url = "http://" + ip + ":11000/syncLocation";
            locations = Database.getInstance(SynchronizationService.this).locationDao().getNotSyncedLocations();
            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<ArrayList<Location>> entity = new HttpEntity<ArrayList<Location>>((ArrayList<Location>) locations, headers);
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

            url = "http://" + ip + ":11000/syncActivity";
            activities = Database.getInstance(SynchronizationService.this).activityDao().getNotSyncedActivities();
            if(!activities.isEmpty()) {
                for (Activity ac : activities) {
                    if (!ac.getName().equals("Bazalni metabolizam")) {
                        ActivityType type = Database.getInstance(SynchronizationService.this).activityTypeDao().searchById(ac.getActivityTypeId());
                        ac.setActivityType(type);
                        if (ac.getName().equals("Šetnja (automatski zabilježena)")) {
                            List<Location> list = Database.getInstance(SynchronizationService.this).locationDao().getLocationsInTimeRange(ac.getStartTime(), ac.getEndTime());
                            ac.setLocations(list);
                        }
                    }
                }
            }
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

            url = "http://" + ip + ":11000/syncMeal";
            meals = Database.getInstance(SynchronizationService.this).mealDao().getNotSyncedMeals();
            if(!meals.isEmpty()) {
                for (Meal m : meals) {
                    MealPairRelation mealPairRelation = Database.getInstance(SynchronizationService.this).mealDao().getMealWithPairs(m.getId());
                    for (GroceryAndAmountPair g : mealPairRelation.getPairs()) {
                        Grocery grocery = Database.getInstance(SynchronizationService.this).groceryDao().getGrocery(g.getGroceryId());
                        g.setGrocery(grocery);
                    }
                    for (GroceryAndAmountPair tmp : mealPairRelation.getPairs()) {
                        m.getGroceryAndAmountPairs().add(tmp);
                    }
                }
            }

            try {
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                HttpEntity<ArrayList<Meal>> entity = new HttpEntity<ArrayList<Meal>>((ArrayList<Meal>) meals, headers);
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
}
