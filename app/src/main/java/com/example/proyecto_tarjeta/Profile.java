package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Profile extends AppCompatActivity {

    ImageView informacion_p, direccion, cerrar_sesion, regresar_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        informacion_p = findViewById(R.id.ingPI);
        direccion = findViewById(R.id.ingAdd);
        cerrar_sesion = findViewById(R.id.cerrarSesion);
        regresar_main = findViewById(R.id.regresar_home);

        informacion_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Personal_Information.class);
                startActivity(intent);
            }
        });

        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Domicilio.class);
                startActivity(intent);
            }
        });

        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(Profile.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("¿Quieres cerrar tu sesión?")
                        .setContentText("Recuerda que para acceder a tu cuenta deberás iniciar sesión nuevamente")
                        .setConfirmText("Cerrar")
                        .setConfirmButtonBackgroundColor(Color.rgb(13, 182, 51))
                        .setCancelButtonBackgroundColor(Color.rgb(182, 13, 13))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent intent = new Intent(Profile.this, Login.class);
                                startActivity(intent);
                            }
                        })
                        .setCancelButton("Mantener", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        regresar_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Home.class);
                startActivity(intent);
            }
        });
    }
}