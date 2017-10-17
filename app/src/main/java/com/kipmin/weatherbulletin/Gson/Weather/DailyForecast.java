package com.kipmin.weatherbulletin.Gson.Weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yzl91 on 2017/8/12.
 */

public class DailyForecast {

    public Astro astro;

    public class Astro {
        //月升时间
        @SerializedName("mr")
        public String mrDaily;
        //月落时间
        @SerializedName("ms")
        public String msDaily;
        //日升时间
        @SerializedName("sr")
        public String srDaily;
        //日落时间
        @SerializedName("ss")
        public String ssDaily;
    }

    public Cond cond;

    public class Cond {

        @SerializedName("code_d")
        public String codeDDaily;

        @SerializedName("code_n")
        public String codeNDaily;

        @SerializedName("txt_d")
        public String txtDDaily;

        @SerializedName("txt_n")
        public String txtNDaily;

    }

    @SerializedName("date")
    public String dateDaily;
    //湿度
    @SerializedName("hum")
    public String humDaily;

    @SerializedName("pcpn")
    public String pcpnDaily;

    @SerializedName("pop")
    public String popDaily;

    @SerializedName("pres")
    public String presDaily;

    @SerializedName("tmp")
    public TmpDaily tmpDaily;

    public class TmpDaily {

        @SerializedName("max")
        public String maxDaily;

        @SerializedName("min")
        public String minDaily;

    }

    @SerializedName("vis")
    public String visDaily;

    public Wind wind;

    public class Wind {

        @SerializedName("deg")
        public String degDaily;

        @SerializedName("dir")
        public String dirDaily;

        @SerializedName("sc")
        public String scDaily;

        @SerializedName("spd")
        public String spdDaily;
    }

}
