package com.kgluszczyk.destinations;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class IntentServiceTigerBroadcastReceiver extends android.content.BroadcastReceiver {

    public String NOTIFICATION_CLOSER = "com.kgluszczyk.destinations.NOTIFICATION_CLOSER";

    @Override
    public void onReceive(Context context, Intent intent) {
        HideNotificationIntentService.startService(context, intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1));
    }
}
