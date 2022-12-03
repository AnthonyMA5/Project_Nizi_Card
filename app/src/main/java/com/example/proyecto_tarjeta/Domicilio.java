package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Domicilio extends AppCompatActivity {

    String idU;
    ImageView regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilio);

        idU = getIntent().getStringExtra("idU");
        regresar = findViewById(R.id.btnAtrasAddress);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Domicilio.this, Profile.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });
    }
}