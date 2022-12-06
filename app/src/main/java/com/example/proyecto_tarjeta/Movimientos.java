package com.example.proyecto_tarjeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Movimientos extends AppCompatActivity {

    String idU;
    BottomNavigationView nav_bottom;
    ImageView btnatras;

    //Apartado para las listas
    ListView listView;
    Adapter adapter;
    public static ArrayList<Info_Movimientos>infoMovArrayList = new ArrayList<>();
    Info_Movimientos info_movimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);

        listView = (ListView)findViewById(R.id.movimientos_usuario);
        adapter = new ListAdapter(this,infoMovArrayList);
        listView.setAdapter((android.widget.ListAdapter) adapter);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        movimientosUsuario("https://nizi.red-utz.com/movimientos_usuario.php?idU="+idU+"");

        idU = getIntent().getStringExtra("idU");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nav_bottom = findViewById(R.id.bttom_nav);

        btnatras = findViewById(R.id.btnAtrasMovimientos);

        nav_bottom.setSelectedItemId(R.id.nav_movimientos);

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

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Movimientos.this, Home.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });

    }

    private void movimientosUsuario(String URL) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos_mov");

                            if(exito.equals("1")){
                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String tipo = object.getString("tipo");
                                    String fecha = object.getString("fecha");
                                    String hora = object.getString("hora");
                                    int monto = Integer.parseInt(object.getString("monto"));


                                    info_movimientos = new Info_Movimientos(monto,tipo,fecha,hora);
                                    infoMovArrayList.add(info_movimientos);
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Movimientos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}