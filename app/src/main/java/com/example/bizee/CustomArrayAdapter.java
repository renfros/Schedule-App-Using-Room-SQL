package com.example.bizee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<UserDates> {

    private Context mContext;
    private int mResource;

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<UserDates> dates) {
        super(context, resource, dates);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

       String header = getItem(position).getHeader();
       String time = getItem(position).getTime();
       String date = getItem(position).getDate();
       String priority = getItem(position).getPriority();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView txtHeader = convertView.findViewById(R.id.txtHeader);
        TextView txtTimeAndDate = convertView.findViewById(R.id.txtTimeAndDate);
        ImageView imgPriority = convertView.findViewById(R.id.imgPriority);

        txtHeader.setText(header);
        txtTimeAndDate.setText(time + " | " + date);

        //Switch Statement for the icon based on priority
        switch(priority){

            case "high":
                    imgPriority.setImageResource(R.drawable.ic_high_priority);
                break;

            case "medium":
                    imgPriority.setImageResource(R.drawable.ic_medium_priority);
                break;

            default:
                    imgPriority.setImageResource(R.drawable.ic_low_priority);
                break;


        }

        return convertView;
    }


}
