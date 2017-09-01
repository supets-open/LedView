package com.supets.pet.module.ttl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.supets.pet.ledview.R;

import java.util.ArrayList;

public class D74Adapter extends BaseAdapter {

    private ArrayList<D74> data = new ArrayList<>();

    public void setData(ArrayList<D74> data) {
        if (data != null) {
            this.data.addAll(data);
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem, viewGroup, false);
        }

        TextView name = view.findViewById(R.id.text);
        name.setText(position+1+" "+data.get(position).toString());
        return view;
    }
}