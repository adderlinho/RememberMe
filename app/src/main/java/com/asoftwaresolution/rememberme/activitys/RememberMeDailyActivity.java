package com.asoftwaresolution.rememberme.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.fragments.DatePickerFragment;
import com.asoftwaresolution.rememberme.fragments.TimePickerFragment;
import com.asoftwaresolution.rememberme.restApi.EndpointsApi;
import com.asoftwaresolution.rememberme.restApi.adapter.RestApiAdapter;
import com.asoftwaresolution.rememberme.restApi.model.RespuestaResponse;
import com.asoftwaresolution.rememberme.restApi.model.ReminderResponse;
import com.asoftwaresolution.rememberme.restApi.pojo.ReminderPojo;
import com.asoftwaresolution.rememberme.session.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RememberMeDailyActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references.
    private Toolbar toolbar;
    private EditText mTitleView;
    private EditText mMessageView;
    private EditText mHourView;
    private Button   mCreateButton;
    private SessionManager manager;
    private PendingIntent pendingIntent;
    private int hour_reminder, min_reminder;
    private CheckBox chk_mon, chk_tue, chk_wed, chk_thu, chk_fri, chk_sat, chk_sun;
    private Bundle bundle;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_me_daily);

        manager = new SessionManager();
        toolbar = (Toolbar) findViewById(R.id.miActionBar);
        mProgressView   = findViewById(R.id.loading_progress);
        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mTitleView        = (EditText) findViewById(R.id.title);
        mMessageView      = (EditText) findViewById(R.id.message);
        mHourView         = (EditText) findViewById(R.id.hour);
        mCreateButton     = (Button) findViewById(R.id.reminder_create_button);

        chk_mon           = (CheckBox) findViewById(R.id.checkbox_mon);
        chk_tue           = (CheckBox) findViewById(R.id.checkbox_tue);
        chk_wed           = (CheckBox) findViewById(R.id.checkbox_wed);
        chk_thu           = (CheckBox) findViewById(R.id.checkbox_thu);
        chk_fri           = (CheckBox) findViewById(R.id.checkbox_fri);
        chk_sat           = (CheckBox) findViewById(R.id.checkbox_sat);
        chk_sun           = (CheckBox) findViewById(R.id.checkbox_sun);

        bundle = getIntent().getExtras();
        if(bundle != null)
        {
            mTitleView.setText(bundle.getString("title"));
            mMessageView.setText(bundle.getString("message"));
            mHourView.setText(bundle.getString("hour"));
        }

        mHourView.setOnClickListener(this);
        mCreateButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        menu.removeItem(R.id.mRememberMe);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mThings:
//                Intent intentSettings = new Intent(MainActivity.this, Acercade.class);
//                startActivity(intentSettings);
//                finish();
                break;
            case R.id.mMyFriends:
//                Intent intentConfig = new Intent(MainActivity.this, ActivityConfiguracion.class);
//                startActivity(intentConfig)
//                finish();
                break;
            case R.id.mMyAccount:
//                Intent intentConfig = new Intent(MainActivity.this, ActivityConfiguracion.class);
//                startActivity(intentConfig);
//                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hour:
                showTimePickerDialog();
                break;
            case R.id.reminder_create_button:
                // Reset errors.
                mTitleView.setError(null);
                mMessageView.setError(null);
                mHourView.setError(null);

                if(TextUtils.isEmpty(mTitleView.getText()))
                {
                    mTitleView.setError(getString(R.string.error_invalid_title));
                    mTitleView.requestFocus();
                    //Snackbar.make(mTitleView, getResources().getString(R.string.error_server_1), Snackbar.LENGTH_INDEFINITE);
                }
                else if(TextUtils.isEmpty(mMessageView.getText()))
                {
                    mMessageView.setError(getString(R.string.error_invalid_message));
                    mMessageView.requestFocus();
                }
                else if(TextUtils.isEmpty(mHourView.getText()))
                {
                    mHourView.setError(getString(R.string.error_invalid_time));
                    mHourView.requestFocus();
                }
                else
                {
                    mCreateButton.setEnabled(false);
                    int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
                    mProgressView.setVisibility(View.VISIBLE);
                    mProgressView.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mProgressView.setVisibility(View.VISIBLE);
                        }
                    });
                    if(bundle != null)
                    {
                        Intent intentAlarm = new Intent(this, AlarmReceiverActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, bundle.getInt("alarm_id"), intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);

                        DeleteDataReminder(bundle.getString("id"));
                    }

                    setDataReminderOT(Settings.Secure.getString(RememberMeDailyActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID),
                            manager.getPreferences(this, "email"),
                            mTitleView.getText().toString(),
                            mMessageView.getText().toString(),
                            mHourView.getText().toString());
                }
                break;
        }
    }

    private void showTimePickerDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String selectedTime = twoDigits(hourOfDay) + ":" + twoDigits(minute);
                hour_reminder = hourOfDay;
                min_reminder = minute;
                mHourView.setText(selectedTime);
            }
        });
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void setDataReminderOT(final String user_phone_id, final String user_email, final String title, final String message, final String time) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorReminder();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiHeroku(gsonMediaRecent);
        Call<ReminderResponse> reminderOTResponseCall = endpointsApi.setDataReminder(user_phone_id, user_email, title, message, "", time, "daily");
        reminderOTResponseCall.enqueue(new Callback<ReminderResponse>() {
            @Override
            public void onResponse(Call<ReminderResponse> call, Response<ReminderResponse> response) {
                ReminderResponse reminderResponse =  response.body();

                if(reminderResponse != null)
                {
                    //Se le coloca un nuevo id al recordatorio
                    int alarm_id = ThreadLocalRandom.current().nextInt(1, 15000 + 1);
                    reminderResponse.getReminderPojos().get(0).setAlarm_id(alarm_id);

                    /* Retrieve a PendingIntent that will perform a broadcast */
                    Intent intentAlarm = new Intent(RememberMeDailyActivity.this, AlarmReceiverActivity.class);
                    intentAlarm.putExtra("title", mTitleView.getText().toString());
                    intentAlarm.putExtra("message", mMessageView.getText().toString());

                    pendingIntent = PendingIntent.getActivity(RememberMeDailyActivity.this, alarm_id, intentAlarm, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());

                    if (chk_mon.isChecked()) {
                        /* Set the alarm to start at the time of the user set */
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, hour_reminder);
                        calendar.set(Calendar.MINUTE, min_reminder);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        /* Repeating on every day interval */
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

                    } else if (chk_tue.isChecked()) {
                        /* Set the alarm to start at the time of the user set */
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, hour_reminder);
                        calendar.set(Calendar.MINUTE, min_reminder);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        /* Repeating on every day interval */
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

                    } else if (chk_wed.isChecked()) {
                        /* Set the alarm to start at the time of the user set */
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, hour_reminder);
                        calendar.set(Calendar.MINUTE, min_reminder);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        /* Repeating on every day interval */
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

                    } else if (chk_thu.isChecked()) {
                        /* Set the alarm to start at the time of the user set */
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, hour_reminder);
                        calendar.set(Calendar.MINUTE, min_reminder);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        /* Repeating on every day interval */
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

                    } else if (chk_fri.isChecked()) {
                        /* Set the alarm to start at the time of the user set */
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, hour_reminder);
                        calendar.set(Calendar.MINUTE, min_reminder);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        /* Repeating on every day interval */
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

                    } else if (chk_sat.isChecked()) {
                        /* Set the alarm to start at the time of the user set */
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, hour_reminder);
                        calendar.set(Calendar.MINUTE, min_reminder);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        /* Repeating on every day interval */
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

                    } else if (chk_sun.isChecked()) {
                        /* Set the alarm to start at the time of the user set */
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        calendar.set(Calendar.HOUR_OF_DAY, hour_reminder);
                        calendar.set(Calendar.MINUTE, min_reminder);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        /* Repeating on every day interval */
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                    }

                    Toast.makeText(RememberMeDailyActivity.this, getResources().getString(R.string.successfull_server_2), Toast.LENGTH_SHORT).show();
                    Intent intentMain = new Intent(RememberMeDailyActivity.this, MainActivity.class);
                    startActivity(intentMain);
                    finish();
                    Log.i("TODO SALIO BIEN", reminderResponse.toString());
                }
                else
                {
                    Toast.makeText(RememberMeDailyActivity.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                    Log.i("FALLO LA RESPUESTA", "ReminderResponse is null.");
                }
            }

            @Override
            public void onFailure(Call<ReminderResponse> call, Throwable t) {
                Toast.makeText(RememberMeDailyActivity.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                Log.i("FALLO LA CONEXIÓN", t.toString());
            }
        });
    }

    public void DeleteDataReminder(final String id) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorRespuesta();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiHeroku(gsonMediaRecent);
        Call<RespuestaResponse> respuestaResponseCall = endpointsApi.deleteDataReminder(id);
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
                    Toast.makeText(RememberMeDailyActivity.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                    Log.i("FALLO LA RESPUESTA", "respuestaResponse is null.");
                }
            }

            @Override
            public void onFailure(Call<RespuestaResponse> call, Throwable t) {
                Toast.makeText(RememberMeDailyActivity.this, getResources().getString(R.string.error_server_1), Toast.LENGTH_SHORT).show();
                Log.i("FALLO LA CONEXIÓN", t.toString());
            }
        });
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}
