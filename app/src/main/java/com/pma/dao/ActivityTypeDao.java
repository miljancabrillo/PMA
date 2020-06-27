package com.pma.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pma.model.ActivityType;

import java.util.List;

@Dao
public interface ActivityTypeDao {

    @Insert
    void insert(ActivityType activityType);

    @Update
    void update(ActivityType activityType);

    @Delete
    void delete(ActivityType activityType);

    @Query("SELECT * FROM ActivityType")
    List<ActivityType> getAll();

    @Query("SELECT * FROM ActivityType where name like :searchString;")
    List<ActivityType> searchByName(String searchString);

    @Query("SELECT * FROM ActivityType WHERE isSynced != 1")
    List<ActivityType> getNotSyncedActivityTypes();

}
