package org.threebeans.airquality.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirQuality {
    @SerializedName("list")
    List<AirQualityItem> airQualityItemList;

    public List<AirQualityItem> getAirQualityItemList() {
        return airQualityItemList;
    }

    public void setAirQualityItemList(List<AirQualityItem> airQualityItemList) {
        this.airQualityItemList = airQualityItemList;
    }
}
