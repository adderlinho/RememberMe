package com.asoftwaresolution.rememberme.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.adapters.GetRemindersAdapter;
import com.asoftwaresolution.rememberme.fragments.RepeatFragment;
import com.asoftwaresolution.rememberme.restApi.EndpointsApi;
import com.asoftwaresolution.rememberme.restApi.Interfaces.IGetReminders;
import com.asoftwaresolution.rememberme.restApi.adapter.RestApiAdapter;
import com.asoftwaresolution.rememberme.restApi.model.ClientesResponse;
import com.asoftwaresolution.rememberme.restApi.model.ReminderResponse;
import com.asoftwaresolution.rememberme.restApi.pojo.ReminderPojo;
import com.asoftwaresolution.rememberme.session.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Things_To_Remember extends AppCompatActivity implements IGetReminders {

    private Toolbar toolbar;
    private ArrayList<ReminderPojo> reminderPojos;
    private RecyclerView rvReminders;
    private TextView status;
    private SessionManager manager;
    private Button btnCrear;
    private String email = "";
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things_to_remember);

        toolbar = (Toolbar) findViewById(R.id.miActionBar);
        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        manager            = new SessionManager();
        status             = findViewById(R.id.status);
        rvReminders        = findViewById(R.id.rvReminders);
        btnCrear           = findViewById(R.id.btnCrear);
        mProgressView   = findViewById(R.id.loading_progress);

        email = manager.getPreferences(this, "email");

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepeatFragment newFragment = new RepeatFragment();
                newFragment.show(getSupportFragmentManager(), "repeatFragment");
            }
        });

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.setVisibility(View.VISIBLE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(View.VISIBLE);
            }
        });

        getDataReminders(email);

    }

    public void getDataReminders(final String email) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorGetReminders();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiHeroku(gsonMediaRecent);
        Call<ReminderResponse> reminderResponseCall = endpointsApi.getDataReminders(email);
        reminderResponseCall.enqueue(new Callback<ReminderResponse>() {
            @Override
            public void onResponse(Call<ReminderResponse> call, Response<ReminderResponse> response) {
                ReminderResponse reminderResponse =  response.body();

                if(reminderResponse != null)
                {
                    reminderPojos = reminderResponse.getReminderPojos();
                    generarLinearLayoutVertical();
                    inicializarAdaptadorML(crearAdaptador(reminderPojos));
                    if(reminderPojos.size() <= 0)
                    {
                        status.setVisibility(View.VISIBLE);
                    }
                    mProgressView.setVisibility(View.GONE);
                    Log.i("TODO SALIO BIEN", reminderResponse.toString());
                }
                else
                {
                    Toast.makeText(Things_To_Remember.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                    Log.i("FALLO LA RESPUESTA", "reminderResponse is null.");
                    status.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ReminderResponse> call, Throwable t) {
                Toast.makeText(Things_To_Remember.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                Log.i("FALLO LA CONEXIÓN", t.toString());
                status.setVisibility(View.VISIBLE);
                mProgressView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void generarLinearLayoutVertical() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvReminders.setLayoutManager(linearLayoutManager);
    }

    @Override
    public GetRemindersAdapter crearAdaptador(ArrayList<ReminderPojo> reminderPojos) {
        GetRemindersAdapter adapter = new GetRemindersAdapter(reminderPojos, this);
        return adapter;
    }

    @Override
    public void inicializarAdaptadorML(GetRemindersAdapter adaptador) {
        rvReminders.setAdapter(adaptador);
    }
}