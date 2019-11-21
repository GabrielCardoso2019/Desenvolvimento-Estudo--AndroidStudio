package com.meuapp.appturma79;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Criar um Intent para iniciar o Menu-Activity */
                Intent LoginActivity = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(LoginActivity);
                finish();


            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
