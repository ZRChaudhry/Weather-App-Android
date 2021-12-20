package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyViewHolder extends RecyclerView.ViewHolder {

    TextView hourlyday;
    TextView hourlytime;
    TextView hourlytemp;
    TextView hourlyinfo;
    ImageView hourlypic;




    public HourlyViewHolder(@NonNull View itemView) {
        super(itemView);
        hourlyday=itemView.findViewById(R.id.hourlyday);
        hourlytime=itemView.findViewById(R.id.hourlytime);
        hourlytemp=itemView.findViewById(R.id.hourlytemp);
        hourlyinfo=itemView.findViewById(R.id.hourlyinfo);
        hourlypic=itemView.findViewById(R.id.hourlyimage);

    }
}
