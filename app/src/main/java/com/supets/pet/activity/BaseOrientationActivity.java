package com.supets.pet.activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import java.util.List;

/**
 * LedView
 *
 * @user lihongjiang
 * @description
 * @date 2017/8/23
 * @updatetime 2017/8/23
 */

public class BaseOrientationActivity extends Activity {


    private SensorManager manager;
    private SensorListener listener = new SensorListener();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> list = manager.getSensorList(Sensor.TYPE_ALL);  //获取传感器的集合
        for (Sensor sensor : list) {
            Log.v("支持传感器：", sensor.getName());
        }
    }

    @Override
    protected void onResume() {
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //应用在前台时候注册监听器
        manager.registerListener(listener, sensor,
                SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }


    @Override
    protected void onPause() {
        //应用不在前台时候销毁掉监听器
        manager.unregisterListener(listener);
        super.onPause();
    }

    private final class SensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            onOrientationDegree((int) event.values[0],(int) event.values[1],(int) event.values[2]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }

    protected void onOrientationDegree(int degreeX, int degreeY, int degreeZ) {

    }


}
