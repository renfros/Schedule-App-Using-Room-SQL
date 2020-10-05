package com.example.bizee;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Table name for the user dates
@Entity(tableName = "user_dates")
public class UserDates {

    //Primary key auto increments id number as new data is inserted into the table
    @PrimaryKey(autoGenerate = true)
    private int id;

    //Each @ColumnInfo is a new column for the table
    @ColumnInfo(name = "header")
    private String header;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "priority")
    private String priority;

    public UserDates(String header, String time, String date, String priority){
        this.header = header;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getPriority() {
        return priority;
    }
}
