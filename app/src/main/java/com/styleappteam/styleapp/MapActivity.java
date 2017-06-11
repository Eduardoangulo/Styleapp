package com.styleappteam.styleapp;

/**
 * Created by eduardo on 6/11/17.
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements  OnMapReadyCallback, GoogleMap.OnCameraChangeListener, PlaceSelectionListener{

    private PlaceAutocompleteFragment autocompleteFragment;
    private static final int INITIAL_ZOOM_LEVEL = 14;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private int PLACE_PICKER_REQUEST = 1;
    private GoogleMap mapa;
    private static final String TAG = MapActivity.class.getName();

    private String direccion_elegida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        try
        {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            rlp.setMargins(0, 0, 10, 10);

            // Retrieve the PlaceAutocompleteFragment.
             autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

            autocompleteFragment.setOnPlaceSelectedListener(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onPlaceSelected(Place place) {
        // TODO: Get info about the selected place.
        Log.i(TAG, "Place: " + place.getName());
        autocompleteFragment.setText(place.getAddress()); //Se muestra direccion elegida

        addMarkerToMap(place.getLatLng());//Se marca en el mapa el lugar elegido
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), INITIAL_ZOOM_LEVEL));

        direccion_elegida=place.getAddress().toString(); // Se almacena la direccion
    }

    @Override
    public void onError(Status status) {
        // TODO: Handle the error.
        Log.i(TAG, "An error occurred: " + status);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                System.out.println(place.getName());

                String toastMsg = String.format("Place: %s", place.getAddress());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                onPlaceSelected(place);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                System.out.println(status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapa=map;
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.0533671, -77.08557689999998), INITIAL_ZOOM_LEVEL));
        mapa.setOnCameraChangeListener(this);

        //My location
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){
                @Override
                public boolean onMyLocationButtonClick()
                {
                    //TODO: Any custom actions
                    try {

                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        startActivityForResult(builder.build(MapActivity.this), PLACE_PICKER_REQUEST);

                    } catch (GooglePlayServicesRepairableException e) {
                        // TODO: Handle the error.
                    } catch (GooglePlayServicesNotAvailableException e) {
                        // TODO: Handle the error.
                    }
                    return false;
                }
            });
        } else {
            // Show rationale and request permission.
        }

    }
    public void addMarkerToMap(LatLng latLng) {
        mapa.addMarker(new MarkerOptions().position(latLng).title("title").snippet("snippet"));
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_burro))
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        // Update the search criteria for this geoQuery and the circle on the map
        LatLng center = cameraPosition.target;

    }

}