package com.asoftwaresolution.rememberme.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.adapters.GetFriendsAdapter;
import com.asoftwaresolution.rememberme.adapters.GetRemindersAdapter;
import com.asoftwaresolution.rememberme.fragments.RepeatFragment;
import com.asoftwaresolution.rememberme.restApi.EndpointsApi;
import com.asoftwaresolution.rememberme.restApi.Interfaces.IGetFriends;
import com.asoftwaresolution.rememberme.restApi.adapter.RestApiAdapter;
import com.asoftwaresolution.rememberme.restApi.model.FriendResponse;
import com.asoftwaresolution.rememberme.restApi.model.ReminderResponse;
import com.asoftwaresolution.rememberme.restApi.model.RespuestaResponse;
import com.asoftwaresolution.rememberme.restApi.pojo.FriendPojo;
import com.asoftwaresolution.rememberme.restApi.pojo.ReminderPojo;
import com.asoftwaresolution.rememberme.session.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Friends extends AppCompatActivity implements IGetFriends {

    private ArrayList<FriendPojo> friendPojos;
    private Toolbar toolbar;
    private RecyclerView rvFriends;
    private TextView status;
    private Button btnAgregar;
    private View mProgressView;
    private SessionManager manager;
    private String email = "", token_session = "";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        context = this;
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
        rvFriends          = findViewById(R.id.rvFriends);
        btnAgregar         = findViewById(R.id.btnAgregar);
        mProgressView      = findViewById(R.id.loading_progress);

        email         = manager.getPreferences(this, "email");
        token_session = manager.getPreferences(this, "token_session");

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.setVisibility(View.VISIBLE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(View.VISIBLE);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get model_to_dialog_friend.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.model_to_dialog_friend, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText codeInput = (EditText) promptsView
                        .findViewById(R.id.code);

                // set dialog message
                AlertDialog.Builder builder = alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get code input and set it
                                        // edit text
                                        if (friendPojos.size() <= 0) {
                                            setDataFriend("-" + codeInput.getText().toString(), token_session);
                                            mProgressView.setVisibility(View.VISIBLE);
                                        } else {
                                            for (int i = 0; i < friendPojos.size(); i++) {

                                                if (friendPojos.get(i).getId().contains("-" + codeInput.getText().toString())) {
                                                    Toast.makeText(context, getResources().getString(R.string.error_friend_already_among), Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }

                                                if (i == friendPojos.size()) {
                                                    if (friendPojos.get(friendPojos.size()).getId().contains("-" + codeInput.getText().toString()))
                                                    {
                                                        Toast.makeText(context, "Este usuario ya se encuentra entre tu lista de amigos.", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    }
                                                    else
                                                    {
                                                        setDataFriend("-" + codeInput.getText().toString(), token_session);
                                                        mProgressView.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        getDataFriends(email, token_session);
    }

    public void getDataFriends(final String email, final String token_session) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorFriends();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiHeroku(gsonMediaRecent);
        Call<FriendResponse> reminderResponseCall = endpointsApi.getDataFriends(email, token_session);
        reminderResponseCall.enqueue(new Callback<FriendResponse>() {
            @Override
            public void onResponse(Call<FriendResponse> call, Response<FriendResponse> response) {
                FriendResponse friendResponse =  response.body();

                if(friendResponse != null)
                {
                    friendPojos = friendResponse.getFriendPojos();
                    generarLinearLayoutVertical();
                    inicializarAdaptadorML(crearAdaptador(friendPojos));
                    if(friendPojos.size() <= 0)
                    {
                        status.setVisibility(View.VISIBLE);
                    }
                    mProgressView.setVisibility(View.GONE);
                    Log.i("TODO SALIO BIEN", friendResponse.toString());
                }
                else
                {
                    Toast.makeText(Friends.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                    Log.i("FALLO LA RESPUESTA", "friendResponse is null.");
                    status.setVisibility(View.VISIBLE);
                    mProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<FriendResponse> call, Throwable t) {
                Toast.makeText(Friends.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                Log.i("FALLO LA CONEXIÓN", t.toString());
                status.setVisibility(View.VISIBLE);
                mProgressView.setVisibility(View.GONE);
            }
        });
    }

    public void setDataFriend(final String token_friend, final String token_session) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorRespuesta();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiHeroku(gsonMediaRecent);
        Call<RespuestaResponse> respuestaResponseCall = endpointsApi.setDataFriend(token_friend, token_session);
        respuestaResponseCall.enqueue(new Callback<RespuestaResponse>() {
            @Override
            public void onResponse(Call<RespuestaResponse> call, Response<RespuestaResponse> response) {
                RespuestaResponse respuestaResponse =  response.body();

                if(respuestaResponse != null)
                {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    Log.i("TODO SALIO BIEN", respuestaResponse.toString());
                    mProgressView.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(Friends.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                    Log.i("FALLO LA RESPUESTA", "respuestaResponse is null.");
                    mProgressView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RespuestaResponse> call, Throwable t) {
                Toast.makeText(Friends.this, getResources().getString(R.string.error_invalid_code_friend), Toast.LENGTH_SHORT).show();
                Log.i("FALLO LA CONEXIÓN", t.toString());
                mProgressView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void generarLinearLayoutVertical() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFriends.setLayoutManager(linearLayoutManager);
    }

    @Override
    public GetFriendsAdapter crearAdaptador(ArrayList<FriendPojo> friendPojos) {
        GetFriendsAdapter adapter = new GetFriendsAdapter(friendPojos, this);
        return adapter;
    }

    @Override
    public void inicializarAdaptadorML(GetFriendsAdapter adaptador) {
        rvFriends.setAdapter(adaptador);
    }
}
