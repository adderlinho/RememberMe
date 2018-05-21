package com.asoftwaresolution.rememberme.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by AdderlyS on 3/11/2017.
 */

public class NotificacionIDTokenService extends FirebaseInstanceIdService {

    private static final String TAG = "FIREBASE_TOKEN";

    @Override
    public void onTokenRefresh() {
        //super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();

        enviarTokenRegistro(token);
    }

    public void enviarTokenRegistro(String token)
    {
        Log.d(TAG, token);
    }
}
