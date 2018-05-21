package com.asoftwaresolution.rememberme.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.DebugUtils;
import android.util.Log;
import android.widget.Toast;

import com.asoftwaresolution.rememberme.activitys.CancelNotification;
import com.asoftwaresolution.rememberme.activitys.MainActivity;
import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.activitys.RememberMeOneTimeActivity;
import com.asoftwaresolution.rememberme.restApi.EndpointsApi;
import com.asoftwaresolution.rememberme.restApi.adapter.RestApiAdapter;
import com.asoftwaresolution.rememberme.restApi.model.RespuestaResponse;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AdderlyS on 3/11/2017.
 */

public class NotificationService extends FirebaseMessagingService {

    private static final String TAG          = "FirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        if(remoteMessage.getData().get("tipo") == null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.remind)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setContentIntent(pendingIntent)
                    .setSound(sonido)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setWhen(0);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationCompat.build());
        }
        else
        {
            Intent intentOneTime = new Intent(this, RememberMeOneTimeActivity.class);
            intentOneTime.putExtra("title", remoteMessage.getData().get("titulo").toString());
            intentOneTime.putExtra("message", remoteMessage.getData().get("mensaje").toString());
            intentOneTime.putExtra("date", remoteMessage.getData().get("fecha").toString());
            intentOneTime.putExtra("hour", remoteMessage.getData().get("hora").toString());
            PendingIntent seeIntent = PendingIntent.getActivity(this, 0, intentOneTime, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent intentCancel = new Intent(this, CancelNotification.class);
            intentCancel.putExtra("token_friend", remoteMessage.getData().get("token_session").toString());
            intentCancel.putExtra("token_session", remoteMessage.getData().get("id").toString());
            PendingIntent cancelIntent = PendingIntent.getActivity(this, 0, intentCancel, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.remind)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setContentIntent(seeIntent)
                    .setDeleteIntent(cancelIntent)
                    .setSound(sonido)
                    .setAutoCancel(true)
                    .addAction(R.drawable.eye, getResources().getString(R.string.question_notification1), seeIntent)
                    .addAction(R.drawable.cancel, getResources().getString(R.string.question_notification2), cancelIntent)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setWhen(0);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationCompat.build());
        }
    }
}
