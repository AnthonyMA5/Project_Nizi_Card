package com.example.proyecto_tarjeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Establecimiento_Mapa extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap gMap;
    ImageView regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecimiento_mapa);

        regresar = findViewById(R.id.regresar_establecimientos);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Establecimiento_Mapa.this, Metodos_IS.class);
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng p_medio = new LatLng(20.509004, -103.487034);
        float zoomLevel = 11.5f;
        LatLng coffewin = new LatLng(20.4824158,-103.5331226);
        LatLng mariscos_h = new LatLng(20.53114983980939, -103.42407765637111);
        gMap.addMarker(new MarkerOptions().position(coffewin).title("Coffee Win"));
        gMap.addMarker(new MarkerOptions().position(mariscos_h).title("Mariscos La Herradura"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p_medio, zoomLevel));
    }
}