package com.pma.view_model;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pma.dao.Database;
import com.pma.dao.GroceryDao;
import com.pma.dao.MealDao;
import com.pma.model.GroceryAndAmountPair;
import com.pma.model.Meal;

import java.util.List;

import lombok.Getter;
import lombok.Setter;



public class MealDetailsViewModel extends AndroidViewModel {

    private int mealId;
    private MutableLiveData<Meal> meal;
    private MutableLiveData<List<GroceryAndAmountPair>> pairs;
    private MealDao mealDao;
    private GroceryDao groceryDao;

    public MealDetailsViewModel(@NonNull Application application) {
        super(application);
        Database db = Database.getInstance(application);
        mealDao = db.mealDao();
        groceryDao = db.groceryDao();
    }

    public void setMealId(int mealId){
        this.mealId = mealId;
    }

    public MutableLiveData<Meal> getMeal(){
        if(meal == null){
            meal =  new MutableLiveData<>();
            GetMealTask task = new GetMealTask();
            task.execute();
        }
        return meal;
    }

    public MutableLiveData<List<GroceryAndAmountPair>> getPairs(){
        if(pairs == null){
            pairs = new MutableLiveData<>();
            GetPairsTask task = new GetPairsTask();
            task.execute();
        }
        return pairs;
    }

    private class GetMealTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Meal m = mealDao.getMeal(mealId);
            meal.postValue(m);
            return null;
        }
    }

    private class GetPairsTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            List<GroceryAndAmountPair> list = mealDao.getMealWithPairs(mealId).getPairs();
            for (GroceryAndAmountPair pair : list) {
                pair.setGrocery(groceryDao.getGrocery(pair.getGroceryId()));
            }
            pairs.postValue(list);
            return null;
        }
    }
}
