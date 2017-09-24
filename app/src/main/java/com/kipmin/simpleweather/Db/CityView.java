package com.kipmin.simpleweather.Db;

import org.litepal.crud.DataSupport;

/**
 * Created by yzl91 on 2017/9/24.
 */

public class CityView extends DataSupport {

    private String cnCity;
    private String weatherId;

    public String getCnCity() {
        return cnCity;
    }

    public void setCnCity(String cnCity) {
        this.cnCity = cnCity;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

}
