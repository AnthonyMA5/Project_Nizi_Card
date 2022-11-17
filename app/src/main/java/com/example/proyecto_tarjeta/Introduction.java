package com.example.proyecto_tarjeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Introduction extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext, btnGetStarted;
    int position = 0;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //when this activity is about to be launch we need to check if it's opened before or not
        if (restorePrefData()){
            Intent mainActivity = new Intent(getApplicationContext(), Login.class);
            startActivity(mainActivity);
            finish();
        }

        setContentView(R.layout.activity_introduction);

        //ini view
        btnNext = findViewById(R.id.btn_next);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        // fill list screen
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Nizi Card vuelve fácil lo que siempre fue complicado", "La nueva tarjeta física que facilitará tus compras en comercios participantes.", R.raw.intro1));
        mList.add(new ScreenItem("Llévala contigo a todos lados", "Nizi es una tarjeta física con un diseño compacto y minimalista.\n\n 100% diseñada para ti.",R.raw.intro2));
        mList.add(new ScreenItem("Dile adiós a tu cartera ", "Nizi cuenta con un sofisticado sistema en el cual solo debes colocar un segundo la tarjeta sobre el lector y automáticamente se pagará tu compra.",R.raw.intro3));
        mList.add(new ScreenItem("Recibela en la puerta de tu casa", "Contamos con servicio de paquetería para evitar que acudas a alguna sucursal para adquirir tu tarjeta.",R.raw.intro4));
        mList.add(new ScreenItem("¡Comienza tu registro ahora!", "Obtenla en tan solo 3 minutos.",R.raw.intro5));

        // Setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //setup tablayout with viewpager

        tabIndicator.setupWithViewPager(screenPager);

        //next button click listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                
                if (position == mList.size()-1){ //when ew rech to the last screen
                    // TODO;show the GETSTARTED button and hide the indicator and the next button

                    loadLastCreen();
                }

            }
        });

        //tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1){
                    loadLastCreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Get started button click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open main activity
                Intent login = new Intent(getApplicationContext(), Registro.class);
                startActivity(login);
                //also we need to save a boolean value to storage so next time when the user run the app
                //we could know that  he's already checked the introduction
                //use shared preference to that process
                savePrefsData();
                finish();
            }
        });

    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened", false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }

    private void loadLastCreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.VISIBLE);
        // TODO:ADD an animation the getstarted button
        //lets create and setup the button animation
        btnGetStarted.setAnimation(btnAnim);
    }
}