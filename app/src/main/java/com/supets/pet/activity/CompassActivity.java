package com.supets.pet.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.supets.pet.ledview.R;

public class CompassActivity extends BaseOrientationActivity {

    private ImageView mImageView;
    private int predegree = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        mImageView = findViewById(R.id.imageView);
        mImageView.setKeepScreenOn(true);//屏幕高亮
    }

    @Override
    protected void onOrientationDegree(int degreeX, int degreeY, int degreeZ) {

        RotateAnimation animation = new RotateAnimation(predegree, degreeX,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        mImageView.startAnimation(animation);
        predegree = -degreeX;
        Log.v("角度X:", "" + degreeX);
    }
}
