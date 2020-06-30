package com.pma.view_model;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pma.dao.ActivityDao;
import com.pma.dao.Database;
import com.pma.dao.MealDao;
import com.pma.dao.UserDao;
import com.pma.model.Activity;
import com.pma.model.DailySummary;
import com.pma.model.User;
import com.pma.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Getter;

@Getter
public class DaysViewModel extends AndroidViewModel {

    private MutableLiveData<List<DailySummary>> dailySummaries = new MutableLiveData<>();
    private MealDao mealsDao;
    private ActivityDao activityDao;
    private UserDao userDao;

    public DaysViewModel(@NonNull Application application) {
        super(application);
        Database db = Database.getInstance(application);
        mealsDao = db.mealDao();
        activityDao = db.activityDao();
        userDao = db.userDao();
        CalculateBMRTask bmrTask = new CalculateBMRTask();
        bmrTask.execute();
        GetSummariesTask task = new GetSummariesTask();
        task.execute();
    }

    public void refreshSummaries(){
        GetSummariesTask task = new GetSummariesTask();
        task.execute();
    }

    private class GetSummariesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            List<DailySummary> summariesMeals = mealsDao.getDailySummariesKcalIn();
            List<DailySummary> summariesActivities = activityDao.getDailySummariesKcalOut();
            List<DailySummary> finalList;

            if(summariesMeals.isEmpty()){
                finalList = summariesActivities;
            } else if(summariesActivities.isEmpty()){
                finalList = summariesMeals;
            } else{
                for (DailySummary summary : summariesActivities) {
                    int index = summariesMeals.indexOf(summary);
                    if(index != -1)summary.setKcalIn(summariesMeals.get(index).getKcalIn());
                }
                finalList = summariesActivities;
            }

            Collections.reverse(finalList);
            dailySummaries.postValue(finalList);
            return null;
        }
    }

    private class CalculateBMRTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Activity bmr = activityDao.getBMRActivity(new Date());

            if (bmr != null) return null;

            bmr = new Activity();
            bmr.setName("Bazalni metabolizam");
            bmr.setFinished(true);

            User user = userDao.findUserByEmail(Utils.getCurrentUsername(getApplication()));
            bmr.setUser(user);
            bmr.setDate(new Date());

            if(user.isMale()) bmr.setKcalBurned((10f * user.getWeight() + 6.25f * user.getHeight() - 5f * user.getAge() + 5f) * 1.2f);
            else bmr.setKcalBurned((10f * user.getWeight() + 6.25f * user.getHeight() - 5f * user.getAge() - 161f) * 1.2f);

            activityDao.insert(bmr);

            return null;
        }
    }

}
