package com.supets.pet.module.ttl;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.supets.pet.ledview.R;


public class D7400Activity extends Activity implements D7400.D7400CallBack, View.OnClickListener {

    private TextView result;
    private D7400 mSelector = new D7400(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7400);

        result = findViewById(R.id.result);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn0) {
            mSelector.setPinA(true);
        } else if (view.getId() == R.id.btn1) {
            mSelector.setPinA(false);
        } else if (view.getId() == R.id.btn2) {
            mSelector.setPinB(true);
        } else if (view.getId() == R.id.btn3) {
            mSelector.setPinB(false);
        }
    }


    @Override
    public void outputY(boolean y) {
        result.setText("A=" + (mSelector.getPinA() ? 1 : 0)
                + " B=" + (mSelector.getPinB() ? 1 : 0)
                + " Y=" + (y ? 1 : 0));
    }
}
