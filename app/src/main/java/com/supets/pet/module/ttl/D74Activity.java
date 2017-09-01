package com.supets.pet.module.ttl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.supets.pet.ledview.R;
import com.supets.pet.module.sensor.BaseOrientationActivity;

import java.util.ArrayList;


public class D74Activity extends BaseOrientationActivity {

    private D74Adapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.title);
        title.setText("模拟74系列数字电路");

        listView = findViewById(R.id.list);
        adapter = new D74Adapter();
        ArrayList<D74> d = new ArrayList<>();
        d.add(new D74(7400, "7400", "2输入端四与非门"));
        d.add(new D74(74138, "74138", "3-8线译码器/复工器"));
        d.add(new D74(74151, "74151", "8选1数据选择器"));

        adapter.setData(d);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                doWithButton((D74) adapterView.getItemAtPosition(position));
            }
        });


    }

    private void doWithButton(D74 data) {
        switch (data.type) {
            case 74138: {
                Intent intent = new Intent(D74Activity.this, D74138Activity.class);
                startActivity(intent);
            }
            break;
            case 74151: {
                Intent intent = new Intent(D74Activity.this, D74151Activity.class);
                startActivity(intent);
            }
            break;
            case 7400: {
                Intent intent = new Intent(D74Activity.this, D7400Activity.class);
                startActivity(intent);
            }
            break;
            default:
                break;
        }
    }


}
