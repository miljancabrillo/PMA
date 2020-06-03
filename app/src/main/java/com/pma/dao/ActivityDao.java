package com.pma.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pma.model.Activity;

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

    @Query("SELECT * FROM activity WHERE name = 'Walking' AND finished = 0")
    Activity getStartedWalkingActivity();

    @Query("SELECT * FROM activity WHERE name = 'Walking' and finished = 1 and strftime('%d-%m-%Y', date/1000,'unixepoch') = :day")
    List<Activity> getFinishedWalkingActivitiesByDay(String day);

}
