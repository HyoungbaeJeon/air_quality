package org.threebeans.airquality;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.threebeans.airquality.model.AirQuality;
import org.threebeans.airquality.model.AirQualityItem;
import org.threebeans.airquality.service.AirQualityService;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ColumnChartView chart;
    private ColumnChartData columnChartData;
    private AirQualityService airQualityService;

    private final Handler resumeHandler = new Handler(Looper.getMainLooper());

    private final Runnable RESUME_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            onResume();
            resumeHandler.postDelayed(this, 1800000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Retrofit airQualityRetrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.airkorea.or.kr/openapi/services/rest/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        airQualityService = airQualityRetrofit.create(AirQualityService.class);

        chart = (ColumnChartView) findViewById(R.id.chart);
        chart.setZoomEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        resumeHandler.post(RESUME_RUNNABLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        resumeHandler.removeCallbacks(RESUME_RUNNABLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Call<AirQuality> call = airQualityService.getAirQualityInfo();
        call.enqueue(new Callback<AirQuality>() {
            @Override
            public void onResponse(Call<AirQuality> call, Response<AirQuality> response) {
                try{
                    List<AirQualityItem> airQualityItemList = response.body().getAirQualityItemList();

                    List<Column> columns = new ArrayList<>();
                    List<SubcolumnValue> values = new ArrayList<>();
                    List<AxisValue> axisValues = new ArrayList<>();

                    int labelIndex = 0;
                    for(int i = airQualityItemList.size()-1 ; i >= 0 ; i--){

                        AirQualityItem airQualityItem = airQualityItemList.get(i);
                        String grade = airQualityItem.getKhaiGrade();

                        int color = 0;

                        switch (grade){
                            case "1":
                                color = Color.parseColor("#03A9F4");
                                break;
                            case "2":
                                color = Color.parseColor("#4CAF50");
                                break;
                            case "3":
                                color = Color.parseColor("#FFC107");
                                break;
                            case "4":
                                color = Color.parseColor("#F44336");
                                break;
                        }


                        values.add(new SubcolumnValue(Integer.parseInt(airQualityItem.getKhaiValue()), color));

                        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
                        DateTime dateTime = formatter.parseDateTime(airQualityItem.getDataTime());

                        axisValues.add(new AxisValue(labelIndex).setLabel(dateTime.toString("MM-dd HH:mm")));

                        Column column = new Column(values);

                        column.setHasLabels(true);
                        columns.add(column);
                        labelIndex++;
                    }

                    Axis axisX = new Axis(axisValues);

                    axisX.setName("날짜");
                    axisX.setLineColor(Color.WHITE);
                    axisX.setTextColor(Color.WHITE);

                    columnChartData = new ColumnChartData(columns);
                    columnChartData.setAxisXBottom(axisX);

                    chart.setColumnChartData(columnChartData);
                }catch (Exception exception){
                    FirebaseCrash.report(exception);
                }

            }

            @Override
            public void onFailure(Call<AirQuality> call, Throwable t) {
                FirebaseCrash.report(t);
            }
        });
    }

}
