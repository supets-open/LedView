package com.supets.pet.module.sensor;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.supets.pet.sensor.ShakeEngine;

/**
 * LedView
 *
 * @user lihongjiang
 * @description
 * @date 2017/8/23
 * @updatetime 2017/8/23
 */

public class BaseShakeActivity extends Activity implements ShakeEngine.OnShakeListener {

    private ShakeEngine mShakeEngine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initShakeEngine();
    }

    private void initShakeEngine() {
        mShakeEngine = new ShakeEngine(this);
        mShakeEngine.setOnShakeListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mShakeEngine.setEnable(true);
        mShakeEngine.startSensor(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShakeEngine.setEnable(false);
        mShakeEngine.stopSensor();
    }

    @Override
    public void onShake() {

    }

}
