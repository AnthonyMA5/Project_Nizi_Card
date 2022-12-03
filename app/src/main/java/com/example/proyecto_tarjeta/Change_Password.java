package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Change_Password extends AppCompatActivity {

    String idU;
    ImageView btnRegresarHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        idU = getIntent().getStringExtra("idU");
        btnRegresarHome = findViewById(R.id.regresar_home_cp);

        btnRegresarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Change_Password.this, Profile.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });
    }
}