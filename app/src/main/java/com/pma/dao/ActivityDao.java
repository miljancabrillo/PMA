package com.pma.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.pma.model.Activity;
import com.pma.model.DailySummary;

import java.util.Date;
import java.util.List;

@Dao
public interface ActivityDao {

    @Insert
    void insert(Activity activity);

    @Update
    void update(Activity activity);

    @Delete
    void delete(Activity activity);

    @Query("SELECT * FROM activity")
    List<Activity> getAll();

    @Query("SELECT * FROM activity WHERE name = 'Šetnja (automatski zabilježena)' AND finished = 0")
    Activity getStartedWalkingActivity();

    @Query("SELECT * FROM activity WHERE name = 'Šetnja (automatski zabilježena)' and finished = 1 and date(date) = date(:date)")
    List<Activity> getFinishedWalkingActivitiesByDay(Date date);

    @Query("SELECT date(date) as day, SUM(kcalBurned) as kcalOut FROM activity WHERE finished = 1 GROUP BY date(date)")
    List<DailySummary> getDailySummariesKcalOut();

    @Query("SELECT * FROM activity WHERE name = 'Bazalni metabolizam' and date(date) = date(:date)")
    Activity getBMRActivity(Date date);

    @Query("SELECT * FROM activity WHERE date(date) = date(:date) and finished = 1")
    List<Activity> getFinishedActivitiesByDay(Date date);

    @Query("SELECT * FROM activity WHERE isSynced != 1")
    List<Activity> getNotSyncedActivities();


}
