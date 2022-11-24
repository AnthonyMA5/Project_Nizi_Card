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
        float zoomLevel = 16.0f;
        LatLng coffewin = new LatLng(20.4824158,-103.5331226);
        gMap.addMarker(new MarkerOptions().position(coffewin).title("Coffee Win"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coffewin, zoomLevel));
    }
}