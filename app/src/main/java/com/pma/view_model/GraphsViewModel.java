package com.pma.view_model;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pma.dao.Database;
import com.pma.dao.MealDao;
import com.pma.model.DailySummary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

@Getter
public class GraphsViewModel extends AndroidViewModel {

    private MutableLiveData<List<DailySummary>> dailySummary = new MutableLiveData<>();
    private MutableLiveData<List<DailySummary>> daily = new MutableLiveData<>();
    private MealDao mealDao;
    private Date date, dateTo, dateFrom;

    public GraphsViewModel(@NonNull Application application) {
        super(application);
        mealDao = Database.getInstance(application).mealDao();
    }

    public void getDataByDay() {
        GetDailyTask getDailyTask = new GetDailyTask();
        getDailyTask.execute();
    }

    public void getData(){
        GetDataTask getDataTask = new GetDataTask();
        getDataTask.execute();
    }

    public void setDates(Date date1, Date date2){
        dateTo = date2;
        dateFrom = date1;
    }

    public void setDate(Date d){
        date = d;
    }

    private class GetDailyTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            List<DailySummary> summaries = mealDao.getDailySummaries(dateFrom, dateTo);
            dailySummary.postValue(summaries);
            return null;
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            List<DailySummary> summaries = mealDao.getDailySummary(date);
            if(summaries != null) {
                daily.postValue(summaries);
            }
            return null;
        }
    }
}