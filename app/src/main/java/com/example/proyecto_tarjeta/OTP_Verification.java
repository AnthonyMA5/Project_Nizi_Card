package com.example.proyecto_tarjeta;

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

public class OTP_Verification extends AppCompatActivity {

    private EditText otpEt1, otpEt2, otpEt3, otpEt4, otpEt5;
    private Button btnVerify;
    private TextView resendCode;

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

        final String getEmail = getIntent().getStringExtra("email");

        //Asignamos el email en el TextView

        otpEmail.setText(getEmail);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);
        otpEt5.addTextChangedListener(textWatcher);

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
                }

            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String generateOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString()+otpEt5.getText().toString();
                Intent intent = new Intent(OTP_Verification.this, Home.class);
                startActivity(intent);
                if (generateOtp.length()==5){
                    //Escribir el código para validar el OTP
                }
            }
        });

    }

    private void showKeyBoard(EditText otpET){

        otpET.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, inputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer(){

        resendEnabled = false;

        new CountDownTimer(resendTime * 1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resendCode.setText("Generar nuevo código ("+(millisUntilFinished / 1000)+")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendCode.setText("Generar nuevo código");
                resendCode.setTextColor(Color.parseColor("#0018DE"));
            }
        }.start();
    }

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