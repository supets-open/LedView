package com.supets.pet.module.ttl;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.supets.pet.lcd.D74LS138;
import com.supets.pet.ledview.R;

/**
 * LedView
 *
 * @user lihongjiang
 * @description
 * @date 2017/9/1
 * @updatetime 2017/9/1
 */

public class D74LS138Activity extends Activity implements D74LS138.OutputResultCallBack, View.OnClickListener {

    private TextView result;
    private CheckBox enable;

    private D74LS138 mSelector = new D74LS138();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_74138);

        mSelector.setOutPutResult(0, this);
        mSelector.setOutPutResult(1, this);
        mSelector.setOutPutResult(2, this);
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
    public void resultCallBack(int input) {
        String  data="";
        for (int i=0;i<8;i++){
            if (i==input){
                data+="1";
            }else{
                data+="0";
            }
        }
        result.setText("输出D7-D0:"+data);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn0) {
            mSelector.setInput(0);
        } else if (view.getId() == R.id.btn1) {
            mSelector.setInput(1);
        } else if (view.getId() == R.id.btn2) {
            mSelector.setInput(2);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSelector.onDestroy();
    }
}
