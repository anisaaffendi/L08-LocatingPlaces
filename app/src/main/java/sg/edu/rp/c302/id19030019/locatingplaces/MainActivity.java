package sg.edu.rp.c302.id19030019.locatingplaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button btnEast, btnNorth, btnCentral;
    private GoogleMap map;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        btnEast = findViewById(R.id.btn3);
        btnNorth = findViewById(R.id.btn1);
        btnCentral = findViewById(R.id.btn2);
        spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    if (map != null) {
                        LatLng poi_north = new LatLng(1.37108, 103.87869);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north, 20));
                    }
                } else if (i == 1) {
                    if (map != null) {
                        LatLng poi_central = new LatLng(1.31782, 103.89907);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 15));
                    }
                } else if (i == 2) {
                    if (map != null) {
                        LatLng poi_east = new LatLng(1.36833, 103.9951514);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 15));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> places = new ArrayList<String>();
        places.add("North");
        places.add("Central");
        places.add("East");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, places);

        spinner.setAdapter(arrayAdapter);

        btnEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map != null){
                    LatLng poi_east = new LatLng(1.36833, 103.9951514);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 15));
                }
            }
        });

        btnCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map != null){
                    LatLng poi_central = new LatLng(1.31782, 103.89907);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 15));
                }
            }
        });

        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map != null){
                    LatLng poi_north = new LatLng(1.37108, 103.87869);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north, 15));
                }
            }
        });

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                map = googleMap;

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);

                LatLng poi_SG = new LatLng(1.352083, 103.819839);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_SG, 15));

                LatLng poi_east = new LatLng(1.36833, 103.9951514);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 10));
                Marker east = map.addMarker(new MarkerOptions().position(poi_east).title("HQ - East").snippet("Blk 555, Tampines Ave 3, 28788 \n" + "Operating hours: 9am-5pm \n" + "Tel: 66776677 \n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                LatLng poi_central = new LatLng(1.31782, 103.89907);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 10));
                Marker central = map.addMarker(new MarkerOptions().position(poi_central).title("HQ - Central").snippet("Blk 3A, Orchard Ave 3, 134542 \n" + "Operating hours: 11am-8pm \n" + "Tel: 67788652 \n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                LatLng poi_north = new LatLng(1.37108, 103.87869);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north, 10));
                Marker north = map.addMarker(new MarkerOptions().position(poi_north).title("HQ - North").snippet("Blk 638, Hougang Ave 8, 765654 \n" + "Operating hours: 10am-5pm \n" + "Tel: 65433456 \n").icon(BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on)));

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 1) {
            if (map != null) {
                LatLng poi_north = new LatLng(1.37108, 103.87869);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north, 15));
            }
        } else if (i == 2) {
            if (map != null) {
                LatLng poi_central = new LatLng(1.31782, 103.89907);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central, 15));
            }
        } else if (i == 3) {
            if (map != null) {
                LatLng poi_east = new LatLng(1.36833, 103.9951514);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east, 15));
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}