package com.styleappteam.styleapp.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.classes.worker;
import com.styleappteam.styleapp.classes.worker_adapter;

import static com.styleappteam.styleapp.VariablesGlobales.place_global;

/**
 * Created by Luis on 06/06/2017.
 */

public class WorkerList extends AppCompatActivity{
    /*private LinearLayout buttom_payment_layout;
    private boolean visibility=true;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workers);
        ListView rootView= (ListView) findViewById(R.id.list);

        worker_adapter adapter1=new worker_adapter(this, R.layout.worker_list);
        for(int i=0;i<40;i++)
            adapter1.add(new worker("Eduardo Angulo", R.drawable.stars, R.drawable.user));

        rootView.setAdapter(adapter1);

        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
                /*buttom_payment_layout=(LinearLayout)findViewById(R.id.button_payment_layout);
                if(visibility)
                {
                    buttom_payment_layout.setVisibility(View.VISIBLE);
                    visibility=false;
                }
                else
                {
                    buttom_payment_layout.setVisibility(View.GONE);
                    visibility=true;
                }*/

                //VAMOS A IMPLEMENTAR DEFRENTE LA VISTA DE PAGO
                Toast.makeText(WorkerList.this,"Falta implementar layout de pago",Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab_maps);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(WorkerList.this, MapActivity.class));
            }
        });



        if(place_global!=null)
        {
            Toast.makeText(this,"La ubicación actual es: "+ place_global.getAddress(), Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"No se tiene información de la dirección",Toast.LENGTH_LONG).show();
        }
    }
}
