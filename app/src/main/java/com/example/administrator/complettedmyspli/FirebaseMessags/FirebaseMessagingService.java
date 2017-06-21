package com.example.administrator.complettedmyspli.FirebaseMessags;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.complettedmyspli.Post;
import com.example.administrator.complettedmyspli.R;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Administrator on 21/06/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {




    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        shownatfication(remoteMessage.getData().get("message"));

    }

    private void shownatfication(String message) {
        Intent i=new Intent(this, Post.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("FCM TEST")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent);
        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }
}
