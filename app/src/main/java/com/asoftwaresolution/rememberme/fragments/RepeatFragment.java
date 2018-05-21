package com.asoftwaresolution.rememberme.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.adapters.ReminderAdapter;

import java.util.Calendar;

public class RepeatFragment extends DialogFragment {

    private RecyclerView rv;
    private ReminderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_repeat_dialog,container);

        //RECYCER
        rv= (RecyclerView) rootView.findViewById(R.id.mRecyerID);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        //OPTIONS
        String[] tvshows = {
                getResources().getString(R.string.title_tab_repeat_one_time),
                getResources().getString(R.string.title_tab_repeat_daily),
                getResources().getString(R.string.title_tab_repeat_every_weekday),
                getResources().getString(R.string.title_tab_repeat_weekly),
                getResources().getString(R.string.title_tab_repeat_monthly),
                getResources().getString(R.string.title_tab_repeat_yearly)
        };

        //ADAPTER
        adapter=new ReminderAdapter(getActivity(),tvshows);
        rv.setAdapter(adapter);

        this.getDialog().setTitle(getResources().getString(R.string.title_dialog_reminders));


        return rootView;
    }
}