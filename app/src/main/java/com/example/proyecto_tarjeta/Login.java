package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import cn.pedant.SweetAlert.SweetAlertDialog;
import android.graphics.Color;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private boolean passwordShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.gilroy_medium);
        final EditText username = findViewById(R.id.txt_username);
        final EditText password = findViewById(R.id.txt_password);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final TextView signUp = findViewById(R.id.signUp);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verificamos si la contraseña se está mostrando o no
                if (passwordShowing){
                    passwordShowing = false;
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_show);
                    //Tomando y asignando el tipo de letra al input
                    password.setTypeface(typeface);
                }else{
                    passwordShowing = true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_hide);
                    //Tomando y asignando el tipo de letra al input
                    password.setTypeface(typeface);
                }
                //Mueve el cursor a la última parte del texto
                password.setSelection(password.length());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });
    }
}