package com.supets.pet.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.supets.pet.ledview.R;

public class ShakeEngine implements SensorEventListener {

	// 检测的时间间隔
	private static final int UPDATE_INTERVAL = 100;

	// 摇晃检测阈值，决定了对摇晃的敏感程度，越小越敏感
	private static final int SHAKE_THRESHOLD = 1500;

	private OnShakeListener listener;
	private SensorManager mSensorManager;
	private long mLastUpdateTime;
	private float mLastX;
	private float mLastY;
	private float mLastZ;
	private boolean mEnable;

	//private Vibrator mVibrator;

	private Context  mContext;

	public ShakeEngine(Context context) {
		mContext=context;
	}

	public void setOnShakeListener(OnShakeListener listener) {
		this.listener = listener;
	}
	
	public boolean isEnable() {
		return mEnable;
	}

	public void setEnable(boolean enable) {
		mEnable = enable;
	}

	public void startSensor(Context context) {
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		if (mSensorManager == null) {
			showUnsupportedTip();
			return;
		}
		Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if (sensor == null) {
			showUnsupportedTip();
			return;
		}
		boolean success = mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
		if (!success) {
			showUnsupportedTip();
			return;
		}
		
		//mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	private void showUnsupportedTip() {
		Toast.makeText(mContext,R.string.shake_not_support_sensor,Toast.LENGTH_SHORT).show();
	}

	public void stopSensor() {
		if (mSensorManager != null) {
			mSensorManager.unregisterListener(this);
			mSensorManager = null;
		}
	}

	public void onSensorChanged(SensorEvent event) {
		long currentTime = System.currentTimeMillis();
		long diffTime = currentTime - mLastUpdateTime;
		if (diffTime > UPDATE_INTERVAL) {
			if (mLastUpdateTime != 0) {
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];
				float deltaX = x - mLastX;
				float deltaY = y - mLastY;
				float deltaZ = z - mLastZ;
				float delta = (float) (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / diffTime * 10000);
				if (delta > SHAKE_THRESHOLD) {
					onShake();
				}
				mLastX = x;
				mLastY = y;
				mLastZ = z;
				Log.v("加速度传感器","x="+x+" y="+y+"  z="+z);
				Log.v("加速度传感器2","deltaX="+deltaX+" deltaY="+deltaY+"  deltaZ="+deltaZ);
			}
			mLastUpdateTime = currentTime;
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
	
	private void onShake() {
		if (mEnable && listener != null) {
			//doVibrate();
			listener.onShake();
		}
	}
	
//	private void doVibrate() {
//		if (mVibrator == null) {
//			return;
//		}
//		mVibrator.vibrate(new long[] {200, 300, 300, 300}, -1);
//	}

	public static interface OnShakeListener {
		public void onShake();
	}

}
