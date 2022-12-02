package com.example.proyecto_tarjeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Profile extends AppCompatActivity {

    String idU;
    TextView nombreperfil;
    BottomNavigationView nav_bottom;
    ImageView informacion_p, direccion, cerrar_sesion, regresar_main;
    LinearLayout change_pass, eliminar_cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        idU = getIntent().getStringExtra("idU");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nav_bottom = findViewById(R.id.bttom_nav);

        nombreperfil = findViewById(R.id.nombre_1_completo_perfil);
        informacion_p = findViewById(R.id.ingPI);
        direccion = findViewById(R.id.ingAdd);
        cerrar_sesion = findViewById(R.id.cerrarSesion);
        regresar_main = findViewById(R.id.regresar_home);
        change_pass = findViewById(R.id.change_pass);
        eliminar_cuenta = findViewById(R.id.ly_eliminar_cuenta);

        nav_bottom.setSelectedItemId(R.id.nav_perfil);
        nombreperfil.setText(idU);

        eliminar_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(Profile.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("¿Quieres eliminar tu cuenta?")
                        .setContentText("Nizi Card recién comienza, espera muchas sorpresas más en un futuro muy cercano")
                        .setConfirmText("Eliminar")
                        .setConfirmButtonBackgroundColor(Color.rgb(13, 182, 51))
                        .setCancelButtonBackgroundColor(Color.rgb(182, 13, 13))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog
                                        .setTitleText("Cuenta eliminada")
                                        .setContentText("Esperamos que regreses pronto")
                                        .setConfirmText("OK")
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                        eliminarCuenta("https://nizi.red-utz.com/eliminar_cuenta_usuario.php");
                                        Intent intent = new Intent(Profile.this, Login.class);
                                        startActivity(intent);
                                }

                        })
                        .setCancelButton("Cancelar", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        nav_bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_out_right);
                        return true;
                    case R.id.nav_movimientos:
                        startActivity(new Intent(getApplicationContext(),Movimientos.class));
                        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_out_right);
                        return true;
                    case R.id.nav_tarjeta:
                        startActivity(new Intent(getApplicationContext(),Tarjeta.class));
                        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_out_right);
                        return true;
                    case R.id.nav_perfil:
                        return true;
                }
                return false;
            }
        });

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Change_Password.class);
                startActivity(intent);
            }
        });

        informacion_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Personal_Information.class);
                intent.putExtra("idU", idU);
                startActivity(intent);
            }
        });

        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Domicilio.class);
                startActivity(intent);
            }
        });

        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(Profile.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("¿Quieres cerrar tu sesión?")
                        .setContentText("Recuerda que para acceder a tu cuenta deberás iniciar sesión nuevamente")
                        .setConfirmText("Cerrar")
                        .setConfirmButtonBackgroundColor(Color.rgb(13, 182, 51))
                        .setCancelButtonBackgroundColor(Color.rgb(182, 13, 13))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent intent = new Intent(Profile.this, Login.class);
                                startActivity(intent);
                            }
                        })
                        .setCancelButton("Mantener", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        regresar_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Home.class);
                startActivity(intent);
            }
        });
    }

    private void eliminarCuenta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    new SweetAlertDialog(Profile.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Tu cuenta ha sido eliminada")
                            .show();
                }else{
                    new SweetAlertDialog(Profile.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("No fue posible eliminar tu cuenta, intentalo de nuevo más tarde")
                            .show();
                }
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
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}