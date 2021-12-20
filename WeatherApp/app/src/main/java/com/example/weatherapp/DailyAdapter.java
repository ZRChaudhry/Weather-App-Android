package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyViewHolder> {
    private List<dailyWeather> forecastList;
    private DailyActivity mainAct;

    DailyAdapter(List<dailyWeather> forecastList, DailyActivity ma) {
        this.forecastList = forecastList;
        this.mainAct = ma;
    }






    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_list, parent, false);

        return new DailyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {

        dailyWeather f= forecastList.get(position);

        holder.dailydate.setText(f.getDate());
        holder.maxmintemp.setText(f.getMaxMin());
        holder.dailydes.setText(f.getDes());
        holder.dailyprecip.setText(f.getPrecip());
        holder.dailyindex.setText(f.getIndex());
        holder.morningtemp.setText(f.getMorningTemp());
        holder.daytemp.setText(f.getDayTimeTemp());
        holder.evetemp.setText(f.getEveningTemp());
        holder.nighttemp.setText(f.getNightTemp());

        String iconCode = "_" + f.getIcon();
        int iconResId = holder.dailypic.getResources().getIdentifier(iconCode,"drawable", mainAct.getPackageName());
        holder.dailypic.setImageResource(iconResId);

    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }
}
