package com.chathra.littleitalypizzeria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        TextView txtAppName = findViewById(R.id.txtAppName);

        imgLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down_enter));
        txtAppName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up_enter));

        new Handler().postDelayed(() -> {

            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            Animatoo.animateSlideLeft(SplashActivity.this);
            finishAffinity();

        },2500);
    }
}