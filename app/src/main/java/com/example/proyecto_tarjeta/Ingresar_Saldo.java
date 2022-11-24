package com.example.proyecto_tarjeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class Ingresar_Saldo extends AppCompatActivity {

    Button open_paypal;
    EditText monto;

    public static final String clientID = "ATMY7X5gZ05-2hVSTjcA1_KHSy3nBx3VBZUOmQFaohXH_hhoCEtpERW86LJRDQ024qV14f-KhBGmpWvQ";
    public static final int PAYPAL_REQUEST_CODE = 123;

    public static PayPalConfiguration configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(clientID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_saldo);

        open_paypal = findViewById(R.id.continuar_paypal);
        monto = findViewById(R.id.monto_ingresar);
        
        open_paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayment();
            }
        });
    }

    private void getPayment() {

        String monto_ingreso = monto.getText().toString();

        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(monto_ingreso)), "MXN", "Learn", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);

        startActivityForResult(intent, PAYPAL_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PAYPAL_REQUEST_CODE){
            PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if (confirmation!=null){

                try {
                    String paymentDetails = confirmation.toJSONObject().toString(4);
                    JSONObject payObj = new JSONObject(paymentDetails);
                }catch (JSONException e){
                    e.printStackTrace();
                    Log.e("Error", "Algo salió mal");
                }
            }
        }else if (requestCode == Activity.RESULT_CANCELED){
            Log.i("Error", "Algo salió mal");
        }else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Log.i("Payment", "Pago no válido");
        }

    }
}