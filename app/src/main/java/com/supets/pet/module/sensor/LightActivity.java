package com.supets.pet.module.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.supets.pet.ledview.R;
import com.supets.pet.sensor.ledPref;
import com.supets.pet.view.LedTextView;

public class LightActivity extends Activity implements SensorEventListener {
    private LedTextView time_led;
    private SensorManager mSensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        // 获取水平仪的主组件
        time_led = findViewById(R.id.time_led);
        // 获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        // 取消注册
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        // 取消注册
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    private float fanzhi = 100;

    private long startTime = 0;
    private int H = 1;
    private int HOLD = 1;
    private int cha = 0;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        dolight(sensorEvent);
        doJuli(sensorEvent);
    }

    public void doJuli(SensorEvent sensorEvent) {
        float[] its = sensorEvent.values;
        if (its != null && sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            H = its[0] > 0 ? 1 : 0;
            if (HOLD != H) {
                if (HOLD == 1 && H == 0) {
                    final long end = System.currentTimeMillis();
                    if (startTime > 0) {
                        if (cha > 0) {

                            time_led.post(new Runnable() {
                                @Override
                                public void run() {
                                    time_led.ForceupdateText("T:" + (end - startTime));
                                }
                            });
                            // time_led.ForceupdateText("T:" + (cha + end - startTime) / 2);
                            cha = (int) ((cha + end - startTime) / 2);
                        } else {
                            cha = (int) (end - startTime);
                            time_led.post(new Runnable() {
                                @Override
                                public void run() {
                                    time_led.ForceupdateText("T:" + (end - startTime));
                                }
                            });
                        }
                        ledPref.saveTime(cha);
                    }
                    startTime = end;
                }
                HOLD = H;
                Log.v("light", HOLD == 1 ? "H" : "L");
            }
        }
    }


    private void dolight(SensorEvent sensorEvent) {
        //获取精度
        float acc = sensorEvent.accuracy;
        //获取光线强度
        float lux = sensorEvent.values[0];

        H = lux > fanzhi ? 1 : 0;

        if (HOLD != H) {
            if (HOLD == 1 && H == 0) {
                long end = System.currentTimeMillis();
                if (startTime > 0) {
                    if (cha > 0) {
                        time_led.ForceupdateText("T:" + (end - startTime));
                        // time_led.ForceupdateText("T:" + (cha + end - startTime) / 2);
                        cha = (int) ((cha + end - startTime) / 2);
                    } else {
                        cha = (int) (end - startTime);
                        time_led.ForceupdateText("T:" + (end - startTime));
                    }
                }
                startTime = end;
            }
            HOLD = H;
            Log.v("light", HOLD == 1 ? "H" : "L");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


}
