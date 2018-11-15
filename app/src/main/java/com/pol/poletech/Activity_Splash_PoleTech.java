package com.pol.poletech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pol.poletech.classes.checkInternet;

public class Activity_Splash_PoleTech extends AppCompatActivity {

    boolean finishSplash = false, getStatusWellCome = false;
    checkInternet internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_poletech);
        SharedPreferences firstOpen = getSharedPreferences("polTech", 0);
        getStatusWellCome = firstOpen.getBoolean("statusWellcome?", false);

        internet = new checkInternet(this);


        LinearLayout linearSplashPolTech = findViewById(R.id.linearSplashPolTech);

        linearSplashPolTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!internet.CheckNetworkConnection()) {
                    checkNet();
                } else {
                    if (finishSplash) {
                        GoInApp();
                    }
                }

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!internet.CheckNetworkConnection()) {
                    checkNet();
                    finishSplash = true;
                } else {
                    GoInApp();
                }

            }

        }, 500);


    }






    private void checkNet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Splash_PoleTech.this);
        builder.setTitle(getString(R.string.ToastCheckNetTitle));
        builder.setMessage(getString(R.string.ToastCheckNetMessage));
        builder.show();
    }

    private void GoInApp() {
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

}
