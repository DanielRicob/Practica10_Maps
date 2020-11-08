package ud.example.mimapa;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

private GoogleMap mMap;
private final LatLng SAN = new LatLng(4.9229996, -74.4469378);
private LocationManager locationManager;
private LocationListener locationListener;
private LatLng miUbicacion;
private Spinner spinner;
private CheckBox checkBox;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        spinner=(Spinner)findViewById(R.id.spinner);
        checkBox= (CheckBox)findViewById(R.id.checkBox);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        String[] Mapas ={"Hibrido","Terreno","Satelite","Normal"};
        ArrayAdapter <String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Mapas);
        spinner.setAdapter(Adapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        locationManager = (LocationManager)
        this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
@Override
public void onLocationChanged(@NonNull Location location) {
        try {
        miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception Ex) {
        }

        }
        };
        RevisarPermisos();
        }


/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 */
@Override
public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener((GoogleMap.OnMapClickListener)this);

        }
//Mapas
public void Aplicar(View v) {
        String Seleccion = spinner.getSelectedItem().toString();
        if (Seleccion.equals("Normal")) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);}
        if (Seleccion.equals("Satelite")) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);}
        if (Seleccion.equals("Hibrido")) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);}
        if (Seleccion.equals("Terreno")) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN); }


        }

@Override
public void onMapClick(LatLng LatLng) {
        mMap.addMarker(new MarkerOptions().position(LatLng));}

public  void  itemClicked ( View v ) {
        CheckBox checkBox = ( CheckBox ) v;
        if (checkBox . isChecked ()) {
        LatLng TempLatLng = mMap.getCameraPosition().target;
        mMap.addMarker(new MarkerOptions()
        .position(TempLatLng)
        .icon(BitmapDescriptorFactory.defaultMarker
        (BitmapDescriptorFactory.HUE_ORANGE)));
        }
        }


public void IrParaiso(View v) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SAN, 15));
        }

public void irUD(View v) {
        LatLng UD = new LatLng(4.6281058, -74.065999);
        Marker miMarker = mMap.addMarker(new MarkerOptions()
        .position(UD)
        .title("UD")
        .snippet("Universidad Distrital FJC"));
        miMarker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UD, 17));
        }

public void limpiar(View v) {
        mMap.clear();
        }
public void IraMiUbicacion(View v) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion, 19));
        }

    /*public void addMarker(View v) {
        LatLng TempLatLng = mMap.getCameraPosition().target;
        mMap.addMarker(new MarkerOptions()
                .position(TempLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_ORANGE))
        );
    }*/

    protected void RevisarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission
                            (this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 101);
            } else {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

}