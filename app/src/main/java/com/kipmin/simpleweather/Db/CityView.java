package com.kipmin.simpleweather.Db;

import org.litepal.crud.DataSupport;

/**
 * Created by yzl91 on 2017/9/24.
 */

public class CityView extends DataSupport {

    private String cnName;
    private String weatherId;

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

}
