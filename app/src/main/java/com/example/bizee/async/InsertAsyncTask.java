package com.example.bizee.async;

import android.os.AsyncTask;

import com.example.bizee.UserDates;
import com.example.bizee.persistence.UserDateDao;

public class InsertAsyncTask extends AsyncTask<UserDates, Void, Void> {

    private UserDateDao userDateDao;

    public InsertAsyncTask(UserDateDao userDatesDao) {
        this.userDateDao = userDatesDao;
    }

    @Override
    protected Void doInBackground(UserDates... userDates) {
        userDateDao.insertUserDate(userDates);
        return null;
    }

}
