package com.asoftwaresolution.rememberme.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asoftwaresolution.rememberme.R;
import com.asoftwaresolution.rememberme.activitys.AboutMe;
import com.asoftwaresolution.rememberme.activitys.Friends;
import com.asoftwaresolution.rememberme.activitys.MainActivity;
import com.asoftwaresolution.rememberme.activitys.RememberMeOneTimeActivity;
import com.asoftwaresolution.rememberme.activitys.Things_To_Remember;
import com.asoftwaresolution.rememberme.fragments.RepeatFragment;

public class CustomAndroidGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] string;
    private final int[] Imageid;

    public CustomAndroidGridViewAdapter(Context c, String[] string, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.string = string;
    }

    @Override
    public int getCount() {
        return string.length;
    }

    @Override
    public Object getItem(int p) {
        return null;
    }

    @Override
    public long getItemId(int p) {
        return 0;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.gridview_custom_layout, null);
            final TextView textView = (TextView) grid.findViewById(R.id.gridview_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.gridview_image);
            textView.setText(string[p]);
            imageView.setImageResource(Imageid[p]);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(textView.getText().toString() == "Remember me this")
                    {
                        FragmentManager fm = ((FragmentActivity)mContext).getSupportFragmentManager();
                        RepeatFragment newFragment = new RepeatFragment();
                        newFragment.show(fm, "repeatFragment");
                    }
                    else if(textView.getText().toString() == "Things to remember")
                    {
                        Intent intentRemember = new Intent(mContext, Things_To_Remember.class);
                        mContext.startActivity(intentRemember);
                    }
                    else if(textView.getText().toString() == "My friends")
                    {
                        Intent intentConfig = new Intent(mContext, Friends.class);
                        mContext.startActivity(intentConfig);
                    }
                    else if(textView.getText().toString() == "My Account")
                    {
                        Intent intentAbout = new Intent(mContext, AboutMe.class);
                        mContext.startActivity(intentAbout);
                    }
                }
            });
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
