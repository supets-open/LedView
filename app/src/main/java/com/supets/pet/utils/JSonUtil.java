//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.supets.pet.utils;

import android.text.TextUtils;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JSonUtil {
    public JSonUtil() {
    }

    public static Gson getGsonExcludeFields(ExclusionStrategy exclusion) {
        GsonBuilder builder = (new GsonBuilder()).excludeFieldsWithModifiers(new int[]{138});
        return exclusion == null?builder.create():builder.setExclusionStrategies(new ExclusionStrategy[]{exclusion}).create();
    }

    public static String toJson(Object src) {
        Gson gson = getGsonExcludeFields((ExclusionStrategy)null);
        return gson.toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if(TextUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return getGsonExcludeFields((ExclusionStrategy)null).fromJson(json, clazz);
            } catch (Exception var3) {
                var3.printStackTrace();
                return fromJson2(json, clazz);
            }
        }
    }

    private static <T> T fromJson2(String json, Class<T> clazz) {
        if(TextUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                String e = json.replaceAll("((?<=\\{)\"\\w+\":\"\",|,*\"\\w+\":\"\"|(?<=\\{)\"\\w+\":\\[\\],|,*\"\\w+\":\\[\\])", "");
                return getGsonExcludeFields((ExclusionStrategy)null).fromJson(e, clazz);
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static <T> T fromJson(String json, Type clazz) {
        if(TextUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return getGsonExcludeFields((ExclusionStrategy)null).fromJson(json, clazz);
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public static String createJson(Map<String, ?> map) {
        return toJson(map);
    }

    public static String createArrayJson(ArrayList<?> array) {
        return toJson(array);
    }

    public static String createArrayJson(String key, ArrayList<?> array) {
        Gson gson = getGsonExcludeFields((ExclusionStrategy)null);
        HashMap map = new HashMap();
        map.put(key, array);
        String text = gson.toJson(map);
        return text;
    }

    public static String filterJson(String json) {
        return json.replaceAll("((?<=\\{)\"\\w+\":\"\",|,*\"\\w+\":\"\"|(?<=\\{)\"\\w+\":\\[\\],|,*\"\\w+\":\\[\\])", "");
    }
}
