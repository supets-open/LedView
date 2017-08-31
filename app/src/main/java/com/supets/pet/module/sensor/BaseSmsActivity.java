package com.supets.pet.module.sensor;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * LedView
 *
 * @user lihongjiang
 * @description
 * @date 2017/8/21
 * @updatetime 2017/8/21
 */

public class BaseSmsActivity extends Activity {

    public void sendSms(String receiverPhone, String msg) {
        // 使用短信管理类提供的静态方法创建一个默认的短信管理实例
        SmsManager smsmanager = SmsManager.getDefault();
        // 第一个参数是收信方的电话号码
        // 第二个参数是短信服务中心号码
        // 第三个参数是短信内容
        // 第四个参数是发送信息的描述意图pending服务,如果发送成功,会广播出去
        // 第五个参数是信息反馈的描述意图pending服务,如果传送成功,会广播出去
        PendingIntent pisend = PendingIntent.getBroadcast(
                this, 0, new Intent(
                        "MY_SMS_SEND"), 0);
        PendingIntent piDeliver = PendingIntent.getBroadcast(
                this, 0, new Intent(
                        "MY_SMS_DELIVER"), 0);
        smsmanager.sendTextMessage(receiverPhone, null,
                "led-sms:"+msg, pisend, piDeliver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSms);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(mSms, new IntentFilter("MY_SMS_DELIVER"));
    }

    private BroadcastReceiver mSms = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = null;
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    message = "Message sent!";
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    message = "Error.";
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    message = "Error: No service.";
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    message = "Error: Null PDU.";
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    message = "Error: Radio off.";
                    break;
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT)
                    .show();
        }
    };


}
