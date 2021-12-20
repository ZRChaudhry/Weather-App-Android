package com.example.weatherapp;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Weather extends ArrayList<hourlyWeather> {

    //icons
    private final String currenticon;

    //current
    private final String currentcity;
    private final String currentTemp;
    private final String currentdes;
    private final String currenthumidity;
    private final String currentwind;
    private final String currentdate;
    private final String currentfeelslike;
    private final String currentuv;
    private final String currentvisibilty;
    private final String currentsunrise;
    private final String currentsunset;
    private final String currentmain;

    //Hourly
    private final String dailymorningtemp;
    private final String dailydaytimetemp;
    private final String dailyeveningtemp;
    private final String dailynighttemp;

    Weather (String currentcity, String currentTemp,String currentdes,
             String currenthumidity,String currentwind,String currentdate,String currentfeelslike,
             String currentuv, String currentvisibilty, String currentsunrise, String currentsunset, String currentmain, String dailymorningtemp,
             String dailydaytimetemp, String dailyeveningtemp, String dailynighttemp,String currenticon ) {

        //Icons
        this.currenticon=currenticon;

        //Current
        this.currentcity = currentcity;
        this.currentTemp = currentTemp;
        this.currentdes=currentdes;
        this.currenthumidity = currenthumidity;
        this.currentwind = currentwind;
        this.currentdate = currentdate;
        this.currentfeelslike=currentfeelslike;
        this.currentuv=currentuv;
        this.currentvisibilty=currentvisibilty;
        this.currentsunrise=currentsunrise;
        this.currentsunset=currentsunset;
        this.currentmain=currentmain;

        //Hourly
        this.dailymorningtemp=dailymorningtemp;
        this.dailydaytimetemp=dailydaytimetemp;
        this.dailyeveningtemp=dailyeveningtemp;
        this.dailynighttemp=dailynighttemp;
    }

    //Icons
    String getCurrentIcon(){return currenticon;}

    //Current
    String getCurrentCity() {
        return currentcity;
    }
    String getCurrentTemp() {
        return currentTemp;
    }
    String getCurrentDescription() { return currentdes; }
    String getCurrentHumidity() {
        return currenthumidity;
    }
    String getCurrentWind() {
        return currentwind;
    }
    String getCurrentDate() {
        return currentdate;
    }
    String getCurrentFeelsLike() {
        return currentfeelslike;
    }
    String getCurrentUV() {
        return currentuv;
    }
    String getCurrentVisibilty(){return currentvisibilty;}
    String getCurrentSunset() {  return currentsunset;    }
    String getCurrentSunrise() {  return currentsunrise;    }
    String getCurrentMain() {  return currentmain;    }

    //Hourly
    String getDailyMorningTemp() {  return dailymorningtemp;    }
    String getDailyDayTimeTemp() {  return dailydaytimetemp;    }
    String getDailyEveningTemp() {  return dailyeveningtemp;    }
    String getDailyNightTemp() {  return dailynighttemp;    }

    //Hourly Recycler View







}
