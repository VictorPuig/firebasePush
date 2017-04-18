package victor.firebasemessaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Patango on 18/04/2017.
 */

public class TopicSubscription extends BroadcastReceiver implements PushUpdateListener{

    private static final String TAG = "TopicSubscription";

    String topic;
    PushUpdateListener listener;

    Context ctx;

    boolean subscribed;

    public TopicSubscription(Context ctx, String topic) {
        this.topic = topic;
        this.ctx = ctx;
        listener = this;

        subscribed = false;
    }

    public void setListener(PushUpdateListener listener) {
        this.listener = listener;
    }

    public String getTopic() {
        return topic;
    }

    public void subscribe() {
        Log.d(TAG, "Subcribing to firebase topic '" + topic + "'");
        FirebaseMessaging.getInstance().subscribeToTopic(topic);

        subscribed = true;

        //Registra el receiver per a rebre els intents amb els canvis que envia el firebaseMessagingService
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.packageName + "." + topic);
        ctx.registerReceiver(this, intentFilter);
    }

    public void unsubscribe() {
        Log.d(TAG, "Unsubscribing from firebase topic '" + topic + "'");

        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);

        if (subscribed)
            ctx.unregisterReceiver(this);

        subscribed = false;

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onRecieve called");
        listener.onPushUpdate((RemoteMessage) intent.getParcelableExtra("remoteMessage"));
    }

    @Override
    public void onPushUpdate(RemoteMessage remoteMessage) {
        Log.w(TAG, "Default listener called. Set your custom listener.");
    }


}
