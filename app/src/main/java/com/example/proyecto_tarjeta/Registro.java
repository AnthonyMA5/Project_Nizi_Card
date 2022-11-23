package com.example.proyecto_tarjeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Registro extends AppCompatActivity {

    private boolean passwordShowing = false;
    private boolean Check = false;


    EditText nombre, apellidos, telefono, correo, contrasena, nombre_usuario;
    String nombre_su, apellidos_su, telefono_su, correo_su, contrasena_su, username_su;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.txt_name);
        apellidos = findViewById(R.id.txt_lastname);
        telefono = findViewById(R.id.txt_telephone);
        correo = findViewById(R.id.txt_email);
        contrasena = findViewById(R.id.txt_password);
        nombre_usuario = findViewById(R.id.txt_username);

        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.gilroy_medium);
        final Button sign_up = findViewById(R.id.btnSignUp);
        final TextView signIn = findViewById(R.id.txtsignIn);
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
                    contrasena.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_show);
                    //Tomando y asignando el tipo de letra al input
                    contrasena.setTypeface(typeface);
                }else{
                    passwordShowing = true;
                    contrasena.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_hide);
                    //Tomando y asignando el tipo de letra al input
                    contrasena.setTypeface(typeface);
                }
                //Mueve el cursor a la última parte del texto
                contrasena.setSelection(contrasena.length());
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre_su = nombre.getText().toString();
                apellidos_su = apellidos.getText().toString();
                telefono_su = telefono.getText().toString();
                correo_su= correo.getText().toString();
                contrasena_su = contrasena.getText().toString();
                username_su = nombre_usuario.getText().toString();

                if (!nombre_su.isEmpty() && !apellidos_su.isEmpty() && !telefono_su.isEmpty() && !correo_su.isEmpty() && !username_su.isEmpty() && !contrasena_su.isEmpty()){
                    if (Check != true){
                        new SweetAlertDialog(Registro.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Es necesario que aceptes los Términos y Condiciones")
                                .show();
                    }else{
                        ejecutarServicio("https://nizi.red-utz.com/insertar_usuario.php");
                    }
                }else{
                    new SweetAlertDialog(Registro.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Completa todos los campos para continuar")
                            .setConfirmButtonBackgroundColor(Color.parseColor("#100DE5"))
                            .show();
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registro.this, Login.class));
            }
        });
    }

    private void ejecutarServicio (String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final String getEmailTxt = correo.getText().toString();

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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se logró", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", nombre.getText().toString());
                parametros.put("apellidos", apellidos.getText().toString());
                parametros.put("correo", correo.getText().toString());
                parametros.put("telefono", telefono.getText().toString());
                parametros.put("username", nombre_usuario.getText().toString());
                parametros.put("contrasena", contrasena.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}