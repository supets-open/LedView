package com.supets.pet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.supets.pet.ledview.R;
import com.supets.pet.view.DateLedTextView;
import com.supets.pet.view.LedTextView;
import com.supets.pet.view.WakeScreenUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeActivity extends Activity {


    private DateLedTextView date_led;
    private LedTextView time_led;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WakeScreenUtils.keepScreen(this);
        setContentView(R.layout.activity_datetime);

        date_led = findViewById(R.id.date_led);
        time_led = findViewById(R.id.time_led);
        time_led.updateText("88:88");

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                time_led.updateText(getStringDate());
                handler.sendEmptyMessageDelayed(0, 1000);
                return false;
            }
        });

        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        date_led.stopScroll();
        handler.removeMessages(0);
    }

    private boolean isShan = false;

    public String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(isShan ? "HH mm" : "HH:mm");
        isShan = !isShan;
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}
