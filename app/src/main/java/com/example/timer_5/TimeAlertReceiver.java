package com.example.timer_5;

import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import androidx.core.app.NotificationCompat;

public class TimeAlertReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        TimeNotificationHelper notificationHelper = new TimeNotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }
}