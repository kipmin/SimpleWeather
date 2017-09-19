package com.kipmin.simpleweather.Gson.Weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yzl91 on 2017/8/12.
 */

public class Aqi {

    @SerializedName("aqi")
    public CityAqi cityAqi;

    public class CityAqi {

        @SerializedName("aqi")
        public String aqiAqi;

        @SerializedName("co")
        public String coAqi;

        @SerializedName("no2")
        public String no2Aqi;

        @SerializedName("o3")
        public String o3Aqi;

        @SerializedName("pm10")
        public String pm10Aqi;

        @SerializedName("pm25")
        public String pm25Aqi;

        @SerializedName("qlty")
        public String qltyAqi;

        @SerializedName("so2")
        public String so2Aqi;

    }
}
