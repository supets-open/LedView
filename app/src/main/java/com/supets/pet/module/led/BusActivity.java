package com.supets.pet.module.led;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.supets.pet.bus.BusInfo;
import com.supets.pet.bus.BusUtils;
import com.supets.pet.ledview.R;
import com.supets.pet.view.LedTextView;

import java.util.List;

public class BusActivity extends Activity {


    LedTextView time_led;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        boolean isDitie = getIntent().getBooleanExtra("status", true);

        String url = isDitie ?
                "http://map.baidu.com/mobile/webapp/search/search/qt=inf&uid=229abe20df4728442ad7ab79/?third_party=webapp-aladdin" :
                "http://map.baidu.com/mobile/webapp/search/search/qt=inf&uid=bbca8d0458ee9582601c67b7/?third_party=webapp-aladdin";
        time_led = findViewById(R.id.time_led);
        time_led.startScroll();
        requestData(url);
    }

    public void requestData(String url) {
        new AsyncTask<String, Void, List<BusInfo>>() {

            @Override
            protected List<BusInfo> doInBackground(String... params) {
                return BusUtils.getListPic(params[0]);
            }

            @Override
            protected void onPostExecute(List<BusInfo> result) {
                StringBuilder sb = new StringBuilder();
                sb.append(" 车已经到达:");
                for (BusInfo info : result) {
                    if (info.isStop) {
                        sb.append(" " + info.getLineNumber() + " " + info.getName());
                    }
                }
                time_led.ForceupdateText(sb.toString());
            }
        }.execute(url);
    }
}
