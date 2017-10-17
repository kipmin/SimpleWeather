package com.kipmin.weatherbulletin.Gson.Weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yzl91 on 2017/8/12.
 */

public class HourlyForecast {

    public Cond cond;

    public class Cond {

        @SerializedName("code")
        public String codeHourly;

        @SerializedName("txt")
        public String txtHourly;

    }

    @SerializedName("date")
    public String dateHourly;

    @SerializedName("hum")
    public String humHourly;

    @SerializedName("pop")
    public String popHourly;

    @SerializedName("pres")
    public String presHourly;

    @SerializedName("tmp")
    public String tmpHourly;

    public class Wind {

        @SerializedName("deg")
        public String degHourly;

        @SerializedName("dir")
        public String dirHourly;

        @SerializedName("sc")
        public String scHourly;

        @SerializedName("spd")
        public String spdHourly;
    }
}
