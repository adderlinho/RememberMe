package com.asoftwaresolution.rememberme.activitys;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.adapters.CustomAndroidGridViewAdapter;
import com.asoftwaresolution.rememberme.fragments.RepeatFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // UI references.
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayoutAndroid;
    private GridView gridView;

    /**
     * Strings to menu
     */
    public static String[] gridViewStrings = {
            "Remember me this",
            "Things to remember",
            "My friends",
            "My Account",
    };

    /**
     * Images to menu
     */
    public static int[] gridViewImages = {
            R.drawable.clock,
            R.drawable.remember_me_this,
            R.drawable.my_friends,
            R.drawable.settings
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the initial form.
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new CustomAndroidGridViewAdapter(this, gridViewStrings, gridViewImages));

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("FIREBASE_TOKEN", token);

        initInstances();
    }

    private void initInstances() {
        collapsingToolbarLayoutAndroid = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_android_layout);
        collapsingToolbarLayoutAndroid.setTitle(getResources().getString(R.string.app_name));
        collapsingToolbarLayoutAndroid.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        collapsingToolbarLayoutAndroid.setExpandedTitleColor(Color.TRANSPARENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mRememberMe:
                RepeatFragment newFragment = new RepeatFragment();
                newFragment.show(getSupportFragmentManager(), "repeatFragment");
                break;
            case R.id.mThings:
                Intent intentThings = new Intent(MainActivity.this, Things_To_Remember.class);
                startActivity(intentThings);
                break;
            case R.id.mMyFriends:
                Intent intentFriends = new Intent(MainActivity.this, Friends.class);
                startActivity(intentFriends);
                break;
            case R.id.mMyAccount:
                Intent intentAbout = new Intent(MainActivity.this, AboutMe.class);
                startActivity(intentAbout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.title_exit))
                .setMessage(getResources().getString(R.string.question_exit))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
