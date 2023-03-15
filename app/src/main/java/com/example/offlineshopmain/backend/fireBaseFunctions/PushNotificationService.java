package com.example.offlineshopmain.backend.fireBaseFunctions;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.example.offlineshopmain.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationService extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        String title = message.getNotification().getTitle();
        String body = message.getNotification().getBody();
        final String channelID = "HEADS_UP_NOTIFICATION";
        NotificationChannel channel = new NotificationChannel(channelID, "MyNotification", NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, channelID).setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.me)
                .setAutoCancel(true);

        NotificationManagerCompat.from(this).notify(1, notification.build());
    }
}
