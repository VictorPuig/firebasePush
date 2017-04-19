package victor.firebasemessaging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.messaging.RemoteMessage;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainApp";

    TopicSubscription topicSubscription;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        topicSubscription = new TopicSubscription(this, "main");
        topicSubscription.subscribe();

        topicSubscription.setListener(new PushUpdateListener() {
            @Override
            public void onPushUpdate(RemoteMessage remoteMessage) {
                Log.d(TAG, "push recieved");
                Log.d(TAG, remoteMessage.getData().get("message"));
                textView.setText(remoteMessage.getData().get("message"));
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //topicSubscription.unsubscribe();
    }
}
