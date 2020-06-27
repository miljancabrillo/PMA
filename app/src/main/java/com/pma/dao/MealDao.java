package com.pma.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.pma.model.DailySummary;
import com.pma.model.Meal;
import com.pma.model.MealPairRelation;

import java.util.Date;
import java.util.List;

@Dao
public interface MealDao {

    @Insert
    long insert(Meal meal);

    @Update
    void update(Meal meal);

    @Delete
    void delete(Meal meal);

    @Transaction
    @Query("SELECT * FROM meal WHERE meal.id = :id")
    MealPairRelation getMealWithPairs(int id);


    @Query("SELECT * FROM meal WHERE meal.id = :id")
    Meal getMeal(int id);

    @Query("SELECT date(dateAndTime) as day, SUM(totalKcal) as kcalIn FROM meal GROUP BY date(dateAndTime)")
    List<DailySummary> getDailySummariesKcalIn();

    @Query("SELECT date(dateAndTime) as day, SUM(totalKcal) as kcalIn, SUM(totalProtein) as totalProtein, SUM(totalCarb) as totalCarb, SUM(totalFat) as totalFat FROM meal WHERE date(dateAndTime) BETWEEN date(:date1) AND date(:date2) GROUP BY date(dateAndTime)")
    List<DailySummary> getDailySummaries(Date date1, Date date2);

    @Query("SELECT date(dateAndTime) as day, SUM(totalKcal) as kcalIn, SUM(totalProtein) as totalProtein, SUM(totalCarb) as totalCarb, SUM(totalFat) as totalFat  FROM meal WHERE date(dateAndTime) = date(:date)")
    List<DailySummary> getDailySummary(Date date);

    @Query("SELECT * FROM meal WHERE date(dateAndTime) = date(:date)")
    List<Meal> getMealsByDay(Date date);

    @Query("SELECT * FROM meal WHERE isSynced != 1")
    List<Meal> getNotSyncedMeals();
}
