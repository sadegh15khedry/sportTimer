package com.example.sporttimer;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.media.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), "clicked",
                Toast.LENGTH_SHORT).show();

        NotificationManagerCompat manager  = NotificationManagerCompat.from(context);
        manager.cancel(1);


    }
}
