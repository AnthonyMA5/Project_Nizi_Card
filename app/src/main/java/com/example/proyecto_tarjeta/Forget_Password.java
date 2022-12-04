package com.example.proyecto_tarjeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Forget_Password extends AppCompatActivity {

    ImageView cancelar;
    EditText correo;
    Button recuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        cancelar = findViewById(R.id.btnCancelar);
        correo = findViewById(R.id.email_rec_pass);
        recuperar = findViewById(R.id.btn_recup_contra);

        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!correo.getText().toString().isEmpty()) {
                    generarContrasena("https://nizi.red-utz.com/solicitar_contrasena.php");
                }else{
                    new SweetAlertDialog(Forget_Password.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("El correo electrónico no puede estar vacío")
                            .setConfirmButtonBackgroundColor(Color.parseColor("#100DE5"))
                            .show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forget_Password.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void generarContrasena(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String respuesta = jsonObject.getString("message");
                    if (respuesta.equals("Error correo")){
                        new SweetAlertDialog(Forget_Password.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("No existe una cuenta asociada al correo electrónico ingresado")
                                .setConfirmButtonBackgroundColor(Color.parseColor("#100DE5"))
                                .show();
                    }else if (respuesta.equals("Correcto")){
                        new SweetAlertDialog(Forget_Password.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Hemos enviado una contraseña temporal al correo electrónico que ingresaste")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener(){
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent(Forget_Password.this, Login.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", correo.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}