package com.example.bizee.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.bizee.UserDates;
import com.example.bizee.async.DeleteAsyncTask;
import com.example.bizee.async.InsertAsyncTask;

import java.util.List;

public class UserDatesRepository {

    private UserDatesDatabase userDatesDatabase;

    public UserDatesRepository(Context context){
        userDatesDatabase = UserDatesDatabase.getInstance(context);
    }

    public void insertUserDatesTask(UserDates userDates){
        new InsertAsyncTask(userDatesDatabase.getUserDatesDao()).execute(userDates);
    }

    public void updateUserDates(UserDates userDates){

    }

    public LiveData<List<UserDates>> retrieveUserDatesTask(){

        return userDatesDatabase.getUserDatesDao().getUserDates();
    }

    public void deleteUserDates(UserDates userDates){
        new DeleteAsyncTask(userDatesDatabase.getUserDatesDao()).execute(userDates);
    }

}
