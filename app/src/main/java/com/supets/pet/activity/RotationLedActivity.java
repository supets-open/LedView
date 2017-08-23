package com.supets.pet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.supets.pet.ledview.R;
import com.supets.pet.sensor.ledPref;
import com.supets.pet.view.ChatUtils;
import com.supets.pet.view.LedTextView;
import com.supets.pet.view.WakeScreenUtils;

public class RotationLedActivity extends Activity {
    private LedTextView time_led;

    private Handler handler;

    private boolean[][] data;

    private int m = 0;

    public static final int MAX_DOTS = 128;//0.05-0.2
    private int delay = 10;//12个汉字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WakeScreenUtils.keepScreen(this);
        setContentView(R.layout.activity_rotationled);

        delay = ledPref.getTime() / 160;
        delay = delay < 10 ? 10 : delay;

        data = ChatUtils.convert("李洪江", this);
        time_led = findViewById(R.id.time_led);
        time_led.ForceupdateText(getLineData(m));

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                time_led.ForceupdateText(getLineData(m));
                return false;
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (runflag) {
                    m++;
                    if (m >= MAX_DOTS) {
                        m = 0;
                    }
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
                handler.removeMessages(0);
            }
        }).start();

    }


    private boolean runflag = true;

    private boolean[][] getLineData(int m) {

        boolean[][] temp = new boolean[16][1];
        for (int i = 0; i < 16; i++) {
            if (m > data[0].length - 1) {
                temp[i][0] = false;
            } else {
                temp[i][0] = data[i][m];
            }
        }


        return temp;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runflag = false;
        handler.removeMessages(0);
    }



}
