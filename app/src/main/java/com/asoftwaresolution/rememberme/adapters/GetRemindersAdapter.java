package com.asoftwaresolution.rememberme.adapters;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.asoftwaresolution.rememberme.activitys.RememberMeDailyActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeMonthlyActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeOneTimeActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeWeekDayActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeWeeklyActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeYearlyActivity;
import com.asoftwaresolution.rememberme.activitys.Things_To_Remember;
import com.asoftwaresolution.rememberme.restApi.EndpointsApi;
import com.asoftwaresolution.rememberme.restApi.adapter.RestApiAdapter;
import com.asoftwaresolution.rememberme.restApi.model.RespuestaResponse;
import com.asoftwaresolution.rememberme.restApi.pojo.ReminderPojo;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;

public class GetRemindersAdapter extends RecyclerView.Adapter<GetRemindersAdapter.RemindersViewHolder> {

    private ArrayList<ReminderPojo> reminderPojos;
    private Activity activity;

    public GetRemindersAdapter(ArrayList<ReminderPojo> reminderPojos, Activity activity)
    {
        this.reminderPojos = reminderPojos;
        this.activity = activity;
    }

    @Override
    public RemindersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_reminder, parent, false);
        return new RemindersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemindersViewHolder holder, int position) {
        final ReminderPojo reminderPojo = reminderPojos.get(position);

        if(reminderPojos.size()  <= 0)
        {
            holder.titleCardview.setVisibility(View.GONE);
            holder.messageCardview.setVisibility(View.GONE);
            holder.date_hourCardview.setVisibility(View.GONE);
            holder.imgbInfoCardview.setVisibility(View.GONE);
            holder.imgbDeleteCardview.setVisibility(View.GONE);
        }
        else
        {
            holder.titleCardview.setText(activity.getResources().getString(R.string.prompt_title) + reminderPojo.getTitulo());
            holder.messageCardview.setText(activity.getResources().getString(R.string.prompt_message) + reminderPojo.getMensaje());
            holder.date_hourCardview.setText(reminderPojo.getFecha() + " " + reminderPojo.getHora() + " " + reminderPojo.getTipo());

            holder.imgbInfoCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (reminderPojo.getTipo())
                    {
                        case "one_time":
                            Intent intentOneTime = new Intent(activity, RememberMeOneTimeActivity.class);
                            intentOneTime.putExtra("title", reminderPojo.getTitulo());
                            intentOneTime.putExtra("message", reminderPojo.getMensaje());
                            intentOneTime.putExtra("date", reminderPojo.getFecha());
                            intentOneTime.putExtra("hour", reminderPojo.getHora());
                            intentOneTime.putExtra("alarm_id", reminderPojo.getAlarm_id());
                            intentOneTime.putExtra("id", reminderPojo.getId());
                            activity.startActivity(intentOneTime);
                            activity.finish();
                            break;
                        case "daily":
                            Intent intentDaily = new Intent(activity, RememberMeDailyActivity.class);
                            intentDaily.putExtra("title", reminderPojo.getTitulo());
                            intentDaily.putExtra("message", reminderPojo.getMensaje());
                            intentDaily.putExtra("hour", reminderPojo.getHora());
                            intentDaily.putExtra("alarm_id", reminderPojo.getAlarm_id());
                            activity.startActivity(intentDaily);
                            activity.finish();
                            break;
                        case "weekday":
                            Intent intentWeekday = new Intent(activity, RememberMeWeekDayActivity.class);
                            intentWeekday.putExtra("title", reminderPojo.getTitulo());
                            intentWeekday.putExtra("message", reminderPojo.getMensaje());
                            intentWeekday.putExtra("hour", reminderPojo.getHora());
                            intentWeekday.putExtra("alarm_id", reminderPojo.getAlarm_id());
                            activity.startActivity(intentWeekday);
                            activity.finish();
                            break;
                        case "weekly":
                            Intent intentWeekly = new Intent(activity, RememberMeWeeklyActivity.class);
                            intentWeekly.putExtra("title", reminderPojo.getTitulo());
                            intentWeekly.putExtra("message", reminderPojo.getMensaje());
                            intentWeekly.putExtra("hour", reminderPojo.getHora());
                            intentWeekly.putExtra("alarm_id", reminderPojo.getAlarm_id());
                            activity.startActivity(intentWeekly);
                            activity.finish();
                            break;
                        case "monthly":
                            Intent intentMonthly = new Intent(activity, RememberMeMonthlyActivity.class);
                            intentMonthly.putExtra("title", reminderPojo.getTitulo());
                            intentMonthly.putExtra("message", reminderPojo.getMensaje());
                            intentMonthly.putExtra("date", reminderPojo.getFecha());
                            intentMonthly.putExtra("hour", reminderPojo.getHora());
                            intentMonthly.putExtra("alarm_id", reminderPojo.getAlarm_id());
                            activity.startActivity(intentMonthly);
                            activity.finish();
                            break;
                        case "yearly":
                            Intent intentYearly = new Intent(activity, RememberMeYearlyActivity.class);
                            intentYearly.putExtra("title", reminderPojo.getTitulo());
                            intentYearly.putExtra("message", reminderPojo.getMensaje());
                            intentYearly.putExtra("date", reminderPojo.getFecha());
                            intentYearly.putExtra("hour", reminderPojo.getHora());
                            intentYearly.putExtra("alarm_id", reminderPojo.getAlarm_id());
                            activity.startActivity(intentYearly);
                            activity.finish();
                            break;
                    }
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
                                    Intent intentAlarm = new Intent(activity, AlarmReceiverActivity.class);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(activity, reminderPojo.getAlarm_id(), intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
                                    AlarmManager alarmManager = (AlarmManager) activity.getSystemService(ALARM_SERVICE);
                                    alarmManager.cancel(pendingIntent);

                                    DeleteDataReminder(reminderPojo.getId());
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
        return reminderPojos.size();
    }

    public static class RemindersViewHolder extends RecyclerView.ViewHolder
    {
        private ImageButton   imgbInfoCardview;
        private ImageButton   imgbDeleteCardview;
        private TextView      titleCardview;
        private TextView      messageCardview;
        private TextView      date_hourCardview;

        public RemindersViewHolder(View view)
        {
            super(view);
            imgbInfoCardview       = view.findViewById(R.id.imgbInfoCardview);
            imgbDeleteCardview     = view.findViewById(R.id.imgbDeleteCardview);
            titleCardview          = view.findViewById(R.id.titleCardview);
            messageCardview        = view.findViewById(R.id.messageCardview);
            date_hourCardview      = view.findViewById(R.id.date_hourCardview);
        }
    }

    public void DeleteDataReminder(final String id) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorRespuesta();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiHeroku(gsonMediaRecent);
        Call<RespuestaResponse> respuestaResponseCall = endpointsApi.deleteDataReminder(id);
        respuestaResponseCall.enqueue(new Callback<RespuestaResponse>() {
            @Override
            public void onResponse(Call<RespuestaResponse> call, Response<RespuestaResponse> response) {
                RespuestaResponse deleteResponse =  response.body();

                if(deleteResponse != null)
                {
                    Intent intentActualizar = new Intent(activity, Things_To_Remember.class);
                    activity.startActivity(intentActualizar);
                    activity.finish();
                    Log.i("TODO SALIO BIEN", deleteResponse.toString());
                }
                else
                {
                    Toast.makeText(activity, activity.getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                    Log.i("FALLO LA RESPUESTA", "deleteResponse is null.");
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
