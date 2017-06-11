package com.styleappteam.styleapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.styleappteam.styleapp.classes.worker;
import com.styleappteam.styleapp.classes.worker_adapter;

/**
 * Created by Luis on 06/06/2017.
 */

public class WorkerList extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workers);
       ListView rootView= (ListView) findViewById(R.id.list);

        worker_adapter adapter1=new worker_adapter(this, R.layout.worker_list);
        for(int i=0;i<40;i++)
            adapter1.add(new worker("Eduardo Angulo", R.drawable.stars, R.drawable.user));

        rootView.setAdapter(adapter1);
    }
}
