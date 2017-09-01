package com.supets.pet.module.ttl;

import android.os.Handler;

/**
 * java实现----------主从下降沿JK触发器
 */
public class D74107 implements Runnable {

    private int J, K;
    private int Q;
    private int CLK;//1代表高电平  0-下降沿
    private int CLR;


    //复位  输出永远为0
    public void clear() {
        CLR = 0;
        Q=0;
    }
    //保持态 Q不确定
    public void setNoChange2() {
        CLR = 1;
        CLK = 1;
    }
   //保持态，Q不确定
    public void setNoChange3() {
        CLR = 1;
        J = 1;
        K = 1;
    }

    //稳态 Q=J CLK触发不起作用--Q随J变化，频率不改变
    public void setQ() {
        CLR = 1;
        J = 1;
        K = 0;
        Q = 1;
    }

    //稳态 Q=J  CLK触发不起作用--Q随J变化，频率不改变
    public void setQ2() {
        CLR = 1;
        J = 0;
        K = 1;

        Q = 0;
    }
    //触发态 Q=J  CLK触发---分频器
    public void toggle() {
        CLR = 1;
        J = 1;
        K = 1;
        Q = (Q == 1 ? 0 : 1);
    }


    private Handler handler = new Handler();
    private D7400CallBack mCallBack;

    public D74107(D7400CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public void seCallBack(D7400CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }


    @Override
    public void run() {
        if (mCallBack != null) {
          //  mCallBack.outputY(Q);
        }
    }

    interface D7400CallBack {
        void outputY(boolean y);
    }

}
