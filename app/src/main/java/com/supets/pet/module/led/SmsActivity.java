package com.supets.pet.module.led;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.supets.pet.ledview.R;
import com.supets.pet.module.sensor.BaseSmsActivity;
import com.supets.pet.view.DateLedTextView;
import com.supets.pet.view.LedTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SmsActivity extends BaseSmsActivity {


    private DateLedTextView date_led;
    private LedTextView scroll_led;
    private LedTextView scroll_led1;
    private LedTextView time_led;

    private Handler handler;

    private EditText phone;
    private EditText msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        date_led = findViewById(R.id.date_led);
        scroll_led = findViewById(R.id.scroll_led);
        scroll_led1 = findViewById(R.id.scroll_led1);

        msg(getIntent());

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

        phone = findViewById(R.id.phone);
        msg = findViewById(R.id.msg);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                sendSms(phone.getText().toString(), msg.getText().toString());
                view.setEnabled(true);
            }
        });

    }

    private void msg(Intent intent) {
        String message = intent.getStringExtra("message");
        String phone = intent.getStringExtra("phone");
        if (message != null) {
            scroll_led1.updateText("from:" + phone);
            scroll_led.ForceupdateText(message);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        msg(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        date_led.stopScroll();
        scroll_led.stopScroll();
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

    public String getAPM() {
        long time = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int apm = mCalendar.get(Calendar.AM_PM);
        return apm == 0 ? "AM" : "PM";
    }

    public String getMS() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("SSS");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
