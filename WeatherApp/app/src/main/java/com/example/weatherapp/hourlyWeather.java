package com.example.weatherapp;

public class hourlyWeather {

    private final String date;
    private final String time;
    private final String hourlytemp;
    private final String hourlydes;
    private final String icon;


    hourlyWeather(String date, String time, String hourlytemp, String hourlydes, String icon) {

        this.date=date;
        this.time=time;
        this.hourlydes=hourlydes;
        this.hourlytemp=hourlytemp;
        this.icon=icon;


    }

    String getDate() {
        return date;
    }
    String getTime() {
        return time;
    }
    String getHourlytemp() {
        return hourlytemp;
    }
    String getHourlydes() {
        return hourlydes;
    }
    String getHourlyIcon(){return icon;}

}
