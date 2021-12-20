package com.example.weatherapp;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;

public class WeatherInfoRunnable implements Runnable {

    private static final String TAG = "WeatherDownloadRunnable";
    private static final String weatherURL = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String APIKey = "cf73ec675e680d27d04170dda5c6e35c";
    private final MainActivity mainActivity;
    private final DailyActivity da;
    private final Double lat;
    private final Double lon;
    private final boolean fahrenheit;


    WeatherInfoRunnable(MainActivity mainActivity, Double lat, Double lon, boolean fahrenheit, DailyActivity da) {
        this.mainActivity = mainActivity;
        this.fahrenheit = fahrenheit;
        this.lat = lat;
        this.lon = lon;
        this.da=da;

    }

    private String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X"; // We'll use 'X' as the default if we get a bad value
    }

    @Override
    public void run() {
        Uri.Builder buildURL = Uri.parse(weatherURL).buildUpon();

        //Creates the API URL call for the Loaction,
        buildURL.appendQueryParameter("lat", String.valueOf(lat));
        buildURL.appendQueryParameter("lon", String.valueOf(lon));
        buildURL.appendQueryParameter("units", (fahrenheit ? "imperial" : "metric"));
        buildURL.appendQueryParameter("appid", APIKey);
        buildURL.appendQueryParameter("exclude", "minutely");


        String urlToUse = buildURL.build().toString();
        Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(urlToUse);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();

            //If the Connection is not able to Connect
            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                InputStream is = connection.getErrorStream();
                BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                handleError(sb.toString());
                return;
            }

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            Log.d(TAG, "doInBackground: " + sb.toString());


            //If something else goes wrong
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
            handleResults(null);
            return;
        }
        handleResults(sb.toString());
    }
    public void handleError(String s) {
        String msg = "Error: ";
        try {
            JSONObject jObjMain = new JSONObject(s);
            msg += jObjMain.getString("message");
        } catch (JSONException e) {
            msg += e.getMessage();
        }
        String finalMsg = msg;
        mainActivity.runOnUiThread(() -> mainActivity.handleError(finalMsg));
    }
    public void handleResults(final String jsonString) {
        Log.e(TAG, "json inputs: " + jsonString);
        final Weather w = parseJSON(jsonString);
        final ArrayList<hourlyWeather> weatherList = hparseJSON(jsonString);
        final ArrayList<dailyWeather> forcastList = fparseJSON(jsonString);
        mainActivity.runOnUiThread(() -> mainActivity.updateData(w,weatherList,forcastList));
      //  da.runOnUiThread(() -> da.updateData(forcastList));



    }
    private ArrayList<dailyWeather> fparseJSON(String s) {
        try {
            JSONObject jObjMain = new JSONObject(s);
            JSONArray daily = jObjMain.getJSONArray("daily");

            ArrayList<dailyWeather> forecastList=new ArrayList<>();
            for (int n = 0;n<7;n++ ) {
                JSONObject DWeather = (JSONObject) daily.get(n);
                JSONArray weather=DWeather.getJSONArray("weather");
                JSONObject dw=(JSONObject) weather.get(0);
                JSONObject dailytemp = DWeather.getJSONObject("temp");

                long dt =DWeather.getLong("dt");
                String date = new SimpleDateFormat("EEEE, MM/dd/yyyy", Locale.getDefault()).format(new Date(dt * 1000));

                String maxtemp = dailytemp.getString("max");
                String mintemp = dailytemp.getString("min");
                String maxmin=(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(maxtemp)))+'/'+
                        (String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(mintemp)));

                String dailydes = dw.getString("description");

                String pop= DWeather.getString("pop");
                String precip="Precip: "+pop+'%';

                String uv= DWeather.getString("uvi");
                String index= "UV Index: "+uv;

                String dailymorningtemp = dailytemp.getString("morn");
                dailymorningtemp=(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(dailymorningtemp)));

                String dailydaytimetemp = dailytemp.getString("day");
                dailydaytimetemp=(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(dailydaytimetemp)));

                String dailyeveningtemp = dailytemp.getString("eve");
                dailyeveningtemp=(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(dailyeveningtemp)));

                String dailynighttemp = dailytemp.getString("night");
                dailynighttemp=(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(dailynighttemp)));


                String icon = dw.getString("icon");

                forecastList.add(new dailyWeather(date,maxmin,dailydes,precip,index,dailymorningtemp,dailydaytimetemp,dailyeveningtemp
                ,dailynighttemp,icon));
            }
            return forecastList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<hourlyWeather> hparseJSON(String s) {
        try {
            JSONObject jObjMain = new JSONObject(s);
            JSONArray hourly = jObjMain.getJSONArray("hourly");
            String timezone_offeset=jObjMain.getString("timezone_offset");

            ArrayList<hourlyWeather> weatherList=new ArrayList<>();
            for (int n = 0;n<48;n++ ) {
                JSONObject HWeather = (JSONObject) hourly.get(n);
                JSONArray weather=HWeather.getJSONArray("weather");
                JSONObject hw=(JSONObject) weather.get(0);
                String hourlytemp = HWeather.getString("temp");
                hourlytemp=(String.format("%.0f° " + (fahrenheit ? "F" : "C"), Double.parseDouble(hourlytemp)));
                String hourlydes = hw.getString("description");
                long dt =HWeather.getLong("dt");
                String date = new SimpleDateFormat("EEEE ", Locale.getDefault()).format(new Date(dt * 1000));
                String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(dt * 1000));
                Long timeoffset=Long.parseLong(timezone_offeset);
                String icon = hw.getString("icon");
                weatherList.add(new hourlyWeather(date,time,hourlytemp,hourlydes,icon));
            }
            return weatherList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Weather parseJSON(String s) {

        try {
            JSONObject jObjMain = new JSONObject(s);
            // CURRENT SECTION
            JSONObject current = jObjMain.getJSONObject("current");
            JSONArray weather = current.getJSONArray("weather");
            JSONObject jWeather = (JSONObject) weather.get(0);
            //temp
            String currentTemp = current.getString("temp");
            //Description
            String currentdes = jWeather.getString("description");
            String currentmain = jWeather.getString("main");
            //humidity
            String currenthumidity = current.getString("humidity");
            // wind speed & degree
            String windSpeed = current.getString("wind_speed");
            double windDeg = Double.parseDouble(current.getString("wind_deg"));
            // current wind
            String windDegree = getDirection(windDeg);
            String currentwind = windSpeed + " MPH " + " at " + windDegree;
            // date/time
            long dt = current.getLong("dt");
            String currentdate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault()).format(new Date(dt * 1000));
            //feels like
            String currentfeelslike = current.getString("feels_like");
            //UV index
            String currentuv = "UV Index: " + current.getString("uvi");
            //visibility
            double visibility = current.getDouble("visibility");
            visibility = visibility * (0.000621371);
            String currentvisibilty = "Visibility: " + visibility + " mi";
            // location
            String currentcity = jObjMain.getString("timezone");
            // sunset section
            long jSunset = current.getLong("sunset");
            String currentsunset = "Sunset: " + new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(jSunset * 1000));
            //Sunrise
            long jSunrise = current.getLong("sunrise");
            String currentsunrise = "Sunrise: " + new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(jSunrise * 1000));
            Log.e(TAG, "json inputs: ");

            // DAILY SECTION
            JSONArray daily = jObjMain.getJSONArray("daily");
            JSONObject DWeather = (JSONObject) daily.get(0);
            JSONObject dailytemp = DWeather.getJSONObject("temp");
            //morning, daytime, evening, night temp
            String dailymorningtemp = dailytemp.getString("morn");
            String dailydaytimetemp = dailytemp.getString("day");
            String dailyeveningtemp = dailytemp.getString("eve");
            String dailynighttemp = dailytemp.getString("night");
            //image code
            String currenticon = jWeather.getString("icon");

            return new Weather(currentcity, currentTemp, currentdes, currenthumidity, currentwind, currentdate, currentfeelslike,
                    currentuv, currentvisibilty, currentsunrise, currentsunset, currentmain,
                    dailymorningtemp, dailydaytimetemp, dailyeveningtemp, dailynighttemp, currenticon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

