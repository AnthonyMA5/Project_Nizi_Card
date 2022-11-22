package com.example.proyecto_tarjeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import cn.pedant.SweetAlert.SweetAlertDialog;
import android.graphics.Color;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    RequestQueue requestQueue;
    EditText username, password;
    Button btn_login;
    String usuario, contra;
    User user = new User();

    private boolean passwordShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.gilroy_medium);

        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final TextView signUp = findViewById(R.id.signUp);
        btn_login = findViewById(R.id.btn_login);
        username = findViewById(R.id.txt_log_username);
        password = findViewById(R.id.txt_log_password);


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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = username.getText().toString();
                contra = password.getText().toString();
                if (!usuario.isEmpty() && !contra.isEmpty()){
                    validarUsuario("https://nizi.red-utz.com/validar_usuario.php");
                }else{
                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Completa todos los campos para continuar")
                            .show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }

    private void checkSession() {
        //Check if user is logged in
        //If user is logged in --> moved to Home Activity

        SessionManagement sessionManagement = new SessionManagement(Login.this);
        int userID = sessionManagement.getSession();

        if (userID != -1){
            //User ID logged in and move to Home Activity
            moveToHomeActivity();
        }else{
            //Do nothing
        }
    }

    private void moveToHomeActivity() {
        Intent intent = new Intent(Login.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("datos",user);
        startActivity(intent);
    }

    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    obtenerDatosUsuario("https://nizi.red-utz.com/busqueda_datos_usuario.php?username="+username.getText()+"&contrasena="+password.getText()+"");
                    User info_usuario = new User();
                    SessionManagement sessionManagement = new SessionManagement(Login.this);
                    sessionManagement.saveSession(info_usuario);
                    moveToHomeActivity();
                }else{
                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Datos incorrectos")
                            .setContentText("Nombre de usuario y/o contraseña incorrectos")
                            .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("username", username.getText().toString());
                parametros.put("contrasena", password.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void obtenerDatosUsuario(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        user.setId(jsonObject.getInt("idusuario"));
                        user.setNombre(jsonObject.getString("nombre"));
                        user.setApellidos(jsonObject.getString("apellido"));
                        user.setCorreo(jsonObject.getString("correo"));
                        user.setTelefono(jsonObject.getString("telefono"));
                        user.setUsername(jsonObject.getString("username"));
                        user.setContrasena(jsonObject.getString("contrasena"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error de conexión")
                        .setContentText("Verifica tu conexión a internet o intentalo más tarde")
                        .show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


}