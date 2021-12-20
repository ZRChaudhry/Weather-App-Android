package com.example.weatherapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "MainActivity";
    private boolean fahrenheit = true;
    private String m_Text = "Chicago,US";
    private String locale;
    private double lat;
    private double lon;
    private RecyclerView rv;
    HourlyAdapter hAdapter;
    DailyActivity da;
    private ActivityResultLauncher<Intent> arl;


    private  List<hourlyWeather> weatherList = new ArrayList<>();
    private  List<dailyWeather> forecastList = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasNetworkConnection();
        getLocationName(m_Text);
        getLatLon(locale);
        doDownload();

        rv=findViewById(R.id.hourlyrecycler);
        hAdapter= new HourlyAdapter(weatherList,this);
        rv.setAdapter(hAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private String getLocationName(String userProvidedLocation) {
        Geocoder geocoder = new Geocoder(this); // Here, “this” is an Activity
        try {
            List<Address> address;
            address = geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                // Nothing returned!
                return null;
            }
            String country = address.get(0).getCountryCode();
            String p1 = "";
            String p2 = "";
            if (country.equals("US")) {
                p1 = address.get(0).getLocality();
                p2 = address.get(0).getAdminArea();
            } else {
                p1 = address.get(0).getLocality();
                if (p1 == null)
                    p1 = address.get(0).getSubAdminArea();
                p2 = address.get(0).getCountryName();
            }
            locale = p1 + ", " + p2;
            return locale;
        } catch (IOException e) {
            // Failure to get an Address object
            return null;
        }
    }

    private double[] getLatLon(String userProvidedLocation) {
        Geocoder geocoder = new Geocoder(this); // Here, “this” is an Activity
        try {
            List<Address> address =
                    geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                // Nothing returned!
                return null;
            }
            lat = address.get(0).getLatitude();
            lon = address.get(0).getLongitude();

            return new double[] {lat, lon};
        } catch (IOException e) {
            // Failure to get an Address object
            return null;
        }
    }

    private void doDownload() {

        // Load the data
        WeatherInfoRunnable weatherLoaderRunnable = new WeatherInfoRunnable(this, lat, lon, fahrenheit,da);
        new Thread(weatherLoaderRunnable).start();
    }

    //Create Menu Option Button Listeners
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate((R.menu.homeweathermenu), menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.imperial) {
            Toast.makeText(this, "Imperial", Toast.LENGTH_SHORT).show();
            if (fahrenheit==true){
                fahrenheit=false;
            }else if (fahrenheit==false){
                fahrenheit=true;
            }

        } else if (item.getItemId() == R.id.forecast) {
            Toast.makeText(this, "Daily Forecast", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DailyActivity.class);
            intent.putExtra("userInputs", (Serializable) forecastList);
            //setResult(RESULT_OK,intent);
            //arl.launch(intent);
            startActivity(intent);





        } else if (item.getItemId() == R.id.location) {
            Toast.makeText(this, "Change Location", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("New Location");
            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    getLocationName(m_Text);
                    getLatLon(locale);

                    doDownload();
                }});
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }});
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }



    public void updateData(Weather weather, ArrayList<hourlyWeather> weatherList, ArrayList<dailyWeather> forcastList) {
        if (weather == null) {
            Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
            return;
        }
        TextView city = findViewById(R.id.homelocation);
        city.setText(m_Text);

        TextView temp = findViewById(R.id.hometemp);
        temp.setText(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(weather.getCurrentTemp())));

        TextView condition = findViewById(R.id.homefeel);
        condition.setText("Feels Like: "+ (String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(weather.getCurrentFeelsLike()))));

        TextView description = findViewById(R.id.homeinfo);
        description.setText(String.format( weather.getCurrentDescription() ));

        TextView date = findViewById(R.id.homedate);
        date.setText(String.format(weather.getCurrentDate()));

        TextView wind = findViewById(R.id.homewind);
        wind.setText(String.format("Wind: " + (weather.getCurrentWind())));

        TextView humid = findViewById(R.id.homehumidity);
        humid.setText(String.format(Locale.getDefault(), "Humidity: %.0f%%", Double.parseDouble(weather.getCurrentHumidity())));

        TextView sunset= findViewById(R.id.homesunset);
        sunset.setText(weather.getCurrentSunset());

        TextView sunrise= findViewById(R.id.homesunrise);
        sunrise.setText(weather.getCurrentSunrise());

        TextView uv= findViewById(R.id.homeindex);
        uv.setText(weather.getCurrentUV());

        TextView visibility= findViewById(R.id.homevisibility);
        visibility.setText(weather.getCurrentVisibilty());

        TextView dailymorningtemp= findViewById(R.id.morningtemp);
        dailymorningtemp.setText(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(weather.getDailyMorningTemp())));

        TextView dailydaytemp= findViewById(R.id.daytimetemp);
        dailydaytemp.setText(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(weather.getDailyDayTimeTemp())));

        TextView dailyevening= findViewById(R.id.eveningtemp);
        dailyevening.setText(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(weather.getDailyEveningTemp())));

        TextView dailynight= findViewById(R.id.nighttemp);
        dailynight.setText(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(weather.getDailyNightTemp())));

       ImageView image = findViewById(R.id.homeimageView);
       String iconCode = "_" + weather.getCurrentIcon();
       int iconResId = this.getResources().getIdentifier(iconCode,
                "drawable", this.getPackageName());
        image.setImageResource(iconResId);

        this.weatherList.clear();
        this.weatherList.addAll(weatherList);
        hAdapter.notifyDataSetChanged();

        Log.e(TAG, "json inputs: " + forcastList.size());

        this.forecastList.clear();
        this.forecastList.addAll(forcastList);

    }

    public void handleError(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Data Problem")
                .setMessage(s)
                .setPositiveButton("OK", (dialogInterface, i) -> {})
                .create().show();

    }









}