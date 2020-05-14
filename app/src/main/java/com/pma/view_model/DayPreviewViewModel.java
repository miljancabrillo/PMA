package com.pma.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pma.dao.Database;
import com.pma.dao.MealDao;
import com.pma.model.Meal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DayPreviewViewModel extends AndroidViewModel {

    private MealDao mealDao;
    private Date day;
    private MutableLiveData<List<Meal>> meals;

    public DayPreviewViewModel(@NonNull Application application) {
        super(application);
        mealDao = Database.getInstance(application).mealDao();
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

    private class GetMealsTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            List<Meal> mealList = mealDao.getMealsByDay(sdf.format(day));
            meals.postValue(mealList);
            return null;
        }
    }
}
