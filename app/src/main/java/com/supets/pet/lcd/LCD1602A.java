package com.supets.pet.lcd;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.supets.pet.ledview.R;

public class LCD1602A extends android.support.v7.widget.AppCompatTextView {


    private static  final int HANG=2;
    private static final int FONT_WIDTH=8;
    private static final int FONT_HEIGHT=16;

    private int dots = FONT_HEIGHT*HANG; //行数
    private float spacing = 0;//点阵之间的距离
    private float radius;//点阵中点的半径

    private Paint normalPaint;
    private Paint selectPaint;

    public String text;//显示文本

    private int paintColor = Color.GREEN;

    private ZiMo[][] ddram = new ZiMo[2][16];

    public LCD1602A(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
            }
        }
        typedArray.recycle();
        selectPaint = new Paint();
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setColor(paintColor);

        normalPaint = new Paint();
        normalPaint.setStyle(Paint.Style.STROKE);
        normalPaint.setColor(Color.BLACK);
    }


    public LCD1602A(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LCD1602A(Context context) {
        this(context, null, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) (height*(32f/128)), height);
    }

    private void drawText(Canvas canvas, int xoffset, int yoffset, RGB[][] matrix) {
        radius = (getHeight() - (dots + 1) * spacing) / (2 * dots);
        // 行  
        int row = 0;
        // 列  
        int column = 0;
        while (getYPosition(row, yoffset) < getHeight()) {
            while (getXPosition(column, xoffset) < getWidth()) {
                // just draw  
                if (row < matrix.length && column < matrix[0].length && matrix[row][column].light) {
                    canvas.drawCircle(getXPosition(column, xoffset), getYPosition(row, yoffset), radius, selectPaint);
                }
                column++;
            }
            row++;
            column = 0;
        }
    }

    private float getXPosition(int column, int xoffset) {
        return spacing + radius + (spacing + 2 * radius) * (column + xoffset);
    }

    private float getYPosition(int row, int yoffset) {
        return spacing + radius + (spacing + 2 * radius) * (row + yoffset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < ddram.length; i++) {
            for (int j = 0; j < ddram[0].length; j++) {
                ZiMo ziMo = ddram[i][j];
                if (ziMo != null && ziMo.pdata != null) {
                    drawText(canvas, j * FONT_WIDTH, FONT_HEIGHT * i, ziMo.pdata);
                }
            }
        }
    }


    /**
     * 穿入传入单个字符
     *
     * @param line
     * @param column
     * @param str
     */
    public void setRam(int line, int column, String str) {

        if (line > 2 || column > 16 || column < 1 || line < 1) {
            return;
        }
        ddram[line - 1][column - 1] = new ZiMo(ZiMoUtuls.convert(str, getContext()));

    }

    public void refreshView() {
        postInvalidate();
    }

}