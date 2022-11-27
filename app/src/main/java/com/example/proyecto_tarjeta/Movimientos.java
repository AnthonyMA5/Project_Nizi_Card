package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Movimientos extends AppCompatActivity {

    ImageView btnatras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);

        btnatras = findViewById(R.id.btnAtrasMovimientos);

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Movimientos.this, Home.class);
                startActivity(intent);
            }
        });

    }
}