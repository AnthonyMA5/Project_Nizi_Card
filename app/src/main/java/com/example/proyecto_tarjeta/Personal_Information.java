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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Personal_Information extends AppCompatActivity {

    String idU;
    EditText nombre, apellido, telefono, correo, username;
    ImageView regresar;
    TextView iniciales;
    TextView nombre_c, correo_c;
    Button actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        idU = getIntent().getStringExtra("idU");
        regresar = findViewById(R.id.btnAtrasPerfil);

        inicialesUsuario("https://nizi.red-utz.com/iniciales_usuario.php?idU="+idU+"");

        iniciales = findViewById(R.id.inicialesUsuario_profile_info);
        correo_c = findViewById(R.id.correo_perfil_info);
        nombre_c = findViewById(R.id.nombre_completo_perfil);
        nombre = findViewById(R.id.name_profile);
        apellido = findViewById(R.id.lastname_profile);
        telefono = findViewById(R.id.number_profile);
        correo = findViewById(R.id.email_profile);
        username = findViewById(R.id.username_profile);
        actualizar = findViewById(R.id.btn_act_datos);


        datosUsuario("https://nizi.red-utz.com/informacion_usuario.php?idU="+idU+"");
        
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos("https://nizi.red-utz.com/actualizar_info_usuario.php");
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Personal_Information.this, Profile.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });
    }

    private void inicialesUsuario(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        iniciales.setText(jsonObject.getString("Nombre")+jsonObject.getString("Apellido"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void actualizarDatos(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new SweetAlertDialog(Personal_Information.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Datos actualizados correctamente")
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
                parametros.put("nombre", nombre.getText().toString());
                parametros.put("apellidos", apellido.getText().toString());
                parametros.put("telefono", telefono.getText().toString());
                parametros.put("correo", correo.getText().toString());
                parametros.put("username", username.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void datosUsuario(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        nombre.setText(jsonObject.getString("nombre"));
                        apellido.setText(jsonObject.getString("apellido"));
                        telefono.setText(jsonObject.getString("telefono"));
                        correo.setText(jsonObject.getString("correo"));
                        username.setText(jsonObject.getString("username"));
                        nombre_c.setText(jsonObject.getString("nombre")+" "+jsonObject.getString("apellido"));
                        correo_c.setText(jsonObject.getString("correo"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}