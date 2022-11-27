package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    ImageView access_profile, logout_home;
    TextView bienvenida;
    CardView ingreso_dinero, mi_tarjeta, movimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        access_profile = findViewById(R.id.img_profile);
        logout_home = findViewById(R.id.btn_logout_home);
        bienvenida = findViewById(R.id.txt_bienvenida);
        ingreso_dinero = findViewById(R.id.ingresarDinero);
        mi_tarjeta = findViewById(R.id.miTarjeta);
        movimientos = findViewById(R.id.verMovimientos);

        movimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Movimientos.class);
                startActivity(intent);
            }
        });

        access_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Profile.class);
                startActivity(intent);
            }
        });

        logout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        ingreso_dinero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Metodos_IS.class);
                startActivity(intent);
            }
        });

        mi_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Tarjeta.class);
                startActivity(intent);
            }
        });
    }

    public void logout() {
        SessionManagement sessionManagement = new SessionManagement(Home.this);
        sessionManagement.removeSession();
        
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(Home.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}