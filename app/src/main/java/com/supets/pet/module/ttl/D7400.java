package com.supets.pet.module.ttl;

import android.os.Handler;

/**
 * java实现----------与非门
 */
public class D7400 implements Runnable {

    private Handler handler = new Handler();
    private D7400CallBack mCallBack;

    public D7400(D7400CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public void seCallBack(D7400CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    private boolean pinA;
    private boolean pinB;

    private boolean pinY;

    public void setPinA(boolean pinA) {
        this.pinA = pinA;
        andnon();
    }

    public void setPinB(boolean pinB) {
        this.pinB = pinB;
        andnon();
    }

    private void andnon() {
        pinY = !(pinA & pinB);
        handler.post(this);
    }

    @Override
    public void run() {
        if (mCallBack != null) {
            mCallBack.outputY(pinY);
        }
    }

    interface D7400CallBack {
        void outputY(boolean y);
    }

}
