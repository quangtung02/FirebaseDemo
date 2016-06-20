package rlm.sfv.org.com.firebasedemo.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by nguyen.quang.tung on 6/17/2016.
 */
public class MyInstanceIDListenerService extends FirebaseInstanceIdService {

    public static final String TAG = MyInstanceIDListenerService.class.getName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        // Get updated an InstanceID token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}
