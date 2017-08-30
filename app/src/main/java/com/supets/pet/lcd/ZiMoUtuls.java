package com.supets.pet.lcd;

import android.content.Context;

import com.supets.pet.view.ChatUtils;

import java.util.ArrayList;

/**
 * LedView
 *
 * @user lihongjiang
 * @description
 * @date 2017/8/30
 * @updatetime 2017/8/30
 */

public class ZiMoUtuls {

    public static RGB[][] getDisplayCache(ArrayList<byte[]> list, int SCALE) {

        int totalDots = 0;//每行点数=字宽之和
        for (byte[] temp : list) {
            totalDots += (temp.length/2);
        }

        RGB[][] matrix = new RGB[16 * SCALE][totalDots * SCALE];

        for (int num = 0; num < list.size(); num++) {//显示字数
            byte[] word = list.get(num);
            for (int i = 0; i < 16; i++) {//字符高度16
                int wordWidth = word.length / 16;
                for (int j1 = 0; j1 < wordWidth; j1++) {//一个字节行循环
                    byte tmp = word[i * wordWidth + j1];
                    for (int j2 = 0; j2 < 8; j2++) {

                        //起始点X 坐标
                        int length = 0;
                        for (int x = 0; x < num; x++) {
                            length += (list.get(x).length / 16) * 8;
                        }

                        if (((tmp >> (7 - j2)) & 1) == 1) {
                            for (int m = 0; m < SCALE; m++) {
                                for (int n = 0; n < SCALE; n++) {
                                    matrix[i * SCALE + m][(length+ j1 * 8 + j2) * SCALE + n] =new RGB(0,true);
                                }
                            }
                        } else {
                            for (int m = 0; m < SCALE; m++) {
                                for (int n = 0; n < SCALE; n++) {
                                    matrix[i * SCALE + m][(length + j1 * 8 + j2) * SCALE + n] =new RGB(0,false);
                                }
                            }
                        }
                    }
                }
            }
        }

        return matrix;

    }


    public static RGB[][] convert(String auto, Context context){
        return  getDisplayCache(ChatUtils.auto(context,auto),1);
    }

}
