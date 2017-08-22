package com.supets.pet.weather;

import java.util.ArrayList;

/**
 * LedView
 *
 * @user lihongjiang
 * @description
 * @date 2017/8/22
 * @updatetime 2017/8/22
 */

public class WeatherInfo {

    public String message;
    public int status;
    public String city;
    public int count;

    public Weather data;


    @Override
    public String toString() {
        return " 【" + city + "天气预报】  " + data.toString();
    }

    public static class Weather {

        public String shidu;//84%
        public String pm25;// 70
        public String pm10;//95
        public String quality;//良
        public String wendu;// 28
        public String ganmao;//极少数敏感人群应减少户外活动

        public WeatherDay yesterday;
        public ArrayList<WeatherDay> forecast;


        @Override
        public String toString() {
            return
                    forecast.get(0).date + " "
                            + forecast.get(0).type
                            + " 温度:" + wendu
                            + " pm2.5:" + pm25
                            + " 湿度:" + shidu
                            + " " + forecast.get(0).fx + forecast.get(0).fl
                            + " 户外:" + ganmao
                            + " 注意:" + forecast.get(0).notice
                    ;
        }
    }


    public static class WeatherDay {
        public String date;//21日星期一",
        public String sunrise;//05:30",
        public String high;//高温 32.0℃
        public String low;//低温 24.0℃
        public String sunset;//19:04
        public String aqi;//106,
        public String fx;//南风
        public String fl;//<3级
        public String type;//多云
        public String notice;//今日多云，骑上单车去看看世界吧"
    }

}
