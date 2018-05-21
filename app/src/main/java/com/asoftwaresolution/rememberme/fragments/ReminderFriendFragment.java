package com.asoftwaresolution.rememberme.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.adapters.ReminderAdapter;

public class ReminderFriendFragment extends DialogFragment {

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
                getResources().getString(R.string.title_tab_repeat_one_time)
        };

        //ADAPTER
        adapter=new ReminderAdapter(getActivity(),tvshows);
        rv.setAdapter(adapter);

        this.getDialog().setTitle(getResources().getString(R.string.title_dialog_reminders));


        return rootView;
    }
}