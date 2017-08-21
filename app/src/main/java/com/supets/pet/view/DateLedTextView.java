package com.supets.pet.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateLedTextView extends LedTextView {

    public DateLedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DateLedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateLedTextView(Context context) {
        super(context);
    }

    public String changeContent() {
        Date currentTime = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter =
                new SimpleDateFormat(" yyyy年MM月dd日 EEEE ");
        this.text = formatter.format(currentTime);
        if (1 == scrollDirection) {
            text = reverseString(text);
        }
        return  text;
    }

}  