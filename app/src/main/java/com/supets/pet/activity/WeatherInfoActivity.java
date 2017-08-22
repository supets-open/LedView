package com.supets.pet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.supets.pet.ledview.R;
import com.supets.pet.view.LedTextView;
import com.supets.pet.view.WakeScreenUtils;
import com.supets.pet.weather.WeatherApi;
import com.supets.pet.weather.WeatherInfo;


public class WeatherInfoActivity extends Activity {


    private LedTextView time_led;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WakeScreenUtils.keepScreen(this);
        setContentView(R.layout.activity_weather);

        time_led = findViewById(R.id.time_led);
        time_led.startScroll();
        time_led.ForceupdateText("获取天气信息中...");

        WeatherApi.requestWeather("北京", new WeatherApi.WeatherCallBack() {
            @Override
            public void onSuccess(WeatherInfo info) {
                time_led.ForceupdateText(info.toString());
            }

            @Override
            public void onFaill() {
                time_led.updateText("-->获取失败！");
            }
        });
    }

}