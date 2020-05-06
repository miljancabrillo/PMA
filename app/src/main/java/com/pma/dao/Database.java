package com.pma.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pma.model.Grocery;
import com.pma.model.User;


@androidx.room.Database(entities = {User.class, Grocery.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract UserDao userDao();

    public abstract  GroceryDao groceryDao();

    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "pma_database")
                    .createFromAsset("databases/pma_database.db")
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
