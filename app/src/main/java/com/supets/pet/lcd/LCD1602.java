package com.supets.pet.lcd;


/**
 * LCD1602 字符型显示器（
 *
 * @user lihongjiang
 * @description 2X16个字符，不能显示汉字
 * @date 2017/8/30
 * @updatetime 2017/8/30
 */
public class LCD1602 implements ILCD {

    private static final int DDRAM_HANG_MAX_BYTE = 40;
    private static final int DDRAM_HANG_NUM = 2;
    private static final int DDRAM_HANG_BYTE = 16;
    private static final int CGRAM_LENGTH = 8;//自定义存储RAM

    private int cursor_postion = 0;//光标位置

    private ZiMo[][] ddram = new ZiMo[DDRAM_HANG_NUM][DDRAM_HANG_MAX_BYTE];

    private static RGB[][] display = new RGB[DDRAM_HANG_NUM][DDRAM_HANG_MAX_BYTE];
    private static char[] cgram = new char[CGRAM_LENGTH];


    @Override
    public void closeScreen() {

    }

    @Override
    public void openScreen() {

    }

    @Override
    public void moveRam(int leftOrRight) {

    }

    @Override
    public void clearRam() {

    }

    @Override
    public void writeCGROM(int x, int y, char str) {

    }

    @Override
    public void writeDDROM(int x, int y, char str) {

    }

    @Override
    public void setDramLength(int length) {

    }

    @Override
    public void setcgramLength(int length) {

    }
}
