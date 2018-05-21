package com.asoftwaresolution.rememberme.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.activitys.RememberMeDailyActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeMonthlyActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeOneTimeActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeWeekDayActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeWeeklyActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeYearlyActivity;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private String[] tvshows;
    private Context context;

    public ReminderAdapter(Context context, String[] tvshows)
    {
        this.context = context;
        this.tvshows  = tvshows ;
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_to_choose_reminder_type, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReminderViewHolder holder, final int position) {
        holder.nameTxt.setText(tvshows[position]);

        holder.nameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        Intent intentRememberOT = new Intent(context, RememberMeOneTimeActivity.class);
                        context.startActivity(intentRememberOT);
                        break;
                    case 1:
                        Intent intentRememberDaily = new Intent(context, RememberMeDailyActivity.class);
                        context.startActivity(intentRememberDaily);
                        break;
                    case 2:
                        Intent intentRememberWeekday = new Intent(context, RememberMeWeekDayActivity.class);
                        context.startActivity(intentRememberWeekday);
                        break;
                    case 3:
                        Intent intentRememberWeekly = new Intent(context, RememberMeWeeklyActivity.class);
                        context.startActivity(intentRememberWeekly);
                        break;
                    case 4:
                        Intent intentRememberMonthly = new Intent(context, RememberMeMonthlyActivity.class);
                        context.startActivity(intentRememberMonthly);
                        break;
                    case 5:
                        Intent intentRememberYearly = new Intent(context, RememberMeYearlyActivity.class);
                        context.startActivity(intentRememberYearly);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvshows.length;
    }

    public static class ReminderViewHolder extends RecyclerView.ViewHolder
    {
        private TextView    nameTxt;

        public ReminderViewHolder(View view)
        {
            super(view);
            nameTxt = (TextView) view.findViewById(R.id.nameTxt);
        }
    }
}
