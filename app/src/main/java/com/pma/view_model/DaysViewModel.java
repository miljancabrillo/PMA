package com.pma.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pma.dao.Database;
import com.pma.dao.MealDao;
import com.pma.model.DailySummary;

import java.util.List;

import lombok.Getter;

@Getter
public class DaysViewModel extends AndroidViewModel {

    private MutableLiveData<List<DailySummary>> dailySummaries = new MutableLiveData<>();
    private MealDao mealsDao;

    public DaysViewModel(@NonNull Application application) {
        super(application);
        mealsDao = Database.getInstance(application).mealDao();
        GetSummariesTask task = new GetSummariesTask();
        task.execute();
    }

    private class GetSummariesTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            List<DailySummary> summaries = mealsDao.getDailySummariesKcalIn();
            dailySummaries.postValue(summaries);
            return null;
        }
    }

}
