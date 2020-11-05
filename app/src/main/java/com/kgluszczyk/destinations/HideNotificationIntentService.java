package com.kgluszczyk.destinations;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;

public class HideNotificationIntentService extends IntentService {
    private static final String HIDE_NOTIFICATION = "com.kgluszczyk.destinations.action.hide_notification";
    private static final String NOTIFICATION_ID = "com.kgluszczyk.destinations.extra.notification_id";

    public static void startService(Context context, int param1) {
        Intent intent = new Intent(context, HideNotificationIntentService.class);
        intent.setAction(HIDE_NOTIFICATION);
        intent.putExtra(NOTIFICATION_ID, param1);
        context.startService(intent);
    }

    public HideNotificationIntentService() {
        super("HideNotificationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (HIDE_NOTIFICATION.equals(action)) {
                final int notificationId = intent.getIntExtra(NOTIFICATION_ID, -1);
                NotificationManagerCompat.from(this).cancel(notificationId);
            }
        }
    }
}
