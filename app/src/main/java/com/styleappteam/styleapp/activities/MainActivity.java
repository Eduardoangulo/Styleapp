package com.styleappteam.styleapp.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.facebook.login.LoginManager;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.connection_service.API_Connection;
import com.styleappteam.styleapp.connection_service.loginPost;
import com.styleappteam.styleapp.connection_service.loginResult;
import com.styleappteam.styleapp.connection_service.styleapp_API;
import com.styleappteam.styleapp.fragments.fragments_ajustes.Ajustes;
import com.styleappteam.styleapp.fragments.fragments_compartir.Compartir;
import com.styleappteam.styleapp.fragments.fragments_perfil.Miperfil;
import com.styleappteam.styleapp.fragments.fragments_mis_servicios.Misservicios;
import com.styleappteam.styleapp.fragments.fragments_principal.Principal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.styleappteam.styleapp.VariablesGlobales.MY_PERMISSIONS_REQUEST_LOCATION;
import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.URL_desarrollo;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentClient;
import static com.styleappteam.styleapp.VariablesGlobales.infoWorker;
import static com.styleappteam.styleapp.VariablesGlobales.loginPrefsEditor;
public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "MainActivity");
        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(currentClient==null){
            finish();
            goLoginScreen();
        }

        conexion= new API_Connection(getApplicationContext(), TAG, URL_desarrollo);

        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Principal()).commit(); //launch the first fragment

        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = new Principal();
                                cleanStack();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                OcultarFragment1();
                                fragment = new Miperfil();
                                cleanStack();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_3:
                                OcultarFragment1();
                                cleanStack();
                                fragment = new Misservicios();
                                fragmentTransaction = true;
                                break;
                            /*case R.id.menu_seccion_4:
                                OcultarFragment1();
                                cleanStack();
                                fragment = new Promociones();
                                fragmentTransaction = true;
                                break;*/
                            case R.id.menu_seccion_5:
                                OcultarFragment1();
                                cleanStack();
                                fragment = new Compartir();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_6:
                                OcultarFragment1();
                                cleanStack();
                                fragment = new Ajustes();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_7:
                                OcultarFragment1();
                                cleanStack();
                                sendReport();
                                break;
                            case R.id.menu_seccion_8:
                                OcultarFragment1();
                                cleanStack();
                                LoginManager.getInstance().logOut();
                                progress.show();
                                LogOut();
                                //progress.dismiss();
                                break;
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return false;
                    }
                });
    }
    private void sendReport(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.StyleAppEmail)});
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.reportSubjet));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private void LogOut(){
        loginPrefsEditor.clear();
        loginPrefsEditor.commit();
        Log.i(TAG, "User: "+currentClient.getLogedUsername()+" password: "+currentClient.getLogedPassword());
        loginPost lPost = new loginPost(currentClient.getLogedUsername(), currentClient.getLogedPassword(), currentClient.getLogedUsername());
        if(conexion==null){
            conexion= new API_Connection(getApplicationContext(), TAG, URL_desarrollo);
        }
        conexion.retrofitLoad();
        if(conexion.getRetrofit()!=null){
            Log.i(TAG, "Principal: Hay internet");
            styleapp_API service = conexion.getRetrofit().create(styleapp_API.class);
            Call<loginResult> Call = service.login(lPost);
            Call.enqueue(new Callback<loginResult>() {
                @Override
                public void onResponse(Call<loginResult> call, Response<loginResult> response) {
                    progress.dismiss();
                    if (response.isSuccessful()) {
                        if(response.body().getSuccess()){
                            Log.i(TAG, "Usuario Correcto");
                            currentClient=null;
                            goLoginScreen();
                        }
                        else {
                            Log.i(TAG, "Datos dañados");
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                        Log.e(TAG, " logOut onResponse: " + response.errorBody());
                    }

                }
                @Override
                public void onFailure(Call<loginResult> call, Throwable t) {
                    Log.e(TAG, " logOut onFailure: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            });
        }else {
            progress.dismiss();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Principal: se fue el internet");
        }
    }
    //regresar al login despues de cerrar sesion o si no habias iniciado sesion.
    private void goLoginScreen() {
        Intent intent= new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    //Este método sirve para ocultar el contenido del fragment inicial y direccionarlo al archivo fragment1.xml, es cuestión de comodidad para usar el fragment
    public void OcultarFragment1(){
        FrameLayout frame = (FrameLayout)findViewById(R.id.frame_inicial);
        frame.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void cleanStack(){
        FragmentManager fm = this.getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //progress.dismiss();
        Log.i(TAG, "onRequestPermissionsResult");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission. ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Log.i(TAG, "Permiso ubicacion autorizado");
                        if(getLocation()[0]==-1.0){
                            Toast.makeText(getApplicationContext(),"error: Se utilizará su ubicación predeterminada",Toast.LENGTH_SHORT).show();
                            infoWorker.setLatitude(-12.054227); //jalar del registro
                            infoWorker.setLongitude(-77.082802); //jalar del registro
                        }else{
                            Toast.makeText(getApplicationContext(),"Se utilizará su ubicación",Toast.LENGTH_SHORT).show();
                            infoWorker.setLatitude(getLocation()[0]); //jalar del registro
                            infoWorker.setLongitude(getLocation()[1]); //jalar del registro
                        }

                    }
                } else {
                    Log.i(TAG, "Permiso ubicacion rechazado");
                    Toast.makeText(getApplicationContext(),"Se utilizará su ubicación predeterminada",Toast.LENGTH_SHORT).show();
                    infoWorker.setLatitude(-12.054227); //jalar del registro
                    infoWorker.setLongitude(-77.082802); //jalar del registro
                }
            }
            return;
        }
    }
    private double[] getLocation(){
        double coordenadas[]= new double[2];
        // Get the location manager
        LocationManager locationManager = (LocationManager)
                getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        try{
            Location location = locationManager.getLastKnownLocation(bestProvider);
            try {
                coordenadas[0] = location.getLatitude();
                coordenadas[1]  = location.getLongitude();
            } catch (NullPointerException e) {
                coordenadas[0] = -1.0;
                coordenadas[1]  = -1.0;
            }
        }
        catch(SecurityException e){
            Log.e(TAG, "No esta autorizado");
            coordenadas[0] = -1.0;
            coordenadas[1]  = -1.0;
        }
        return coordenadas;
    }
    private double[] getGPS() {
        LocationManager lm = (LocationManager) getSystemService(
                getApplicationContext().LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        Location l = null;
        try {
            for (int i = providers.size() - 1; i >= 0; i--) {
                l = lm.getLastKnownLocation(providers.get(i));
                if (l != null) break;
            }
        }
        catch(SecurityException e){
            Log.e(TAG, "No esta autorizado");
        }
        double[] gps = new double[2];
        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }

        return gps;
    }

}


