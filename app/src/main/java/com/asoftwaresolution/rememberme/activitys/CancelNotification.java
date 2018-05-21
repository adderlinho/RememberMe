package com.asoftwaresolution.rememberme.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.restApi.EndpointsApi;
import com.asoftwaresolution.rememberme.restApi.adapter.RestApiAdapter;
import com.asoftwaresolution.rememberme.restApi.model.RespuestaResponse;
import com.asoftwaresolution.rememberme.session.SessionManager;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelNotification extends AppCompatActivity {

    private Bundle bundle;
    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_notification);

        manager = new SessionManager();
        bundle = getIntent().getExtras();
        if(bundle != null)
        {
            sendDataNotification(bundle.getString("token_friend"), bundle.getString("token_session"), "Te informamos que el recordatorio que enviaste a " + manager.getPreferences(CancelNotification.this, "user").toString() +" fue rechazado.");
            finish();
        }
    }

    public void sendDataNotification(final String token_friend, final String token_session, final String mensaje) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorRespuesta();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiHeroku(gsonMediaRecent);
        Call<RespuestaResponse> respuestaResponseCall = endpointsApi.sendDataNotification(token_friend, token_session, mensaje);
        respuestaResponseCall.enqueue(new Callback<RespuestaResponse>() {
            @Override
            public void onResponse(Call<RespuestaResponse> call, Response<RespuestaResponse> response) {
                RespuestaResponse respuestaResponse =  response.body();

                if(respuestaResponse != null)
                {
                    Log.i("TODO SALIO BIEN", respuestaResponse.toString());
                }
                else
                {
                    Toast.makeText(CancelNotification.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                    Log.i("FALLO LA RESPUESTA", "respuestaResponse is null.");
                }
            }

            @Override
            public void onFailure(Call<RespuestaResponse> call, Throwable t) {
                Toast.makeText(CancelNotification.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                Log.i("FALLO LA CONEXIÓN", t.toString());
            }
        });
    }
}
