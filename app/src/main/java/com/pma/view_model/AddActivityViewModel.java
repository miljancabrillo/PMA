package com.pma.view_model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pma.utils.Utils;
import com.pma.dao.ActivityDao;
import com.pma.dao.ActivityTypeDao;
import com.pma.dao.Database;
import com.pma.dao.UserDao;
import com.pma.model.Activity;
import com.pma.model.ActivityType;

import java.util.Date;
import java.util.List;

public class AddActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<ActivityType>> activityTypes;
    private ActivityTypeDao activityTypeDao;
    private ActivityDao activityDao;
    private UserDao userDao;

    public AddActivityViewModel(@NonNull Application application) {
        super(application);
        Database db = Database.getInstance(application);
        activityTypeDao = db.activityTypeDao();
        activityDao = db.activityDao();
        userDao = db.userDao();
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

    public void addActivity(ActivityType activityType, Float duration){
        Activity activity = new Activity();
        activity.setActivityType(activityType);
        activity.setFinished(true);
        activity.setDate(new Date());
        activity.setDuration(duration);

        AddActivityTask task = new AddActivityTask();
        task.execute(activity);
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

    private class AddActivityTask extends AsyncTask<Activity, Void, Void>{

        @Override
        protected Void doInBackground(Activity... activities) {
            activities[0].setUser(userDao.findUserByEmail(Utils.getCurrentUsername(getApplication())));
            activities[0].calculateBurnedKcals();
            activityDao.insert(activities[0]);
            Log.d("BURNED KCALS", Float.toString(activities[0].getKcalBurned()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplication(), "Activity recorded!", Toast.LENGTH_SHORT).show();
        }
    }

}
