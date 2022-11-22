package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    Bundle bundle;
    ImageView access_profile, logout_home;
    TextView bienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*bundle = getIntent().getExtras();
        User user = (User) bundle.getSerializable("datos");*/

        access_profile = findViewById(R.id.img_profile);
        logout_home = findViewById(R.id.btn_logout_home);
        bienvenida = findViewById(R.id.txt_bienvenida);

        //bienvenida.setText(user.getNombre());

        access_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Profile.class);
                startActivity(intent);
            }
        });

        logout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
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