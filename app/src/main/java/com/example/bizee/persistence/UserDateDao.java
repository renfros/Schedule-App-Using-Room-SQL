package com.example.bizee.persistence;





import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bizee.UserDates;

import java.util.List;

/*
    The reason we are querying lists of data is because we are going to store the object in the database.
    each one of the fields inside of the UserDates object class has already been annotated for which column they fall under.
    So, when we insert and query we are just moving whole objects which makes it much easier to retrieve data for the list view.
 */

@Dao
public interface UserDateDao {

    //The ... denotes an array or list of objects example would be String[] but instead is String...
    @Insert
    void insertUserDate(UserDates... userDates);

    @Query("SELECT * FROM user_dates")
    LiveData<List<UserDates>> getUserDates();

    @Delete
    void delete(UserDates... userDates);

    @Update
    void updateUserDates(UserDates... userDates);

}
