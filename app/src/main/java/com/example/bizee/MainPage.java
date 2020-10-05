package com.example.bizee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.bizee.persistence.UserDatesRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainPage extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{

    TextView textView;
    ArrayList<UserDates> mUserDates;
    CustomArrayAdapter adapter;
    SwipeMenuListView listView;

    FloatingActionButton addBtn;

    //For creating new user date object
    String date;
    String header;
    String time;
    String priority;
    //////////////////////////////////////////////END

    //For database
    private UserDatesRepository userDatesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        textView = findViewById(R.id.txtComingUp);

        setUpUI();
    }

    private void sendNotification(){

        NotificationManager notificationManager = (NotificationManager) MainPage.this.getSystemService(NOTIFICATION_SERVICE);

        //Create Notification Channel
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "Test";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("123",name,importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(R.color.colorPrimary);

            notificationManager.createNotificationChannel(channel); }

        //Create Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"123")
                .setContentTitle("Test")
                .setContentText("Hi this is first notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_high_priority)
                .setAutoCancel(true);
        notificationManager.notify(1, builder.build());

    }

    private void setUpUI(){

        setUpList(); //ListView

        setUpRepo();//Setup Repository

        retrieveUserDates(); //Repository

        setUpButton(); //Buttons
    }

    private void setUpRepo(){
        userDatesRepository = new UserDatesRepository(this);
    }

    private void setUpList(){
        mUserDates = new ArrayList<UserDates>();
        adapter = new CustomArrayAdapter(this,R.layout.date_layout,mUserDates);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(340);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        final int p = position;
                        final int i = index;
                       AlertDialog.Builder builder = new AlertDialog.Builder(menu.getContext());
                        builder.setMessage("Are you sure you want to delete?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        deleteUserDate(i);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();


                        break;
                    case 1:
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

    }

    private void retrieveUserDates(){

        userDatesRepository.retrieveUserDatesTask().observe(this, new Observer<List<UserDates>>() {
            @Override
            public void onChanged(List<UserDates> userDates) {
                if(mUserDates.size() > 0){
                    mUserDates.clear();
                }
                if(mUserDates != null){
                    mUserDates.addAll(userDates);
                }
                adapter.notifyDataSetChanged();
            }
        });

        //if nothing planned change the header
//        if(mUserDates.isEmpty()){
//            textView.setText("Looks like there's nothing planned");
//        }

    }

    private void setUpButton(){
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();

            }
        });
    }

    private void showDateDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                R.style.DateDialogStyle,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month += 1;
        date = month + "/" + dayOfMonth + "/" + year;
        Log.d("DATE", date);
        showTimeSelectDialog();
    }

    private void showTimeSelectDialog(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.TimePickerStyle, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int nMinute) {

                String minute = Integer.toString(nMinute);
                if(minute.equals("0")){
                    minute = "00";
                }
                if(hourOfDay > 12){
                    hourOfDay -= 12;
                    time = hourOfDay + ":" + minute + " pm";
                    Log.d("TIME", time);
                }else if(hourOfDay == 12){
                    time = hourOfDay + ":" + minute + " pm";
                    Log.d("TIME", time);
                }else if(hourOfDay == 0){
                    time = 12 + ":" + minute + "am";
                    Log.d("TIME", time);
                }else {
                    time = hourOfDay + ":" + minute + "am";
                    Log.d("TIME", time);
                }

                getHeaderFromUser();

            }
        },12,0,false);

        timePickerDialog.show();
    }

    private void getHeaderFromUser(){

        //Must declare the view in order to have access to the variables
        //Otherwise the program will crash from nullPointer exception
        final View v = getLayoutInflater().inflate(R.layout.popup_dialog_input_layout,null);
         AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.UserInputDialog)
                .setView(v);
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = v.findViewById(R.id.editTextSetHeader);
                        header = editText.getText().toString();
                        editText.setText("");
                        Log.d("HEADER", header);
                        setUserPriority();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = findViewById(R.id.editTextSetHeader);
                        editText.setText("");
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(1250,750);


    }

    private void setUserPriority(){

        String[] userPriority = {"High","Medium","Low"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Select The Priority")
                .setCancelable(true)
                .setItems(userPriority, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                priority = "high";
                                UserDates add1 = new UserDates(header,time,date,priority);
                                addUserDate(add1);
                                break;
                            case 1:
                                priority = "medium";
                                UserDates add2 = new UserDates(header,time,date,priority);
                                addUserDate(add2);
                                break;

                            default:
                                priority = "low";
                                UserDates add3 = new UserDates(header,time,date,priority);
                                addUserDate(add3);
                                break;
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void addUserDate(UserDates nUserDate){
        //If the array is empty update the header
        if(mUserDates.isEmpty()){
            textView.setText("Coming Up");
        }
        mUserDates.add(nUserDate);
        userDatesRepository.insertUserDatesTask(nUserDate);
        adapter.notifyDataSetChanged();
    }

    private void deleteUserDate(int index){
        UserDates toDelete = mUserDates.get(index);
        mUserDates.remove(index);
        adapter.notifyDataSetChanged();
        userDatesRepository.deleteUserDates(toDelete);

        //if empty list update the header
        if(mUserDates.isEmpty()){
                textView.setText("Looks like there's nothing planned");
        }
    }

}