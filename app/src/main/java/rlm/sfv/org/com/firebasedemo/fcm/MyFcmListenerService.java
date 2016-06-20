package rlm.sfv.org.com.firebasedemo.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import rlm.sfv.org.com.firebasedemo.MainActivity;
import rlm.sfv.org.com.firebasedemo.R;

/**
 * Created by nguyen.quang.tung on 6/17/2016.
 */
public class MyFcmListenerService extends FirebaseMessagingService {

    public static final String TAG = MyFcmListenerService.class.getName();

    /**
     * Call when message received
     *
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String from = remoteMessage.getFrom();
        String body = remoteMessage.getNotification().getBody();

        Log.d(TAG, "From: " + from + "\nbody: " + body);

        // Send notification to device
        sendNotification(remoteMessage.getNotification().getBody());
    }

    private void sendNotification(String msgBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Create sound when received message
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Push Notification")
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, nBuilder.build());
    }
}
