package com.rogacheski.lbd.lbdmusic.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rogacheski.lbd.lbdmusic.R;

import java.util.List;

/**
 * Created by vis_a on 26-Jun-17.
 */

public class HintAdapter<T> extends ArrayAdapter {


    public HintAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = super.getView(position, convertView, parent);
        if (position == getCount()) {
            ((TextView)v).setTextColor(getContext().getColor(R.color.colorGrey));
        }

        return v;
    }

    @Override
    public int getCount() {
        return super.getCount()-1; // you dont display last item. It is used as hint.
    }
}
