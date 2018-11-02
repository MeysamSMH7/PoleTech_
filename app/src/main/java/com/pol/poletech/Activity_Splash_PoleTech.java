package com.pol.poletech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_Splash_PoleTech extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_poletech);
        SharedPreferences firstOpen = getSharedPreferences("polTech", 0);

        final boolean getStatusWellCome = firstOpen.getBoolean("statusWellcome?", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!getStatusWellCome) {
                    Intent intent = new Intent(Activity_Splash_PoleTech.this, Activity_Wellcome_PoleTech.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Activity_Splash_PoleTech.this, Activity_Login_PoleTech.class);
                    startActivity(intent);
                    finish();
                }


            }

        }, 500);

    }
}
