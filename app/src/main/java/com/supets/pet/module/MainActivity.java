package com.supets.pet.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.supets.pet.ledview.R;
import com.supets.pet.module.led.BusActivity;
import com.supets.pet.module.led.DateTimeActivity;
import com.supets.pet.module.led.LCD1602Activity;
import com.supets.pet.module.led.MainAdapter;
import com.supets.pet.module.led.SmsActivity;
import com.supets.pet.module.led.TimeActivity;
import com.supets.pet.module.led.WeatherInfoActivity;
import com.supets.pet.module.sensor.BaseOrientationActivity;
import com.supets.pet.module.sensor.CompassActivity;
import com.supets.pet.module.sensor.LevelVialActivity;
import com.supets.pet.module.sensor.LightActivity;
import com.supets.pet.module.sensor.RotationLedActivity;

import java.util.ArrayList;


public class MainActivity extends BaseOrientationActivity {

    private MainAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        adapter = new MainAdapter();
        ArrayList<String> d = new ArrayList<>();
        d.add("时间");
        d.add("日期");
        d.add("短消息");

        d.add("北京天气");
        d.add("方向传感器-指南针");
        d.add("方向传感器-水平仪");

        d.add("光线传感器-旋转LED");
        d.add("光线/距离传感器-测量时间");

        d.add("428公交线路-天通北苑");
        d.add("428公交线路-龙泽地铁");

        d.add("LCD1602A");

        adapter.setData(d);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                doWithButton(position);
            }
        });


    }

    private void doWithButton(int position) {
        switch (position + 1) {
            case 1:
                startActivity(new Intent(MainActivity.this, TimeActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, DateTimeActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, SmsActivity.class));
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, WeatherInfoActivity.class));
                break;
            case 5:
                startActivity(new Intent(MainActivity.this, CompassActivity.class));
                break;
            case 6:
                startActivity(new Intent(MainActivity.this, LevelVialActivity.class));
                break;
            case 7:
                startActivity(new Intent(MainActivity.this, RotationLedActivity.class));
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, LightActivity.class));
                break;
            case 9: {
                Intent intent = new Intent(MainActivity.this, BusActivity.class);
                intent.putExtra("status", true);
                startActivity(intent);
            }
            break;
            case 10: {
                Intent intent = new Intent(MainActivity.this, BusActivity.class);
                intent.putExtra("status", false);
                startActivity(intent);
            }
            break;
            case 11: {
                Intent intent = new Intent(MainActivity.this, LCD1602Activity.class);
                startActivity(intent);
            }
            break;
            default:
                break;
        }
    }


}
