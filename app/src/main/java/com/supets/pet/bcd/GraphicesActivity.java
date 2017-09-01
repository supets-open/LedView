package com.supets.pet.bcd;

import android.app.Activity;
import android.os.Bundle;

public class GraphicesActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(myView);
    }

}