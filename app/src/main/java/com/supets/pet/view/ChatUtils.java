package com.supets.pet.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ChatUtils {

    public static void main(String[] args) {
        String[] strArr = new String[]{
                "www.micmiu.com",
                "!@#$%^&*()_+{}[]|\"'?/:;<>,.",
                "！￥……（）——：；“”‘’《》，。？、",
                "不要啊",
                "やめて",
                "韩佳人",
                "???"};
        for (String str : strArr) {
            char[] ch = str.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                char c = ch[i];
                System.out.println("ASCII判断结果 ：" + (isAscii(c) ? "是" : "否"));
                System.out.println("Unicode判断结果 ：" + (isChinese(c) ? "是" : "否"));
            }
        }
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 根据Unicode编码完美的判断英文和ASCII
    private static boolean isAscii(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.BASIC_LATIN
                || ub == Character.UnicodeBlock.LATIN_1_SUPPLEMENT) {
            return true;
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean isChinesePuctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {//jdk1.7
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static boolean isChineseByScript(char c) {
        Character.UnicodeScript sc = Character.UnicodeScript.of(c);
        if (sc == Character.UnicodeScript.HAN) {
            return true;
        }
        return false;
    }

    public static ArrayList<byte[]> auto(Context context, String input) {

        ArrayList<byte[]> sparseArray = new ArrayList<>();

        char[] ch = input.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            //            System.out.println("ASCII判断结果 ：" + (isAscii(c) ? "是" : "否"));
            //            System.out.println("Unicode判断结果 ：" + (isChinese(c) ? "是" : "否"));
            if (isAscii(c)) {
                byte[] temp = readAscii(context, c);
                if (temp != null) {
                    sparseArray.add(temp);
                }
            }
            if (isChinese(c)) {

                try {
                    byte[] bytes = String.valueOf(c).getBytes("GB2312");
                    int areacode = bytes[0] < 0 ? 256 + bytes[0] : bytes[0];//区码
                    int poscode = bytes[1] < 0 ? 256 + bytes[1] : bytes[1];//位码
                    byte[] temp = readHan(context, areacode, poscode);
                    if (temp != null) {
                        sparseArray.add(temp);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

//                byte[] bytes = charToByte(c);

            }
        }
        return sparseArray;
    }

    private static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    private static byte[] readAscii(Context context, char ascii) {
        byte[] data = null;
        try {
            InputStream in = context.getResources().getAssets().open("ASC16");
            int offset = ascii * 16;
            in.skip(offset);
            data = new byte[16];
            in.read(data);
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    private static byte[] readHan(Context context, int areaCode, int posCode) {
        byte[] data = null;
        try {
            int area = areaCode - 0xa0;
            int pos = posCode - 0xa0;
            InputStream in = context.getResources().getAssets().open("HZK16");
            long offset = 32 * ((area - 1) * 94 + pos - 1);
            in.skip(offset);
            data = new byte[32];
            in.read(data, 0, 32);
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public static boolean[][] getDisplayCache(ArrayList<byte[]> list, int SCALE) {

        int totalDots = 0;//每行点数=字宽之和
        for (byte[] temp : list) {
            totalDots += (temp.length/2);
        }

        boolean[][] matrix = new boolean[16 * SCALE][totalDots * SCALE];

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
                                    matrix[i * SCALE + m][(length+ j1 * 8 + j2) * SCALE + n] = true;
                                }
                            }
                        } else {
                            for (int m = 0; m < SCALE; m++) {
                                for (int n = 0; n < SCALE; n++) {
                                    matrix[i * SCALE + m][(length + j1 * 8 + j2) * SCALE + n] = false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return matrix;

    }

    public static boolean[][]  convert(String auto,Context context){
        return  getDisplayCache(auto(context,auto),1);
    }
}
