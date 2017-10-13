package com.kipmin.simpleweather.Utility;

import android.util.Log;

import com.google.gson.Gson;
import com.kipmin.simpleweather.Db.CityDb;
import com.kipmin.simpleweather.Gson.Weather.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by yzl91 on 2017/9/18.
 */

public class Utility {

    public static boolean handleCity (String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject cityObject = jsonObject.getJSONObject("WeatherList");
            JSONArray cityArray = cityObject.getJSONArray("cityList");
            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject city = cityArray.getJSONObject(i);
                CityDb cityDb = new CityDb();
                cityDb.setCnCity(city.getString("cnName"));
                cityDb.setWeatherId(city.getString("weatherId"));
                cityDb.save();
                continue;
            }
            Log.d("Kipmin", "handleCity: OK");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            Log.d("Utility", "handleWeatherResponse: OK");
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
