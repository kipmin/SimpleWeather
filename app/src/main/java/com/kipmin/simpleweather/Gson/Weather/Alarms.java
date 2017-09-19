package com.kipmin.simpleweather.Gson.Weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yzl91 on 2017/8/12.
 */

public class Alarms {

    @SerializedName("level")
    public String levelArarms;

    @SerializedName("stat")
    public String statAlarms;

    @SerializedName("title")
    public String titleAlarms;

    @SerializedName("txt")
    public String txtAlarms;

    @SerializedName("type")
    public String typeAlarms;

}
