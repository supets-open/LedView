package com.supets.pet.bcd;

import android.graphics.Path;

/**
 * LedView
 *
 * @user lihongjiang
 * @description
 * @date 2017/9/2
 * @updatetime 2017/9/2
 */

public class BCD {

    public static final byte[] bcd = new byte[]{
            0X3F, 0X06, 0X5B, 0X4F, 0X66,
            0X6D, 0X7D, 0X07, 0X7F, 0X6F
    };


    public static boolean isBitLight(byte data, int bit) {
        return ((data >> bit) & 1) == 1;
    }

    public static byte getNum(int num) {
        return bcd[num];
    }

    public static byte am(byte data, boolean am) {
        return (byte) (data | (am ? 0x80 : 0x00));
    }

    public static byte dian(byte data, boolean dian) {
        return (byte) (data | (dian ? 0x80 : 0x00));
    }


    //宽22个单位  高6个单位  以宽为基准
    //字宽W+2H  字高3h+2W  W=4H 关系。
    //字宽本来占3个单位==》H=1/44W0单位 W=1/11W0
    public static Path[] buildBCD(int width, int x0, int y0) {

        Path[] paths = new Path[7];

        int w = width / 11;
        int h = width / 44;

        paths[0] = getHBCD(w, h, x0 + h, y0 + h / 2);//A
        paths[3] = getHBCD(w, h, x0 + h, y0 +w+h+w+h+ h / 2);//D
        paths[6] = getHBCD(w, h, x0 + h, y0 + h / 2 + w + h);//G

        paths[1] = getLBCD(w, h, x0 + h + w + h / 2, y0 + h);//B
        paths[2] = getLBCD(w, h, x0 + h + w + h / 2, y0 + w + h + h);//C
        paths[4] = getLBCD(w, h, x0 + h / 2, y0 + w + h + h);//E
        paths[5] = getLBCD(w, h, x0 + h /2, y0 + h);//F


        return paths;

    }


    private static Path getHBCD(int w, int h, int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x + h / 2, y + h / 2);
        path.lineTo(x + w - h / 2, y + h / 2);
        path.lineTo(x + w, y);
        path.lineTo(x + w - h / 2, y - h / 2);
        path.lineTo(x + h / 2, y - h / 2);
        path.close();
        return path;
    }

    private static Path getLBCD(int w, int h, int x, int y) {
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x - h / 2, y + h / 2);
        path.lineTo(x - h / 2, y + w - h / 2);
        path.lineTo(x, y + w);
        path.lineTo(x + h / 2, y + w - h / 2);
        path.lineTo(x + h / 2, y + h / 2);
        path.close();
        return path;
    }

}
