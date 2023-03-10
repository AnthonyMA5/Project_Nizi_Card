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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
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
    TextView ejemplo, forget_pass;
    String usuario, contra;

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
        ejemplo = findViewById(R.id.textView);
        forget_pass = findViewById(R.id.forgetPass);

        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Forget_Password.class));
            }
        });

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Verificamos si la contrase??a se est?? mostrando o no
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
                //Mueve el cursor a la ??ltima parte del texto
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
                    validarUsuario("https://nizi.red-utz.com/login_usuarios.php");
                }else{
                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Completa todos los campos para continuar")
                            .show();
                }
            }
        });

    }


    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    try {
                        JSONArray informacion = new JSONArray(new String(response));
                        String id = informacion.getJSONObject(0).getString("idusuario");
                        Intent intent = new Intent(Login.this, Home.class);
                        intent.putExtra("idU", id);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Nombre de usuario y/o contrase??a incorrectos")
                            .setConfirmButtonBackgroundColor(Color.parseColor("#100DE5"))
                            .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(error.toString())
                        .setConfirmButtonBackgroundColor(Color.parseColor("#100DE5"))
                        .show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
        startActivity(intent);
    }


}