package com.example.proyecto_tarjeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Tarjeta extends AppCompatActivity {

    String idU;
    BottomNavigationView nav_bottom;
    ImageView regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        idU = getIntent().getStringExtra("idU");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nav_bottom = findViewById(R.id.bttom_nav);
        regresar = findViewById(R.id.regresar_home_tarjeta);

        nav_bottom.setSelectedItemId(R.id.nav_tarjeta);

        nav_bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        intent.putExtra("idU", idU);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_out_right);
                        return true;
                    case R.id.nav_movimientos:
                        Intent intent1 = new Intent(getApplicationContext(), Movimientos.class);
                        intent1.putExtra("idU", idU);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_out_right);
                        return true;
                    case R.id.nav_tarjeta:
                        return true;
                    case R.id.nav_perfil:
                        Intent intent2 = new Intent(getApplicationContext(), Profile.class);
                        intent2.putExtra("idU", idU);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_out_left);
                        return true;
                }
                return false;
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tarjeta.this, Home.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });
    }
}