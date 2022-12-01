package com.example.proyecto_tarjeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {

    BottomNavigationView nav_bottom;
    ImageView access_profile, logout_home;
    TextView bienvenida, nom_tarjeta;
    CardView ingreso_dinero, mi_tarjeta, movimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nom_tarjeta = findViewById(R.id.dueñoCuenta);
        nav_bottom = findViewById(R.id.bttom_nav);
        access_profile = findViewById(R.id.img_profile);
        logout_home = findViewById(R.id.btn_logout_home);
        bienvenida = findViewById(R.id.txt_bienvenida);
        ingreso_dinero = findViewById(R.id.ingresarDinero);
        mi_tarjeta = findViewById(R.id.miTarjeta);
        movimientos = findViewById(R.id.verMovimientos);



        nav_bottom.setSelectedItemId(R.id.nav_home);

        nav_bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_movimientos:
                        startActivity(new Intent(getApplicationContext(),Movimientos.class));
                        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_out_left);
                        return true;
                    case R.id.nav_tarjeta:
                        startActivity(new Intent(getApplicationContext(),Tarjeta.class));
                        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_out_left);
                        return true;
                    case R.id.nav_perfil:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_out_left);
                        return true;
                }
                return false;
            }
        });

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