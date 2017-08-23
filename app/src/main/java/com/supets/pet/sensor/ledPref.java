package com.supets.pet.sensor;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ledPref extends BasePref {
	
	private static final String Name="weibo_config";

	private static final String LED_XUANZHUAN="LED_XUANZHUAN";

	public static int getTime() {
		SharedPreferences preferences = getPref(Name);
		return  preferences.getInt(LED_XUANZHUAN, 10);
	}
	
	public static void saveTime(int time) {
		Editor editor = edit(Name);
		editor = editor.putInt(LED_XUANZHUAN, time);
		editor.commit();
	}

	public static void clear() {
		BasePref.clear(Name);
	}

}
