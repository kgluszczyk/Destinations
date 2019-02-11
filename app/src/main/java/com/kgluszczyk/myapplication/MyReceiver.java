package com.kgluszczyk.myapplication;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

public class MyReceiver extends BroadcastReceiver {

    public String NOTIFICATION_CLOSER = "com.kgluszczyk.myapplication.NOTIFICATION_CLOSER";

    // adb shell am broadcast -a com.kgluszczyk.myapplication.NOTIFICATION_CLOSER -p com.kgluszczyk.myapplication --ei android.intent.extra.NOTIFICATION_ID 0

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat.from(context).cancel(intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1));
    }
}
