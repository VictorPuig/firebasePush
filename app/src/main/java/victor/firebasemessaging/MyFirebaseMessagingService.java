package victor.firebasemessaging;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Patango on 18/04/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "PUSH Message recieved");

        Log.d(TAG, "From: " + remoteMessage.getFrom().replace("/topics/", ""));

        String notificationTitle;
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Has notification: true");
            Log.d(TAG, "Notification title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Notification body: " + remoteMessage.getNotification().getBody());
            notificationTitle = remoteMessage.getNotification().getTitle() + ": " + remoteMessage.getNotification().getBody();
        } else {
            Log.d(TAG, "Has notification: false");
            notificationTitle = "Empty";
        }

        if (!remoteMessage.getData().isEmpty()) {
            Log.d(TAG, "Has data: true");
            Log.d(TAG, "Data: " + remoteMessage.getData());
        } else {
            Log.d(TAG, "Has data: false");

        }

        Intent intent = new Intent();
        intent.putExtra("remoteMessage", remoteMessage);
        intent.setAction(Constants.packageName + "." + remoteMessage.getFrom().replace("/topics/", ""));
        sendBroadcast(intent); //envia el missatge de la notificacio als topicSubscription que estan escoltant en el metode onPushUpdate

        Notification notification = new Notification.Builder(this)
                .setContentTitle("New Push Notification")
                .setContentText("Notification: " + notificationTitle + ", Data: " + remoteMessage.getData())
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
    }

}
