package com.pma.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pma.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user WHERE user.email = :email")
    User findUserByEmail(String email);

    @Query("SELECT * FROM user WHERE isSynced != 1")
    List<User> getNotSyncedUsers();
}
