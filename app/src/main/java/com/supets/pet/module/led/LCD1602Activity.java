package com.supets.pet.module.led;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.supets.pet.lcd.LCD1602A;
import com.supets.pet.ledview.R;
import com.supets.pet.view.WakeScreenUtils;

import java.util.Calendar;
import java.util.Date;

public class LCD1602Activity extends Activity {

    private boolean isShan = false;

    private LCD1602A lcd1602A;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WakeScreenUtils.keepScreen(this);
        setContentView(R.layout.activity_lcd1602);

        lcd1602A = findViewById(R.id.lcd1602);


        lcd1602A.setRam(1, 5, "L");
        lcd1602A.setRam(1, 6, "C");
        lcd1602A.setRam(1, 7, "D");
        lcd1602A.setRam(1, 8, "1");
        lcd1602A.setRam(1, 9, "6");
        lcd1602A.setRam(1, 10, "0");
        lcd1602A.setRam(1, 11, "2");

        lcd1602A.setRam(2, 3, "l");
        lcd1602A.setRam(2, 4, "o");
        lcd1602A.setRam(2, 5, "a");
        lcd1602A.setRam(2, 6, "d");
        lcd1602A.setRam(2, 7, "i");
        lcd1602A.setRam(2, 8, "n");
        lcd1602A.setRam(2, 9, "g");
        lcd1602A.setRam(2, 10, ".");
        lcd1602A.setRam(2, 11, ".");
        lcd1602A.setRam(2, 12, ".");

        lcd1602A.refreshView();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                isShan = !isShan;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int shi = calendar.get(Calendar.HOUR);
                int miao = calendar.get(Calendar.SECOND);

                lcd1602A.setRam(2, 3, "T");
                lcd1602A.setRam(2, 4, "I");
                lcd1602A.setRam(2, 5, "M");
                lcd1602A.setRam(2, 6, "E");
                lcd1602A.setRam(2, 7, " ");

                lcd1602A.setRam(2, 8, shi / 10 + "");
                lcd1602A.setRam(2, 9, shi % 10 + "");
                lcd1602A.setRam(2, 10, isShan ? ":" : "");
                lcd1602A.setRam(2, 11, miao / 10 + "");
                lcd1602A.setRam(2, 12, miao % 10 + "");

                lcd1602A.refreshView();

                handler.sendEmptyMessageDelayed(0, 1000);
                return false;
            }
        });
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }

}
