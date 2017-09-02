package com.supets.pet.bcd;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.supets.pet.ledview.R;

public class GraphicesActivity extends Activity {

    MyView2  lcd1602A;
    Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcd);

        lcd1602A = findViewById(R.id.lcd1602);


         handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                lcd1602A.updateData();
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