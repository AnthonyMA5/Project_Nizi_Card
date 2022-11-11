package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Registro extends AppCompatActivity {

    private boolean passwordShowing = false;
    private boolean Check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.gilroy_medium);
        final Button sign_up = findViewById(R.id.btnSignUp);
        final TextView signIn = findViewById(R.id.txtsignIn);
        final EditText password = findViewById(R.id.txt_password);
        final EditText email = findViewById(R.id.txt_email);
        final ImageView check_button = findViewById(R.id.unchecked_button);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);

        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verificamos si la contraseña se está mostrando o no
                if (Check){
                    Check = false;
                    check_button.setImageResource(R.drawable.uncheck_button);
                }else{
                    Check = true;
                    check_button.setImageResource(R.drawable.check_button);
                }
            }
        });

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

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String getEmailTxt = email.getText().toString();

                new SweetAlertDialog(Registro.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Cuenta creada")
                        .setContentText("Tu cuenta se ha creado con éxito")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener(){
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(Registro.this, OTP_Verification.class);

                                intent.putExtra("email", getEmailTxt);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registro.this, Login.class));
            }
        });

    }
}