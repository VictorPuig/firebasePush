package victor.firebasemessaging;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Patango on 18/04/2017.
 */

public class ExampleApp extends Application implements PushUpdateListener {

    private static final String TAG = "ExampleApp";

    TopicSubscription allSubscription;

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE);

        allSubscription = new TopicSubscription(this, "everyone");
        allSubscription.setListener(this);
        allSubscription.subscribe();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public String getFCMToken() {
        return getSharedPreferences().getString(Constants.FCE_TOKEN_NAME, null);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d(TAG, "New configuration");
    }

    @Override
    public void onPushUpdate(RemoteMessage remoteMessage) {
        Log.d(TAG, "New push notification for everyone");
    }
}
