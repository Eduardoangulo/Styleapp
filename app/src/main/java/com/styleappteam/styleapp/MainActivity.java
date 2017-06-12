package com.styleappteam.styleapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.styleappteam.styleapp.classes.Service;
import com.styleappteam.styleapp.classes.service_adapter;
import com.styleappteam.styleapp.fragments.Ajustes;
import com.styleappteam.styleapp.fragments.Compartir;
import com.styleappteam.styleapp.fragments.Miperfil;
import com.styleappteam.styleapp.fragments.Misservicios;
import com.styleappteam.styleapp.fragments.Principal;
import com.styleappteam.styleapp.fragments.Promociones;


public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(AccessToken.getCurrentAccessToken()==null){
            goLoginScreen();
        }
        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
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
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                OcultarFragment1();
                                fragment = new Miperfil();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_3:
                                OcultarFragment1();
                                fragment = new Misservicios();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_4:
                                OcultarFragment1();
                                fragment = new Promociones();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_5:
                                OcultarFragment1();
                                fragment = new Compartir();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_6:
                                OcultarFragment1();
                                fragment = new Ajustes();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_7:
                                OcultarFragment1();
                                sendReport();
                                break;
                            case R.id.menu_seccion_8:
                                OcultarFragment1();
                                LoginManager.getInstance().logOut();
                                goLoginScreen();
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

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
    */
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
}
