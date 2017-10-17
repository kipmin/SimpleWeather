package com.kipmin.weatherbulletin.Gson.Weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yzl91 on 2017/8/1.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("cnty")
    public String cntyName;

    @SerializedName("id")
    public String weatherId;

    @SerializedName("lat")
    public String latitude;

    @SerializedName("lon")
    public String longitude;

    @SerializedName("prov")
    public String province;

    public Update update;

    public class Update {

        @SerializedName("loc")
        public String updateTimeLoc;

        @SerializedName("utc")
        public String updateTimeUtc;

    }
}
