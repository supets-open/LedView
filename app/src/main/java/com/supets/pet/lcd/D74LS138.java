package com.supets.pet.lcd;

import java.util.ArrayList;
import java.util.List;

/**
 * java实现----------译码器功能
 */
public class D74LS138 {

    private boolean enable = false;
    private List<OutputResultCallBack> output = new ArrayList<>();

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setInput(int input) {
        if (enable) {
            if (output != null
                    && (output.size() > input)
                    && output.get(input) != null) {
                output.get(input).resultCallBack(input);
            }
        }
    }


    public void setOutPutResult(int index, OutputResultCallBack listener) {
        output.add(index, listener);
    }

    public void onDestroy() {
        output.clear();
    }

    public interface OutputResultCallBack {
        void resultCallBack(int input);
    }

}
