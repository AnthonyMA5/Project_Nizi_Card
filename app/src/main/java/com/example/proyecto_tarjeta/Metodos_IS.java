package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Metodos_IS extends AppCompatActivity {

    ImageView abrir_paypal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodos_is);

        abrir_paypal = findViewById(R.id.abrir_paypal);

        abrir_paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Metodos_IS.this, Ingresar_Saldo.class);
                startActivity(intent);
            }
        });
    }
}