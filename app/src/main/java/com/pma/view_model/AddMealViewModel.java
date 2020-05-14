package com.pma.view_model;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pma.dao.Database;
import com.pma.dao.GroceryAndAmountPairDao;
import com.pma.dao.GroceryDao;
import com.pma.dao.MealDao;
import com.pma.model.Grocery;
import com.pma.model.GroceryAndAmountPair;
import com.pma.model.Meal;
import com.pma.model.MealPairRelation;

import java.util.Date;
import java.util.List;

import lombok.Getter;

@Getter
public class AddMealViewModel extends AndroidViewModel {

    private MutableLiveData<List<Grocery>> groceries = new MutableLiveData<>();
    private GroceryDao groceriesDao;
    private GroceryAndAmountPairDao pairDao;
    private MealDao mealDao;
    private MutableLiveData<Meal> meal = new MutableLiveData<>();


    public AddMealViewModel(@NonNull Application application) {
        super(application);
        Database db = Database.getInstance(application);
        groceriesDao = db.groceryDao();
        pairDao = db.pairDao();
        mealDao = db.mealDao();
        SearchTask task = new SearchTask();
        task.execute("");
        meal.setValue(new Meal());
        meal.getValue().setDateAndTime(new Date());
    }

    public void searchGroceries(String searchString){
        SearchTask task = new SearchTask();
        task.execute(searchString);
    }

    public void addGroceryAmountPair(GroceryAndAmountPair pair){
        meal.getValue().addGroceryAmountPair(pair);
        meal.setValue(meal.getValue());
    }

    public void removeGroceryAmountPair(GroceryAndAmountPair pair){
        meal.getValue().removeGroceryAmountPair(pair);
        meal.setValue(meal.getValue());
    }

    public boolean addMeal(){
        if(meal.getValue().getGroceryAndAmountPairs().size() == 0) return false;
        AddMealTask task = new AddMealTask();
        task.execute();
        return  true;
    }

    private class SearchTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String searchString = strings[0];
            if(searchString.equals("")){
                List<Grocery> groceriesList = groceriesDao.getAll();
                groceries.postValue(groceriesList);
            }else{
                List<Grocery> groceriesList = groceriesDao.searchByName("%" + searchString + "%");
                groceries.postValue(groceriesList);
            }
            return  null;
        }
    }

    private class AddMealTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            long mealId = mealDao.insert(meal.getValue());
            meal.getValue().setMealId(mealId);
            pairDao.insertMultiple(meal.getValue().getGroceryAndAmountPairs());
            meal.postValue(new Meal());
            return null;
        }
    }
}
