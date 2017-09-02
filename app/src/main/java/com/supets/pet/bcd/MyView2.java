package com.supets.pet.bcd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.supets.pet.ledview.R;

import java.util.Calendar;
import java.util.Date;

/* 自定义继承View 的MyView*/
public class MyView2 extends View {


    private int fontcolor = getContext().getResources().getColor(R.color.bcd_color);
    private int fontbackcolor = getContext().getResources().getColor(R.color.bcd_backcolor);


    public MyView2(Context context) {
        super(context);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int) (MeasureSpec.getSize(widthMeasureSpec) * 6f / 22));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       // canvas.drawColor(fontbackcolor);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);

        int P = getWidth() / 22;

        drawBCD(canvas, paint,P);
        drawampm(canvas, paint, P);
        drawdian(canvas, paint, P);
    }

    private void drawampm(Canvas canvas, Paint paint, int p) {
        paint.setTextSize(p);
        paint.setColor(fontcolor);

        if (ampm==0) {
            canvas.drawText("AM", (float) (0.5 * p), p + p / 2, paint);
        } else  if (ampm==1){
            canvas.drawText("PM", (float) (0.5 * p), p + 4 * p, paint);
        }else {
            canvas.drawText("PM", (float) (0.5 * p), p + 4 * p, paint);
            canvas.drawText("AM", (float) (0.5 * p), p + p / 2, paint);
        }
    }

    private void drawdian(Canvas canvas, Paint paint, int p) {
        if (dian) {
            paint.setColor(fontcolor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(13 * p, (float) (1.5 * p), (float) (0.5 * p), paint);
            canvas.drawCircle(13 * p, (float) (4.5 * p), (float) (0.5 * p), paint);
        }else  if (isshowKong){
            paint.setColor(fontcolor);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(13 * p, (float) (1.5 * p), (float) (0.5 * p), paint);
            canvas.drawCircle(13 * p, (float) (4.5 * p), (float) (0.5 * p), paint);
        }
    }

    private void drawBCD(Canvas canvas, Paint paint,int P) {

        Point[] xy = new Point[]{
                new Point(4 * P, (int) (0.4 * P)),
                new Point((int) (8.5 * P), (int) (0.4 * P)),
                new Point((int) (14.5 * P), (int) (0.4 * P)),
                new Point(19 * P, (int) (0.4 * P)),
        };


        for (int j = 0; j < 4; j++) {
            Point point = xy[j];
            Path[] path2 = BCD.buildBCD(getWidth(), point.x, point.y);
            for (int i = 0; i < 7; i++) {
                Path d = path2[i];
                boolean is = BCD.isBitLight(BCD.getNum(rom[j]), i);
                if (is ) {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(fontcolor);
                    canvas.drawPath(d, paint);
                } else if (isshowKong){
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(fontcolor);
                    canvas.drawPath(d, paint);

                }
            }
        }
    }


    private int[] rom = new int[]{8, 8, 8, 8};
    private boolean dian = true;
    private int ampm = 2;

    private boolean isshowKong=false;


    public void updateData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int shi = calendar.get(Calendar.HOUR);
        int miao = calendar.get(Calendar.MINUTE);

        dian = !dian;

        rom[0] = shi / 10;
        rom[1] = shi % 10;
        rom[2] = miao / 10;
        rom[3] = miao % 10;

        ampm = calendar.get(Calendar.AM_PM);

        postInvalidate();
    }


}