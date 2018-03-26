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
import com.example.user.myhomejarvis.Activity_package.Project_Main;
import com.example.user.myhomejarvis.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by user on 2018-03-23.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{

    private static final String TAG = "MessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.d(TAG,"receivea");
        Log.d(TAG,"from + "+remoteMessage.getFrom());

        if(remoteMessage.getData().size() >0){

            Log.d(TAG,"메세지 데이타 로드" + remoteMessage.getData());

        }if(remoteMessage.getNotification() != null){
            Log.d(TAG,"메세지 노티피케이션 바디" + remoteMessage.getNotification().getBody());
        }
        sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("content"));

        //안드로이드 폰 꺠우기
        PowerManager.WakeLock sCpuWakeLock;
        PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);

        sCpuWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK| PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, TAG);

        sCpuWakeLock.acquire();



    }

    public void sendNotification(String title, String messageBody){

        Intent intent = new Intent(this, Project_Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle( title )
                .setContentText(messageBody)
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0,mNotificationBuilder.build());




    }
}
