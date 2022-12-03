package com.example.proyecto_tarjeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Tarjeta extends AppCompatActivity {

    String idU;
    TextView t_num, t_fecha;
    TextView nombre, numero, vigencia, cvv;
    BottomNavigationView nav_bottom;
    ImageView regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        idU = getIntent().getStringExtra("idU");
        datosTarjeta("https://nizi.red-utz.com/info_tarjeta_usuario.php?idU="+idU+"");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t_num = findViewById(R.id.numTarjeta);
        t_fecha = findViewById(R.id.vigenciaTarjeta);

        nombre = findViewById(R.id.tarjeta_dueño_tarjeta);
        numero = findViewById(R.id.tarjeta_num_tarjeta);
        vigencia = findViewById(R.id.tarjeta_vigencia_tarjeta);
        cvv = findViewById(R.id.tarjeta_cvv_tarjeta);

        nav_bottom = findViewById(R.id.bttom_nav);
        regresar = findViewById(R.id.regresar_home_tarjeta);

        nav_bottom.setSelectedItemId(R.id.nav_tarjeta);

        nav_bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        intent.putExtra("idU", idU);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_out_right);
                        return true;
                    case R.id.nav_movimientos:
                        Intent intent1 = new Intent(getApplicationContext(), Movimientos.class);
                        intent1.putExtra("idU", idU);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_out_right);
                        return true;
                    case R.id.nav_tarjeta:
                        return true;
                    case R.id.nav_perfil:
                        Intent intent2 = new Intent(getApplicationContext(), Profile.class);
                        intent2.putExtra("idU", idU);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_out_left);
                        return true;
                }
                return false;
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tarjeta.this, Home.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });
    }

    private void datosTarjeta(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        t_num.setText(jsonObject.getString("num1")+" "+jsonObject.getString("num2")+" "+jsonObject.getString("num3")+" "+jsonObject.getString("num4"));
                        t_fecha.setText(jsonObject.getString("vigencia"));
                        nombre.setText(jsonObject.getString("nombre")+" "+jsonObject.getString("apellido"));
                        numero.setText(jsonObject.getString("num1")+" "+jsonObject.getString("num2")+" "+jsonObject.getString("num3")+" "+jsonObject.getString("num4"));
                        vigencia.setText(jsonObject.getString("vigencia"));
                        cvv.setText(jsonObject.getString("cuv"));
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