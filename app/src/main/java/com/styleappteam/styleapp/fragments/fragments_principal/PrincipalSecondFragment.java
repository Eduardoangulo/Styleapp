package com.styleappteam.styleapp.fragments.fragments_principal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.adapters.Services_Adapter;

import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentService;
import static com.styleappteam.styleapp.VariablesGlobales.currentType;

/**
 * Created by eduardo on 6/30/17.
 */

public class PrincipalSecondFragment extends Fragment{

    public PrincipalSecondFragment(){

    }
    private ProgressDialog progress;
    private Services_Adapter adapter1;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.principal_second_fragment, container, false);

        progress = new ProgressDialog(getActivity());
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        ListView rootView= (ListView) view.findViewById(R.id.list);
        adapter1=new Services_Adapter(getActivity(), R.layout.basic_list);

        rootView.setAdapter(adapter1);

        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)  {
                Fragment fragment = new WorkerList();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null)
                        .commit();
                currentService=currentType.getServices().get(position);
                System.out.println(currentService.getName());
            }
        });

        obtenerDatos();
        progress.hide();
        return view;
    }
    private void obtenerDatos() {
        adapter1.addAll(currentType.getServices());
    }
}
