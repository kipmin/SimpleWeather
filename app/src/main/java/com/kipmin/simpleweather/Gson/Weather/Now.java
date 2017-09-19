package com.kipmin.simpleweather.Gson.Weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yzl91 on 2017/8/1.
 */

public class Now {

    public Cond cond;

    public class Cond {

        @SerializedName("code")
        public String weatherCode;

        @SerializedName("txt")
        public String weatherTxt;

    }

    @SerializedName("fl")
    public String feelLike;

    @SerializedName("hum")
    public String humidity;

    @SerializedName("pcpn")
    public String precipitation;

    @SerializedName("pres")
    public String airPres;

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("vis")
    public String visibility;

    public Wind wind;

    public class Wind {

        @SerializedName("deg")
        public String windDeg;

        @SerializedName("dir")
        public String windDir;

        @SerializedName("sc")
        public String windSc;

        @SerializedName("spd")
        public String windSpd;
    }
}
