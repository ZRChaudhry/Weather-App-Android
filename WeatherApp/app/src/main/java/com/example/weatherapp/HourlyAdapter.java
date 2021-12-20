package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyViewHolder> {

    private  List<hourlyWeather> weatherList;
    private  MainActivity mainAct;

    HourlyAdapter(List<hourlyWeather> weatherList, MainActivity ma) {
        this.weatherList = weatherList;
        this.mainAct = ma;
    }


    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_list, parent, false);

        return new HourlyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {

        hourlyWeather w= weatherList.get(position);

        holder.hourlyday.setText(w.getDate());
        holder.hourlyinfo.setText(w.getHourlydes());
        holder.hourlytemp.setText(w.getHourlytemp());
        holder.hourlytime.setText(w.getTime());

        String iconCode = "_" + w.getHourlyIcon();
        int iconResId = holder.hourlypic.getResources().getIdentifier(iconCode,"drawable", mainAct.getPackageName());
        holder.hourlypic.setImageResource(iconResId);

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
