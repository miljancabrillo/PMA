package com.pma.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pma.model.GroceryAndAmountPair;

import java.util.List;

@Dao
public interface GroceryAndAmountPairDao {

    @Insert
    void insert(GroceryAndAmountPair pair);

    @Insert
    void  insertMultiple(List<GroceryAndAmountPair> pairs);

    @Update
    void update(GroceryAndAmountPair pair);

    @Delete
    void delete(GroceryAndAmountPair pair);

    @Query("SELECT * FROM groceryandamountpair WHERE isSynced != 1")
    List<GroceryAndAmountPair> getNotSyncedGroceryAndAmountPairs();

}
