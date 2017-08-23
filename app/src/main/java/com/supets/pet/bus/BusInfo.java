package com.supets.pet.bus;

import java.security.PublicKey;

/**
 * LedView
 *
 * @user lihongjiang
 * @description
 * @date 2017/8/23
 * @updatetime 2017/8/23
 */

public class BusInfo {

    public String lineNumber;
    public String name;
    public boolean isStop;

    public BusInfo() {
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public BusInfo(String lineNumber, String name, boolean isStop) {
        this.lineNumber = lineNumber;
        this.name = name;
        this.isStop = isStop;
    }
}
