package com.supets.pet.module.led;

import android.app.Activity;
import android.os.Bundle;

import com.supets.pet.ledview.R;
import com.supets.pet.view.DateLedTextView;
import com.supets.pet.view.WakeScreenUtils;

public class DateTimeActivity extends Activity {


    private DateLedTextView date_led;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WakeScreenUtils.keepScreen(this);
        setContentView(R.layout.activity_datetime);
        date_led = findViewById(R.id.date_led);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        date_led.stopScroll();
    }

}
