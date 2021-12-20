package com.example.weatherapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DailyActivity extends AppCompatActivity implements Serializable {

    private RecyclerView drv;
    DailyAdapter dAdapter;
    private List<dailyWeather> forecastList = new ArrayList<dailyWeather>();
    private ActivityResultLauncher<Intent> arl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        Intent intent=getIntent();
        if (intent.hasExtra("userInputs")){

            ArrayList<dailyWeather> list=((ArrayList<dailyWeather>)intent.getSerializableExtra("userInputs"));
            //dAdapter.notifyDataSetChanged();

            forecastList.clear();
            forecastList.addAll(list);

            drv=findViewById(R.id.forecastrecycler);
            dAdapter= new DailyAdapter(forecastList,this);
            drv.setAdapter(dAdapter);
            drv.setLayoutManager(new LinearLayoutManager(this));

        }




    }

    public void handleResult(ActivityResult n){
        Intent note = n.getData();
        if (note.hasExtra("userInputs")) {
            ArrayList<dailyWeather> nt = (ArrayList<dailyWeather>) note.getExtras().getSerializable("userInputs");
            this.forecastList.clear();
            this.forecastList.addAll(nt);



        }
    }

}