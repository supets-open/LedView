package com.supets.pet.module.ttl;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.supets.pet.lcd.D74LS138;
import com.supets.pet.lcd.D74LS151;
import com.supets.pet.ledview.R;



public class D74LS151Activity extends Activity implements  View.OnClickListener {

    private TextView result;
    private CheckBox enable;

    private D74LS151 mSelector = new D74LS151();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_74151);

        mSelector.setInputData(0, "线路1");
        mSelector.setInputData(0, "线路2");
        mSelector.setInputData(0, "线路3");

        mSelector.setEnable(true);

        result = findViewById(R.id.result);
        enable = findViewById(R.id.enable);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enable.isChecked()) {
                    result.setText("打开输出");
                } else {
                    result.setText("关闭输出");
                }
                mSelector.setEnable(enable.isChecked());
            }
        });

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn0) {
            mSelector.setChannel(0);
            result.setText(mSelector.getResult());
        } else if (view.getId() == R.id.btn1) {
            mSelector.setChannel(1);
            result.setText(mSelector.getResult());
        } else if (view.getId() == R.id.btn2) {
            mSelector.setChannel(2);
            result.setText(mSelector.getResult());
        }
    }

}