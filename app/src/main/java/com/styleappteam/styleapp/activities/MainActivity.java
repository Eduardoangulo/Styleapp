package com.styleappteam.styleapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.styleappteam.styleapp.R;
import com.styleappteam.styleapp.connection_service.API_Connection;
import com.styleappteam.styleapp.fragments.fragments_ajustes.Ajustes;
import com.styleappteam.styleapp.fragments.fragments_compartir.Compartir;
import com.styleappteam.styleapp.fragments.fragments_perfil.Miperfil;
import com.styleappteam.styleapp.fragments.fragments_mis_servicios.Misservicios;
import com.styleappteam.styleapp.fragments.fragments_principal.Principal;
import com.styleappteam.styleapp.fragments.fragments_promociones.Promociones;
import static com.styleappteam.styleapp.VariablesGlobales.TAG;
import static com.styleappteam.styleapp.VariablesGlobales.URL_desarrollo;
import static com.styleappteam.styleapp.VariablesGlobales.conexion;
import static com.styleappteam.styleapp.VariablesGlobales.currentClient;

public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = new ProgressDialog(this);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(currentClient==null){
            finish();
            goLoginScreen();
        }

        conexion= new API_Connection(getApplicationContext(), TAG, URL_desarrollo);
        //conexion.retrofitLoad();
        Log.i(TAG, "Token2: "+AccessToken.getCurrentAccessToken());
        Log.i(TAG, "MainActivity");


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
                            case R.id.menu_seccion_4:
                                OcultarFragment1();
                                cleanStack();
                                fragment = new Promociones();
                                fragmentTransaction = true;
                                break;
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
                                goLoginScreen();
                                progress.hide();
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

}
