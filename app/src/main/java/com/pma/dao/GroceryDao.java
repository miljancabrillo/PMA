package com.pma.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pma.model.Grocery;

import java.util.List;

@Dao
public interface GroceryDao {

    @Insert
    void insert(Grocery grocery);

    @Update
    void update(Grocery grocery);

    @Delete
    void delete(Grocery grocery);

    @Query("SELECT * FROM grocery")
    List<Grocery> getAll();

    @Query("SELECT * FROM grocery WHERE id = :id")
    Grocery getGrocery(int id);

    @Query("SELECT * FROM Grocery where name like :searchString;")
    List<Grocery> searchByName(String searchString);

    @Query("SELECT * FROM grocery WHERE isSynced != 1")
    List<Grocery> getNotSyncedGroceries();
}
