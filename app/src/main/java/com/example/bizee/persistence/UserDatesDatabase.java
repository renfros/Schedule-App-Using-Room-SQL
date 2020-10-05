package com.example.bizee.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bizee.UserDates;

//Entity created in the UserDates Class
//Version number is the version of the database
@Database(entities = {UserDates.class}, version = 1)
public abstract class UserDatesDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "user_dates_db";

    private static UserDatesDatabase instance;

    static UserDatesDatabase getInstance(final Context context){

        //We have this so there is only one copy of the database
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    UserDatesDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract UserDateDao getUserDatesDao();

}
