package com.supets.pet.module.led;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supets.pet.ledview.R;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {

    private ArrayList<String> data = new ArrayList<>();

    public void setData(ArrayList<String> data) {
        if (data != null) {
            this.data.addAll(data);
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem, viewGroup, false);
        }

        TextView name = view.findViewById(R.id.text);
        name.setText(data.get(i));
        return view;
    }
}