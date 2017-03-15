package org.threebeans.airquality.service;

import org.threebeans.airquality.model.AirQuality;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AirQualityService {

    @GET("ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?stationName=서대문구&dataTerm=month&pageNo=1&numOfRows=6&ServiceKey=6oROxkNtYkj%2FF8CNNXqLy70feyJsHbKjXJwdH7KnkCqFpwsi9rr8RKJrYfep%2BYVd9vqBso9ffxuggVDTV8%2FKJw%3D%3D&ver=1.3&_returnType=json")
    Call<AirQuality> getAirQualityInfo();
}
