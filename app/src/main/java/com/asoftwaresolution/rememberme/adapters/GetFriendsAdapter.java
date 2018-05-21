package com.asoftwaresolution.rememberme.adapters;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.activitys.AlarmReceiverActivity;
import com.asoftwaresolution.rememberme.activitys.Friends;
import com.asoftwaresolution.rememberme.activitys.RememberMeOneTimeActivity;
import com.asoftwaresolution.rememberme.fragments.ReminderFriendFragment;
import com.asoftwaresolution.rememberme.fragments.RepeatFragment;
import com.asoftwaresolution.rememberme.restApi.EndpointsApi;
import com.asoftwaresolution.rememberme.restApi.adapter.RestApiAdapter;
import com.asoftwaresolution.rememberme.restApi.model.RespuestaResponse;
import com.asoftwaresolution.rememberme.restApi.pojo.FriendPojo;
import com.asoftwaresolution.rememberme.session.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;

public class GetFriendsAdapter extends RecyclerView.Adapter<GetFriendsAdapter.RemindersViewHolder> {

    private ArrayList<FriendPojo> friendPojos;
    private Activity activity;
    private SessionManager manager;
    private String token_session = "";

    public GetFriendsAdapter(ArrayList<FriendPojo> friendPojos, Activity activity)
    {
        this.friendPojos = friendPojos;
        this.activity = activity;
    }

    @Override
    public RemindersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_friend, parent, false);
        manager            = new SessionManager();
        token_session      = manager.getPreferences(activity, "token_session");
        return new RemindersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemindersViewHolder holder, int position) {
        final FriendPojo friendPojo = friendPojos.get(position);

        if(friendPojos.size()  <= 0)
        {
            holder.usernameCardview.setVisibility(View.GONE);
            holder.emailCardview.setVisibility(View.GONE);
            holder.codeCardview.setVisibility(View.GONE);
            holder.imgbAddCardview.setVisibility(View.GONE);
            holder.imgbDeleteCardview.setVisibility(View.GONE);
        }
        else
        {
            holder.usernameCardview.setText(activity.getResources().getString(R.string.prompt_user) + ": " + friendPojo.getUsuario());
            holder.emailCardview.setText(activity.getResources().getString(R.string.prompt_email) + ": " + friendPojo.getEmail());
            holder.codeCardview.setText(activity.getResources().getString(R.string.prompt_code) + ": " + friendPojo.getId());

            holder.imgbAddCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentOneTime = new Intent(activity, RememberMeOneTimeActivity.class);
                    intentOneTime.putExtra("token_friend", friendPojo.getId());
                    intentOneTime.putExtra("username_friend", friendPojo.getUsuario());
                    activity.startActivity(intentOneTime);
                    activity.finish();
                }
            });

            holder.imgbDeleteCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            activity);

                    // Setting Dialog Title
                    alertDialog2.setTitle("Delete");

                    // Setting Dialog Message
                    alertDialog2.setMessage(activity.getResources().getString(R.string.question_delete));


                    // Setting Icon to Dialog
                    alertDialog2.setIcon(R.drawable.logo2);

                    // Setting Positive "Yes" Btn
                    alertDialog2.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    DeleteDataFriend(friendPojo.getId(), token_session);
                                }
                            });

                    // Setting Negative "NO" Btn
                    alertDialog2.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    dialog.cancel();
                                }
                            });

                    // Showing Alert Dialog
                    alertDialog2.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return friendPojos.size();
    }

    public static class RemindersViewHolder extends RecyclerView.ViewHolder
    {
        private ImageButton   imgbAddCardview;
        private ImageButton   imgbDeleteCardview;
        private TextView      usernameCardview;
        private TextView      emailCardview;
        private TextView      codeCardview;

        public RemindersViewHolder(View view)
        {
            super(view);
            imgbAddCardview       = view.findViewById(R.id.imgbAddCardview);
            imgbDeleteCardview    = view.findViewById(R.id.imgbDeleteCardview);
            usernameCardview      = view.findViewById(R.id.usernameCardview);
            emailCardview         = view.findViewById(R.id.emailCardview);
            codeCardview          = view.findViewById(R.id.codeCardview);
        }
    }

    public void DeleteDataFriend(final String token_friend, final String token_session) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorRespuesta();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiHeroku(gsonMediaRecent);
        Call<RespuestaResponse> respuestaResponseCall = endpointsApi.deleteDataFriend(token_friend, token_session);
        respuestaResponseCall.enqueue(new Callback<RespuestaResponse>() {
            @Override
            public void onResponse(Call<RespuestaResponse> call, Response<RespuestaResponse> response) {
                RespuestaResponse respuestaResponse =  response.body();

                if(respuestaResponse != null)
                {
                    Intent intentActualizar = new Intent(activity, Friends.class);
                    activity.startActivity(intentActualizar);
                    activity.finish();
                    Log.i("TODO SALIO BIEN", respuestaResponse.toString());
                }
                else
                {
                    Toast.makeText(activity, activity.getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                    Log.i("FALLO LA RESPUESTA", "respuestaResponse is null.");
                }
            }

            @Override
            public void onFailure(Call<RespuestaResponse> call, Throwable t) {
                Toast.makeText(activity, activity.getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                Log.i("FALLO LA CONEXIÓN", t.toString());
            }
        });
    }
}
