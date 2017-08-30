package com.supets.pet.lcd;

/**
 * LedView
 *
 * @user lihongjiang
 * @description
 * @date 2017/8/30
 * @updatetime 2017/8/30
 */

public interface ILCD {

    void closeScreen();
    void openScreen();

    void moveRam(int leftOrRight);
    void clearRam();

    void writeCGROM(int  x,int y,char str );
    void writeDDROM(int  x,int y,char str );
    void setDramLength(int length);
    void setcgramLength(int length);

}
