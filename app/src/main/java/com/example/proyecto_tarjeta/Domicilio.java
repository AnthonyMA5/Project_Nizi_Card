package com.example.proyecto_tarjeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Domicilio extends AppCompatActivity {

    String idU;
    Button actDir;
    ImageView regresar;
    EditText calle, num_ex, num_in, cp, colonia, ciudad, estado, pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilio);

        idU = getIntent().getStringExtra("idU");
        direccionUsuario("https://nizi.red-utz.com/informacion_usuario.php?idU="+idU+"");
        regresar = findViewById(R.id.btnAtrasAddress);
        calle = findViewById(R.id.calle_address);
        num_ex = findViewById(R.id.numext_address);
        num_in = findViewById(R.id.numint_address);
        cp = findViewById(R.id.cp_address);
        colonia = findViewById(R.id.colonia_address);
        ciudad = findViewById(R.id.ciudad_address);
        estado = findViewById(R.id.estado_address);
        pais = findViewById(R.id.pais_address);
        actDir = findViewById(R.id.act_direccion);

        actDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDireccion("https://nizi.red-utz.com/actualizar_direccion_usuario.php");
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Domicilio.this, Profile.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });
    }

    private void actualizarDireccion(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new SweetAlertDialog(Domicilio.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Dirección actualizada correctamente")
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
                parametros.put("municipio", ciudad.getText().toString());
                parametros.put("estado", estado.getText().toString());
                parametros.put("cp", cp.getText().toString());
                parametros.put("colonia", colonia.getText().toString());
                parametros.put("num_ext", num_ex.getText().toString());
                parametros.put("num_int", num_in.getText().toString());
                parametros.put("calle", calle.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void direccionUsuario(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        calle.setText(jsonObject.getString("calle"));
                        num_ex.setText(jsonObject.getString("num_ext"));
                        num_in.setText(jsonObject.getString("num_int"));
                        cp.setText(jsonObject.getString("c_p_codigo"));
                        colonia.setText(jsonObject.getString("colonia"));
                        //ciudad.setText(jsonObject.getString("calle"));
                        //estado.setText(jsonObject.getString("calle"));
                        //pais.setText(jsonObject.getString("calle"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}