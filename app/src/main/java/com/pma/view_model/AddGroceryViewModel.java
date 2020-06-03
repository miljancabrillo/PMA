package com.pma.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pma.dao.Database;
import com.pma.dao.GroceryDao;
import com.pma.model.Grocery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGroceryViewModel extends AndroidViewModel {

    private Grocery grocery;
    private GroceryDao groceryDao;

    public AddGroceryViewModel(@NonNull Application application) {
        super(application);
        Database db = Database.getInstance(application);
        groceryDao = db.groceryDao();
        grocery = new Grocery();
    }

    public void addGrocery() {
        AddGroceryTask task = new AddGroceryTask();
        task.execute();
    }

    private class AddGroceryTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            groceryDao.insert(grocery);
            return null;
        }
    }

}