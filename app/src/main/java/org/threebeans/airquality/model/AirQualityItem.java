package org.threebeans.airquality.model;

import com.google.gson.annotations.SerializedName;

public class AirQualityItem {

    @SerializedName("dataTime")
    public String dataTime;

    @SerializedName("khaiGrade")
    public String khaiGrade;

    @SerializedName("khaiValue")
    public String khaiValue;

    @SerializedName("pm10Value")
    public String pm10Value;

    @SerializedName("pm25Value")
    public String pm25Value;

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getKhaiGrade() {
        return khaiGrade;
    }

    public void setKhaiGrade(String khaiGrade) {
        this.khaiGrade = khaiGrade;
    }

    public String getKhaiValue() {
        return khaiValue;
    }

    public void setKhaiValue(String khaiValue) {
        this.khaiValue = khaiValue;
    }

    public String getPm10Value() {
        return pm10Value;
    }

    public void setPm10Value(String pm10Value) {
        this.pm10Value = pm10Value;
    }

    public String getPm25Value() {
        return pm25Value;
    }

    public void setPm25Value(String pm25Value) {
        this.pm25Value = pm25Value;
    }
}
