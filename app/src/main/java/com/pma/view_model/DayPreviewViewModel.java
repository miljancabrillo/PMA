package com.pma.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pma.dao.ActivityDao;
import com.pma.dao.Database;
import com.pma.dao.LocationDao;
import com.pma.dao.MealDao;
import com.pma.model.Activity;
import com.pma.model.Meal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DayPreviewViewModel extends AndroidViewModel {

    private MealDao mealDao;
    private ActivityDao activityDao;
    private LocationDao locationDao;
    private Date day;
    private MutableLiveData<List<Meal>> meals;
    private MutableLiveData<List<Activity>> walkingActivities;

    public DayPreviewViewModel(@NonNull Application application) {
        super(application);
        Database db = Database.getInstance(application);
        mealDao = db.mealDao();
        activityDao = db.activityDao();
        locationDao = db.locationDao();
    }

    public void setDate(Date date){
        this.day = date;
    }

    public MutableLiveData<List<Meal>> getMeals(){
        if(meals == null){
            meals = new MutableLiveData<>();
            GetMealsTask task = new GetMealsTask();
            task.execute();
        }
        return meals;
    }

    public MutableLiveData<List<Activity>> getWalkingActivities(){
        if(walkingActivities == null){
            walkingActivities = new MutableLiveData<>();
            GetWalkingActivitiesTask task = new GetWalkingActivitiesTask();
            task.execute();
        }
        return walkingActivities;
    }

    private class GetMealsTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            List<Meal> mealList = mealDao.getMealsByDay(sdf.format(day));
            meals.postValue(mealList);
            return null;
        }
    }

    private class GetWalkingActivitiesTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            List<Activity> activities = activityDao.getFinishedWalkingActivitiesByDay(sdf.format(day));
            if(activities != null){
                for (Activity activity: activities) {
                    activity.setLocations(locationDao.getLocationsInTimeRange(activity.getStartTime(),activity.getEndTime()));
                }
            }
            walkingActivities.postValue(activities);
            return null;
        }
    }
}
