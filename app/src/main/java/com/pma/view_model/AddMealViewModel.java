package com.pma.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pma.dao.Database;
import com.pma.dao.GroceryDao;
import com.pma.model.Grocery;

import java.util.List;

import lombok.Getter;

@Getter
public class AddMealViewModel extends AndroidViewModel {

    private MutableLiveData<List<Grocery>> groceries = new MutableLiveData<>();
    private GroceryDao groceriesDao;


    public AddMealViewModel(@NonNull Application application) {
        super(application);
        groceriesDao = Database.getInstance(application).groceryDao();
        SearchTask task = new SearchTask();
        task.execute("");
    }

    public void searchGroceries(String searchString){
        SearchTask task = new SearchTask();
        task.execute("%" + searchString + "%");
    }

    private class SearchTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String searchString = strings[0];
            if(searchString.equals("")){
                List<Grocery> groceriesList = groceriesDao.getAll();
                groceries.postValue(groceriesList);
            }else{
                List<Grocery> groceriesList = groceriesDao.searchByName(searchString);
                groceries.postValue(groceriesList);
            }
            return  null;
        }
    }
}
