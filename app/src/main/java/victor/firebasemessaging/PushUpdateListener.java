package victor.firebasemessaging;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Patango on 18/04/2017.
 */

public interface PushUpdateListener {
    public void onPushUpdate(RemoteMessage remoteMessage);
}
