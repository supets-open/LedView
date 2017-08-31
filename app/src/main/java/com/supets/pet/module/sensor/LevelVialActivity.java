package com.supets.pet.module.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.supets.pet.ledview.R;
import com.supets.pet.sensor.LevelVialView;

public class LevelVialActivity extends Activity implements SensorEventListener {
    // 定义水平仪的仪表盘
    private LevelVialView show;
    // 定义水平仪能处理的最大倾斜角，超过该角度，气泡将直接在位于边界。
    private int MAX_ANGLE = 30;
    // 定义Sensor管理器
    SensorManager mSensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        // 获取水平仪的主组件
        show = findViewById(R.id.show);
        // 获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 为系统的方向传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
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


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // 计算x、y点的气泡是否处于水平仪的仪表盘内
    private boolean isContain(int x, int y) {
        // 计算气泡的圆心座标X、Y
        int bubbleCx = x + show.bubble.getWidth() / 2;
        int bubbleCy = y + show.bubble.getWidth() / 2;
        // 计算水平仪仪表盘的圆心座标X、Y
        int backCx = show.back.getWidth() / 2;
        int backCy = show.back.getWidth() / 2;
        // 计算气泡的圆心与水平仪仪表盘的圆心之间的距离。
        double distance = Math.sqrt((bubbleCx - backCx) * (bubbleCx - backCx)
                + (bubbleCy - backCy) * (bubbleCy - backCy));
        // 若两个圆心的距离小于它们的半径差，即可认为处于该点的气泡依然位于仪表盘内
        if (distance < (show.back.getWidth() - show.bubble.getWidth()) / 2) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // 获取触发event的传感器类型
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                // 获取与Y轴的夹角
                float yAngle = values[1];
                // 获取与Z轴的夹角
                float zAngle = values[2];
                // 气泡位于中间时（水平仪完全水平），气泡的X、Y座标
                int x = (show.back.getWidth() - show.bubble.getWidth()) / 2;
                int y = (show.back.getHeight() - show.bubble.getHeight()) / 2;
                // 如果与Z轴的倾斜角还在最大角度之内
                if (Math.abs(zAngle) <= MAX_ANGLE) {
                    // 根据与Z轴的倾斜角度计算X座标的变化值（倾斜角度越大，X座标变化越大）
                    int deltaX = (int) ((show.back.getWidth() - show.bubble
                            .getWidth()) / 2 * zAngle / MAX_ANGLE);
                    x += deltaX;
                }
                // 如果与Z轴的倾斜角已经大于MAX_ANGLE，气泡应到最左边
                else if (zAngle > MAX_ANGLE) {
                    x = 0;
                }
                // 如果与Z轴的倾斜角已经小于负的MAX_ANGLE，气泡应到最右边
                else {
                    x = show.back.getWidth() - show.bubble.getWidth();
                }
                // 如果与Y轴的倾斜角还在最大角度之内
                if (Math.abs(yAngle) <= MAX_ANGLE) {
                    // 根据与Y轴的倾斜角度计算Y座标的变化值（倾斜角度越大，Y座标变化越大）
                    int deltaY = (int) ((show.back.getHeight() - show.bubble
                            .getHeight()) / 2 * yAngle / MAX_ANGLE);
                    y += deltaY;
                }
                // 如果与Y轴的倾斜角已经大于MAX_ANGLE，气泡应到最下边
                else if (yAngle > MAX_ANGLE) {
                    y = show.back.getHeight() - show.bubble.getHeight();
                }
                // 如果与Y轴的倾斜角已经小于负的MAX_ANGLE，气泡应到最右边
                else {
                    y = 0;
                }
                // 如果计算出来的X、Y座标还位于水平仪的仪表盘内，更新水平仪的气泡座标
                if (isContain(x, y)) {
                    show.bubbleX = x;
                    show.bubbleY = y;
                }
                // 通知系统重回MyView组件
                show.postInvalidate();
                break;
        }
    }


}
