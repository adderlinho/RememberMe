package com.asoftwaresolution.rememberme.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.restApi.EndpointsApi;
import com.asoftwaresolution.rememberme.restApi.adapter.RestApiAdapter;
import com.asoftwaresolution.rememberme.restApi.model.ClientesResponse;
import com.asoftwaresolution.rememberme.restApi.pojo.ClientesPojo;
import com.asoftwaresolution.rememberme.session.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {

    private View mProgressView;
    private SessionManager manager;
    private ArrayList<ClientesPojo> clientesPojos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        manager = new SessionManager();
        mProgressView   = findViewById(R.id.loading_progress);


        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.setVisibility(View.VISIBLE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(View.VISIBLE);
            }
        });

        if(manager.getPreferences(this, "token_session").isEmpty())
        {
            Intent intentAbout = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(intentAbout);
            finish();
        }
        else
        {
            getDataVerification(manager.getPreferences(this, "email"), manager.getPreferences(this, "token_session"));
        }
    }

    public void getDataVerification(final String email, final String token_session) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorClientes();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiHeroku(gsonMediaRecent);
        Call<ClientesResponse> clientesResponseCall = endpointsApi.getDataRegistro(email, token_session);
        clientesResponseCall.enqueue(new Callback<ClientesResponse>() {
            @Override
            public void onResponse(Call<ClientesResponse> call, Response<ClientesResponse> response) {
                ClientesResponse clientesResponse =  response.body();

                if(clientesResponse != null)
                {
                    clientesPojos = clientesResponse.getClientesPojos();

                    if(clientesPojos.size() > 0)
                    {
                        Intent intentAbout = new Intent(LoadingActivity.this, MainActivity.class);
                        startActivity(intentAbout);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LoadingActivity.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                        Log.i("FALLO LA RESPUESTA", "clientesResponse size = 0.");
                    }
                    Log.i("TODO SALIO BIEN", clientesResponse.toString());
                }
                else
                {
                    Toast.makeText(LoadingActivity.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                    Log.i("FALLO LA RESPUESTA", "clientesResponse is null.");
                }
            }

            @Override
            public void onFailure(Call<ClientesResponse> call, Throwable t) {
                Toast.makeText(LoadingActivity.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                Log.i("FALLO LA CONEXIÓN", t.toString());
            }
        });
    }
}
