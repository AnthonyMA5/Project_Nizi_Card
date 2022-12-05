package com.example.proyecto_tarjeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Home extends AppCompatActivity {

    String idU;
    BottomNavigationView nav_bottom;
    ImageView logout_home;
    RelativeLayout access_profile;
    TextView bienvenida, nom_tarjeta, saldo_tarjeta, saldo_cuenta, iniciales;
    CardView ingreso_dinero, mi_tarjeta, movimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        idU = getIntent().getStringExtra("idU");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        iniciales = findViewById(R.id.inicialesUsuario);
        nom_tarjeta = findViewById(R.id.dueñoCuenta);
        nav_bottom = findViewById(R.id.bttom_nav);
        access_profile = findViewById(R.id.img_profile);
        logout_home = findViewById(R.id.btn_logout_home);
        bienvenida = findViewById(R.id.txt_bienvenida);
        saldo_tarjeta = findViewById(R.id.dineroTarjeta);
        saldo_cuenta = findViewById(R.id.dineroCuenta);
        ingreso_dinero = findViewById(R.id.ingresarDinero);
        mi_tarjeta = findViewById(R.id.miTarjeta);
        movimientos = findViewById(R.id.verMovimientos);

        nav_bottom.setSelectedItemId(R.id.nav_home);

        datosUsuario("https://nizi.red-utz.com/informacion_usuario.php?idU="+idU+"");
        inicialesUsuario("https://nizi.red-utz.com/iniciales_usuario.php?idU="+idU+"");

        nav_bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_movimientos:
                        Intent intent = new Intent(getApplicationContext(), Movimientos.class);
                        intent.putExtra("idU", idU);
                        startActivity(intent);
                        return true;
                    case R.id.nav_tarjeta:
                        Intent intent1 = new Intent(getApplicationContext(), Tarjeta.class);
                        intent1.putExtra("idU", idU);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_out_left);
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

        movimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Movimientos.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });

        access_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Profile.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });

        logout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        ingreso_dinero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Metodos_IS.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });

        mi_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Tarjeta.class);
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

    private void datosUsuario(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        nom_tarjeta.setText(jsonObject.getString("nombre")+" "+jsonObject.getString("apellido"));
                        bienvenida.setText("¡Hola, "+jsonObject.getString("nombre")+"!");
                        if (jsonObject.getString("saldo").equals("0")){
                            saldo_tarjeta.setText("$"+jsonObject.getString("saldo")+".00");
                            saldo_cuenta.setText("$"+jsonObject.getString("saldo")+".00");
                        }else{
                            saldo_tarjeta.setText("$"+jsonObject.getString("saldo"));
                            saldo_cuenta.setText("$"+jsonObject.getString("saldo"));
                        }

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



    public void logout() {
        SessionManagement sessionManagement = new SessionManagement(Home.this);
        sessionManagement.removeSession();
        
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(Home.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}