package com.pma.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pma.model.Activity;
import com.pma.model.ActivityType;
import com.pma.model.Grocery;
import com.pma.model.GroceryAndAmountPair;
import com.pma.model.Location;
import com.pma.model.Meal;
import com.pma.model.User;


@androidx.room.Database(entities = {User.class, Grocery.class,
        GroceryAndAmountPair.class, Meal.class, Activity.class, Location.class, ActivityType.class}, version = 8)
@TypeConverters({DateStringConverter.class})
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract UserDao userDao();

    public abstract  GroceryDao groceryDao();

    public abstract  GroceryAndAmountPairDao pairDao();

    public  abstract  MealDao mealDao();

    public abstract  ActivityDao activityDao();

    public  abstract  LocationDao locationDao();

    public abstract  ActivityTypeDao activityTypeDao();

    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "pma_database")
                    //.createFromAsset("databases/pma_database.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbTask(instance).execute();
        }
    };

    private static class PopulateDbTask extends AsyncTask<Void, Void, Void>{

        private UserDao userDao;

        public PopulateDbTask(Database db){
            this.userDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //uraditi inserte i to sve
            return null;
        }
    }

}
