package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyViewHolder extends RecyclerView.ViewHolder {

    TextView dailydate;
    TextView maxmintemp;
    TextView dailydes;
    TextView dailyprecip;
    TextView dailyindex;
    TextView morningtemp;
    TextView daytemp;
    TextView evetemp;
    TextView nighttemp;
    ImageView dailypic;





    public DailyViewHolder(@NonNull View itemView) {
        super(itemView);

        dailydate=itemView.findViewById(R.id.dailytime);
        maxmintemp=itemView.findViewById(R.id.tempmaxmin);
        dailydes=itemView.findViewById(R.id.dailydes);
        dailyprecip=itemView.findViewById(R.id.dailyprecip);
        dailyindex=itemView.findViewById(R.id.dailyindex);
        morningtemp=itemView.findViewById(R.id.dailymortemp);
        daytemp=itemView.findViewById(R.id.dailydaytemp);
        evetemp=itemView.findViewById(R.id.dailyevetemp);
        nighttemp=itemView.findViewById(R.id.dailynighttemp);
        dailypic=itemView.findViewById(R.id.dailyimage);

    }
}
