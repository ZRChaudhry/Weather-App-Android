package com.example.weatherapp;

import java.io.Serializable;

public class dailyWeather implements Serializable {

    private final String date;
    private final String maxmin;
    private final String dailydes;
    private final String precip;
    private final String index;
    private final String dailymorningtemp;
    private final String dailydaytimetemp;
    private final String dailyeveningtemp;
    private final String dailynighttemp;
    private final String icon;


    public dailyWeather(String date, String maxmin, String dailydes, String precip, String index, String dailymorningtemp,
                        String dailydaytimetemp, String dailyeveningtemp, String dailynighttemp, String icon) {
        this.date = date;
        this.maxmin = maxmin;
        this.dailydes = dailydes;
        this.precip = precip;
        this.index = index;
        this.dailymorningtemp = dailymorningtemp;
        this.dailydaytimetemp = dailydaytimetemp;
        this.dailyeveningtemp = dailyeveningtemp;
        this.dailynighttemp = dailynighttemp;
        this.icon = icon;
    }

    String getDate() {
        return date;
    }
    String getMaxMin() {
        return maxmin;
    }
    String getDes() {return dailydes;}
    String getPrecip(){return precip;}
    String getIndex() {return index;}
    String getMorningTemp() { return dailymorningtemp;}
    String getDayTimeTemp() { return dailydaytimetemp;}
    String getEveningTemp() { return dailyeveningtemp;}
    String getNightTemp() { return dailynighttemp;    }
    String getIcon(){return icon;}

}
