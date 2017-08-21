package com.supets.pet.sms;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.telephony.SmsMessage;

import com.supets.pet.activity.MainActivity;

public class SmsRecerver extends BroadcastReceiver {

//    private static final String regix = "led-sms:";
    private static final String regix = "";

    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent arg1) {
        // 判断当前广播是不是系统接收到短信后发出的广播ACTION
        if (arg1.getAction().equals(ACTION)) {
            // 获取bundle内容
            Bundle bundle = arg1.getExtras();
            if (bundle != null && bundle.size() > 0) {
                // 获取短信内容
                Object[] pdus = (Object[]) bundle.get("pdus");
                for (Object obj : pdus) {
                    // 转化成短信对象
                    SmsMessage message = SmsMessage.createFromPdu((byte[]) obj);
                    // 获取短信内容
                    String content = message.getMessageBody();
                    // 获取源地址
                    String phone = message.getOriginatingAddress();
                    // 判断短信内容
                    System.out.println(content);
                    /**
                     * 接收方短信处理,在后台服务中处理
                     */
                    if (content.startsWith(regix)) {
                        // 启动短信服务程序
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("phone", phone);
                        intent.putExtra("message", content.substring(regix.length()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        // 使用这种方法启动服务,二者之间没有联系
                        // 广播宿主线程销毁,服务还在
                        // 采用广播启动子线程来完成耗时操作,虽然广播接收器消失,线程还在,
                        // 但由于是没有活动的空线程,虽时杀死,此时线程也被一起杀死,在广播接收器中开启子线程不安全.
                        // 一般只在activity中开启子线程
                        // 一般在广播中开启服务
                        context.startActivity(intent);
                        // 停止广播,不向后面广播
                        // 就不会再消息栏显示,是因为没人处理广播了,才会提示出来
                        // 处理了不停止,还会继续广播下去
                        abortBroadcast();
                        // 因为优先级最高,得到该广播后先处理了,并停止广播了
                        // 所以在系统用户收件箱处理不了改短信息了
                    }
                }
            }
        }
    }
}
