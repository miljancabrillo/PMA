package com.pma.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pma.dao.ActivityTypeDao;
import com.pma.dao.Database;
import com.pma.model.ActivityType;

import java.util.List;

public class AddActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<ActivityType>> activityTypes;
    private ActivityTypeDao activityTypeDao;

    public AddActivityViewModel(@NonNull Application application) {
        super(application);
        Database db = Database.getInstance(application);
        activityTypeDao = db.activityTypeDao();
    }

    public LiveData<List<ActivityType>> getActivityTypes(){
        if(activityTypes == null){
            activityTypes = new MutableLiveData<>();
            SearchTask task = new SearchTask();
            task.execute("");
        }
        return activityTypes;
    }

    public void searchActivityTypes(String searchString){
        SearchTask task = new SearchTask();
        task.execute("%" + searchString + "%");
    }

    private class SearchTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String searchString = strings[0];
            if(searchString.equals("")){
                List<ActivityType> list = activityTypeDao.getAll();
                activityTypes.postValue(list);
            }else{
                List<ActivityType> list = activityTypeDao.searchByName(searchString);
                activityTypes.postValue(list);
            }
            return  null;
        }
    }
}
