package com.example.user.myhomejarvis.FCM_package;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;


import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.user.myhomejarvis.Activity_package.Login_Activity;
import com.example.user.myhomejarvis.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by user on 2018-03-26.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MainActivity";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.v(TAG, "receive");
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("content"));

//      ?덈뱶濡쒖씠???곌묠?곌린
        PowerManager.WakeLock sCpuWakeLock;
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        sCpuWakeLock = pm.newWakeLock( PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, TAG);

        sCpuWakeLock.acquire();
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, Login_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle( title )
                .setContentText(messageBody)
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setContentIntent(pendingIntent);



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, mNotificationBuilder.build());
    }


}
