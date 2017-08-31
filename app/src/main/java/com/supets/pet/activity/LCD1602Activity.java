package com.supets.pet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.supets.pet.lcd.LCD1602A;
import com.supets.pet.ledview.R;
import com.supets.pet.view.LedTextView;
import com.supets.pet.view.WakeScreenUtils;

import java.text.SimpleDateFormat;
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
        
        lcd1602A.setRam(1, 1, "l");
        lcd1602A.setRam(1, 2, "o");
        lcd1602A.setRam(1, 3, "a");
        lcd1602A.setRam(1, 4, "d");
        lcd1602A.setRam(1, 5, "i");
        lcd1602A.setRam(1, 5, "n");
        lcd1602A.setRam(1, 5, "g");

        lcd1602A.refreshView();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                isShan = !isShan;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int shi = calendar.get(Calendar.HOUR);
                int miao = calendar.get(Calendar.MILLISECOND);

                lcd1602A.setRam(1, 1, shi / 10 + "");
                lcd1602A.setRam(1, 2, shi % 10 + "");
                lcd1602A.setRam(1, 3, isShan ? ":" : "");
                lcd1602A.setRam(1, 4, miao / 10 + "");
                lcd1602A.setRam(1, 5, miao % 10 + "");

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
