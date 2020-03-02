package com.antika.berk.ggeasy.helper;

import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Lenovo- on 19.1.2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: " + token);
        sendRegistrationToServer(token,Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
    }

    public void sendRegistrationToServer(String token,String id) {
        new sendToken().execute(token,id);
    }

    private class sendToken extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
          return null;
        }
    }

}