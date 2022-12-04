package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Forget_Password extends AppCompatActivity {

    ImageView cancelar;
    EditText correo;
    Button recuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        cancelar = findViewById(R.id.btnCancelar);
        correo = findViewById(R.id.email_rec_pass);
        recuperar = findViewById(R.id.btn_recup_contra);

        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!correo.getText().toString().isEmpty()) {

                }else{
                    new SweetAlertDialog(Forget_Password.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("El correo electrónico no puede estar vacío")
                            .setConfirmButtonBackgroundColor(Color.parseColor("#100DE5"))
                            .show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forget_Password.this, Login.class);
                startActivity(intent);
            }
        });
    }
}