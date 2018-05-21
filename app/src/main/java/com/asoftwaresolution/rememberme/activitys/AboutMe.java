package com.asoftwaresolution.rememberme.activitys;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.session.SessionManager;

public class AboutMe extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView userAboutMe;
    private TextView emailAboutMe;
    private TextView deviceAboutMe;
    private TextView codeAboutMe;

    private SessionManager manager;
    private String user = "", email = "", token_session = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        toolbar = (Toolbar) findViewById(R.id.miActionBar);
        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        manager            = new SessionManager();
        userAboutMe        = findViewById(R.id.userAboutMe);
        emailAboutMe       = findViewById(R.id.emailAboutMe);
        deviceAboutMe      = findViewById(R.id.deviceAboutMe);
        codeAboutMe        = findViewById(R.id.codeAboutMe);

        user          = manager.getPreferences(this, "user");
        email         = manager.getPreferences(this, "email");
        token_session = manager.getPreferences(this, "token_session");

        userAboutMe.setText(getResources().getString(R.string.prompt_user) + ": " + user);
        emailAboutMe.setText(getResources().getString(R.string.prompt_email) + ": " + email);
        deviceAboutMe.setText(getResources().getString(R.string.prompt_device_id) + ": " + Settings.Secure.getString(AboutMe.this.getContentResolver(), Settings.Secure.ANDROID_ID));
        codeAboutMe.setText(getResources().getString(R.string.prompt_code) + ": " + token_session.replace("-",""));


        codeAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Code", token_session.replace("-",""));
                clipboard.setPrimaryClip(clip);
            }
        });
    }
}
