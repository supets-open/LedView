package com.supets.pet.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.supets.pet.ledview.R;

public class LedTextView extends TextView {

    private int xdots = 40;//X点数
    private int dots = (int) (xdots * (72f / 128)); //Y点数=行数

    private int centeroffset = (dots - 16) / 2;//Y偏移
    private int centerxoffset = (xdots % 8) / 2;//X偏移

    private float spacing = 0;//点阵之间的距离
    private float radius;//点阵中点的半径

    private Paint normalPaint;
    private Paint selectPaint;

    public String text;//显示文本


    private int paintColor = Color.GREEN;

    /*
     * 滚动的text
     */
    private volatile boolean scrollText = false;
    /*
     * 用来调整滚动速度
     */
    private static int maxsleep = 100;
    private static int minsleep = 50;

    private int sleepTime = minsleep;

    /*
     * 滚动方向，默认0向左
     */
    public int scrollDirection = 0;

    /*
* 汉字对应的点阵矩阵
*/
    private boolean[][] matrix;
    private Handler handler;
    private boolean isCircle = true;


    public LedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        boolean scroll = false;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LedTextView);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.LedTextView_textColor:
                    paintColor = typedArray.getColor(R.styleable.LedTextView_textColor, Color.GREEN);
                    break;
                case R.styleable.LedTextView_spacing:
                    spacing = typedArray.getDimension(R.styleable.LedTextView_spacing, 10);
                    break;
                case R.styleable.LedTextView_scroll:
                    scroll = typedArray.getBoolean(R.styleable.LedTextView_scroll, true);
                    break;
                case R.styleable.LedTextView_speed:
                    int speed = typedArray.getInt(R.styleable.LedTextView_speed, 0);
                    if (0 == speed) {
                        sleepTime = minsleep;
                    } else {
                        sleepTime = maxsleep;
                    }
                    break;
                case R.styleable.LedTextView_scrollDirection:
                    scrollDirection = typedArray.getInt(R.styleable.LedTextView_scrollDirection, 0);
                    break;
            }
        }
        typedArray.recycle();
        selectPaint = new Paint();
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setColor(paintColor);

        normalPaint = new Paint();
        normalPaint.setStyle(Paint.Style.STROKE);
        normalPaint.setColor(Color.BLACK);

        text = getText().toString();
        if (TextUtils.isEmpty(text)) {
            text = "Welcome To You！";
        }
        if (1 == scrollDirection) {
            text = reverseString(text);
        }

        matrix = ChatUtils.convert(text, getContext());

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                postInvalidate();
                return false;
            }
        });

        if (scroll){
            startScroll();
        }

    }

    public void startScroll() {
        scrollText = true;
        Thread thread = new ScrollThread();
        thread.start();
    }


    public void stopScroll() {
        scrollText = false;
    }


    public LedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LedTextView(Context context) {
        this(context, null, 0);
    }

    /**
     * 翻转字符串，当设置享有滚动时调用
     *
     * @param str
     * @return
     */
    public String reverseString(String str) {
        StringBuffer sb = new StringBuffer(str);
        sb = sb.reverse();
        return sb.toString();
    }

    /* 
     * 用于控制滚动，仅在开启滚动时启动。 
     */
    private class ScrollThread extends Thread {
        @Override
        public void run() {

            int m = 0;
            while (scrollText) {

                if (m == matrix[0].length) {
                    m = 0;
                    long s = System.currentTimeMillis();
                    matrix = ChatUtils.convert(changeContent(), getContext());
                    System.out.println("time:" + (System.currentTimeMillis() - s));
                }
                System.out.println("time:" + (m));
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (0 == scrollDirection) {
                    matrixLeftMove(matrix);
                } else {
                    matrixRightMove(matrix);
                }
                m++;

                handler.sendEmptyMessage(0);
            }
            matrix = ChatUtils.convert(changeContent(), getContext());
            handler.sendEmptyMessage(0);
        }
    }


    /**
     * 向左滚动时调用，列循环左移
     *
     * @param matrix
     */
    private void matrixLeftMove(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            boolean tmp = matrix[i][0];
            System.arraycopy(matrix[i], 1, matrix[i], 0, matrix[0].length - 1);
            matrix[i][matrix[0].length - 1] = tmp;
        }
    }

    /**
     * 向右滚动时调用，列循环右移
     *
     * @param matrix
     */
    private void matrixRightMove(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            boolean tmp = matrix[i][matrix[0].length - 1];
            System.arraycopy(matrix[i], 0, matrix[i], 1, matrix[0].length - 1);
            matrix[i][0] = tmp;
        }
    }

    /**
     * 主要是想处理AT_MOST的情况，我觉得View默认的情况就挺好的，由于继承自TextView，而TextView重
     * 写了onMeasure，因此这里参考View#onMeasure函数的写法即可
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }


    private void drawText(Canvas canvas, int xoffset, int yoffset, boolean[][] matrix) {
        radius = (getHeight() - (dots + 1) * spacing) / (2 * dots);
        // 行  
        int row = 0;
        // 列  
        int column = 0;
        while (getYPosition(row, yoffset) < getHeight()) {
            while (getXPosition(column, xoffset) < getWidth()) {
                // just draw  
                if (row < matrix.length && column < matrix[0].length && matrix[row][column]) {
                    if (isCircle) {
                        canvas.drawCircle(getXPosition(column, xoffset), getYPosition(row, yoffset), radius, selectPaint);
                    } else {
                        canvas.drawRect(getXPosition(column, xoffset), getYPosition(row, yoffset), getXPosition(column, xoffset) + 2 * radius, getYPosition(row, yoffset) + 2 * radius, selectPaint);
                    }
                }
//                else {
//                    if (isCircle) {
//                        canvas.drawCircle(getXPosition(column, xoffset), getYPosition(row, yoffset), radius, normalPaint);
//                    } else {
//                        canvas.drawRect(getXPosition(column, xoffset), getYPosition(row, yoffset), getXPosition(column, xoffset) + 2 * radius, getYPosition(row, yoffset) + 2 * radius, normalPaint);
//                    }
//                }
                column++;
            }
            row++;
            column = 0;
        }
    }

    /**
     * 获取绘制第column列的点的X坐标
     *
     * @param column
     * @return
     */
    private float getXPosition(int column, int xoffset) {
        if (isCircle) {
            return spacing + radius + (spacing + 2 * radius) * (column + xoffset);
        } else {
            return spacing + (spacing + 2 * radius) * (column + xoffset);
        }
    }

    /**
     * 获取绘制第row行的点的Y坐标
     *
     * @param row
     * @return
     */
    private float getYPosition(int row, int yoffset) {
        if (isCircle) {
            return spacing + radius + (spacing + 2 * radius) * (row + yoffset);
        } else {
            return spacing + (spacing + 2 * radius) * (row + yoffset);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stopScroll();
        handler.removeMessages(0);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawText(canvas, centerxoffset, centeroffset, matrix);
    }

    //测试画点阵
    private void drawBackGround(Canvas canvas) {
        radius = (getHeight() - (dots + 1) * spacing) / (2 * dots);
        // 行
        int row = 0;
        // 列
        int column = 0;
        while (getYPosition(row, 0) < getHeight()) {
            while (getXPosition(column, 0) < getWidth()) {
                // just draw
                if (isCircle) {
                    canvas.drawCircle(getXPosition(column, 0), getYPosition(row, 0), radius, normalPaint);
                } else {
                    canvas.drawRect(getXPosition(column, 0), getYPosition(row, 0), getXPosition(column, 0) + 2 * radius, getYPosition(row, 0) + 2 * radius, normalPaint);
                }
                column++;
            }
            row++;
            column = 0;
        }
    }

    public void updateText(String text) {
        this.text = text;
        if (1 == scrollDirection) {
            this.text = reverseString(text);
        }
        if (!scrollText) {
            matrix = ChatUtils.convert(this.text, getContext());
            postInvalidate();
        }
    }

    public String changeContent() {
        return text;
    }

}  