package com.example.proyecto_tarjeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Change_Password extends AppCompatActivity {

    String idU;
    String s_c_actual, s_c_nueva, s_c_c_nueva;
    EditText c_actual, c_nueva, c_c_nueva;
    Button actualizar_c;
    ImageView btnRegresarHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        idU = getIntent().getStringExtra("idU");
        btnRegresarHome = findViewById(R.id.regresar_home_cp);
        c_actual = findViewById(R.id.contra_actual);
        c_nueva = findViewById(R.id.nueva_contra);
        c_c_nueva = findViewById(R.id.c_nueva_contra);
        actualizar_c = findViewById(R.id.actualizar_contrasena);

        actualizar_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_c_actual = c_actual.getText().toString();
                s_c_nueva = c_nueva.getText().toString();
                s_c_c_nueva = c_c_nueva.getText().toString();

                if (!s_c_actual.isEmpty() && !s_c_nueva.isEmpty() && !s_c_c_nueva.isEmpty()) {
                    compararContrasena("https://nizi.red-utz.com/comparar_contrasena.php");
                }else{
                    new SweetAlertDialog(Change_Password.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Algunos campos se encuentran vacíos")
                            .show();
                }
            }
        });

        btnRegresarHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Change_Password.this, Profile.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });
    }

    private void compararContrasena(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    try {
                        JSONArray array = new JSONArray(new String(response));
                        String respuesta = array.getJSONObject(0).getString("username");
                        if (respuesta != null) {
                            if (s_c_nueva.equals(s_c_c_nueva)){
                                actualizarContrasena("https://nizi.red-utz.com/actualizar_contrasena.php");
                            }else{
                                new SweetAlertDialog(Change_Password.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Tu nueva contraseña no coincide en ambos campos")
                                        .show();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    new SweetAlertDialog(Change_Password.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Tu contraseña actual no es correcta")
                            .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new SweetAlertDialog(Change_Password.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(error.getMessage())
                        .show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("idU", idU);
                parametros.put("contra_actual", c_actual.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void actualizarContrasena(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new SweetAlertDialog(Change_Password.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Contraseña actualizada correctamente")
                        .show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idU", idU);
                parametros.put("n_contrasena", c_nueva.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}