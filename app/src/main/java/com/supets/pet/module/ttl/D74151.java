package com.supets.pet.module.ttl;

import java.util.ArrayList;
import java.util.List;

/**
 * java实现----------数据选择器功能
 */
public class D74151 {

    private boolean enable = false;
    private int channel = 0;
    private List<String> output = new ArrayList<>();

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getResult() {
        if (enable) {
            if (output != null && (output.size() > channel)) {
                return output.get(channel);
            }
        }
        return null;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setInputData(int index, String data) {
        output.add(index, data);
    }

}
