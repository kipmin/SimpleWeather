package com.kipmin.weatherbulletin.Db;

import org.litepal.crud.DataSupport;

/**
 * Created by yzl91 on 2017/9/18.
 */

public class CityDb extends DataSupport {

    private String cnName;
    private String weatherId;
//    private Double latitude;
//    private Double longitude;

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

//    public Double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(Double latitude) {
//        this.latitude = latitude;
//    }
//
//    public Double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(Double longitude) {
//        this.longitude = longitude;
//    }
}
