package com.kipmin.weatherbulletin.Gson.Weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yzl91 on 2017/8/1.
 */

public class Weather {

    @SerializedName("alarms")
    public List<Alarms> alarmsList;

    public Aqi aqi;

    public Basic basic;

    @SerializedName("daily_forecast")
    public List<DailyForecast> dailyForecastList;

    @SerializedName("hourly_forecast")
    public List<HourlyForecast> hourlyForecastList;

    public Now now;

    public String status;

    public Suggestion suggestion;

}
