package com.example.proyecto_tarjeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class OTP_Verification extends AppCompatActivity {

    private EditText otpEt1, otpEt2, otpEt3, otpEt4, otpEt5;
    private Button btnVerify;
    private TextView resendCode;
    String getEmail;
    String generateOtp;

    //Se volverá verdadero después de haber pasado 60 segundos
    private boolean resendEnabled = false;

    //Contar en segundos el tiempo para reenviar el código
    private int resendTime = 60;

    private int selectedETPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        otpEt1 = findViewById(R.id.OTP1);
        otpEt2 = findViewById(R.id.OTP2);
        otpEt3 = findViewById(R.id.OTP3);
        otpEt4 = findViewById(R.id.OTP4);
        otpEt5 = findViewById(R.id.OTP5);
        btnVerify = findViewById(R.id.btnVerificar);

        resendCode = findViewById(R.id.newCode);

        final TextView otpEmail = findViewById(R.id.otp_email);

        //Obtenemos el correo del Activity Registro

        getEmail = getIntent().getStringExtra("email");

        //Asignamos el email en el TextView

        otpEmail.setText(getEmail);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);
        otpEt5.addTextChangedListener(textWatcher);

        generateOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString()+otpEt5.getText().toString();

        //Abrir por defecto el teclado en el otpET1
        showKeyBoard(otpEt1);

        //Iniciamos el conteo regresivo para la solicitud de un nuevo código de activación
        startCountDownTimer();

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (resendEnabled){
                    //Iniciamos un nuevo conteo
                    startCountDownTimer();
                    generarNuevoCodigo("https://nizi.red-utz.com/generar_nuevo_codigo.php");
                }

            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString()+otpEt5.getText().toString();
                if (generateOtp.length()==5){
                    //Escribir el código para validar el OTP
                    validarOTP("https://nizi.red-utz.com/validar_codigo_otp.php");

                }else{
                    new SweetAlertDialog(OTP_Verification.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Código incompleto")
                            .setContentText("Coloca los 5 números del código de verificación para continuar")
                            .show();
                }
            }
        });

    }

    private void validarOTP(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String respuesta = jsonObject.getString("message");
                        if (respuesta.equals("Validación correcta")){

                        }else if(respuesta.equals("Validación incorrecta")){
                            new SweetAlertDialog(OTP_Verification.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Código no válido")
                                    .setContentText("Verifica que hayas introducido correctamente el código o genera uno nuevo")
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                parametros.put("correo", getEmail);
                parametros.put("otp", generateOtp);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void generarNuevoCodigo(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new SweetAlertDialog(OTP_Verification.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Nuevo código generado con éxito")
                        .setContentText("Hemos enviado el luego código a tu correo electrónico")
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
                parametros.put("correo", getEmail);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showKeyBoard(EditText otpET){

        otpET.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, inputMethodManager.SHOW_IMPLICIT);
    }

    //Método para activar el conteo regresivo
    private void startCountDownTimer(){

        resendEnabled = false;

        new CountDownTimer(resendTime * 1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resendCode.setText("Generar nuevo código ("+(millisUntilFinished / 1000)+")");
                resendCode.setTextColor(Color.parseColor("#6A6A6A"));
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendCode.setText("Generar nuevo código");
                resendCode.setTextColor(Color.parseColor("#0018DE"));
            }
        }.start();
    }

    //Método para ubicar en que posición se va a asignar el valor que ingrese el usuario en el teclado

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length()>0){

                if (selectedETPosition == 0){

                    selectedETPosition = 1;
                    showKeyBoard(otpEt2);
                }else if (selectedETPosition == 1){

                    selectedETPosition = 2;
                    showKeyBoard(otpEt3);
                }else if (selectedETPosition == 2){

                    selectedETPosition = 3;
                    showKeyBoard(otpEt4);
                }else if (selectedETPosition == 3){

                    selectedETPosition = 4;
                    showKeyBoard(otpEt5);
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_DEL){

            if (selectedETPosition == 4){
                selectedETPosition = 3;
                showKeyBoard(otpEt4);
            }else if (selectedETPosition == 3){
                selectedETPosition = 2;
                showKeyBoard(otpEt3);
            }else if (selectedETPosition == 2){
                selectedETPosition = 1;
                showKeyBoard(otpEt2);
            }else if (selectedETPosition == 1){
                selectedETPosition = 0;
                showKeyBoard(otpEt1);
            }

            return true;

        }else{
            return super.onKeyUp(keyCode, event);
        }
    }
}