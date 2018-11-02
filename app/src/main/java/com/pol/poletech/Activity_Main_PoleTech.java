package com.pol.poletech;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Activity_Main_PoleTech extends AppCompatActivity {
    SharedPreferences MainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_poletech);
        MainPage = getSharedPreferences("polTech", 0);


    }


    public void Works(View view) {
        if (MainPage.getInt("HaveJob_Tech", 0) != 0) {
            Toast.makeText(this, "شما کار داری عامو", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Activity_Main_PoleTech.this, Activity_Works_PoleTech.class);
            startActivity(intent);
        }
    }


    public void WaitForAcc2(View view) {
        Intent intent = new Intent(Activity_Main_PoleTech.this, Activity_WaitAcc2_PoleTech.class);
        startActivity(intent);
    }


    public void Finished(View view) {
        Intent intent = new Intent(Activity_Main_PoleTech.this, Activity_Finished_PoleTech.class);
        startActivity(intent);
    }


    public void WaitForMoney(View view) {
        Intent intent = new Intent(Activity_Main_PoleTech.this, Activity_GetMoney_PoleTech.class);
        startActivity(intent);
    }


    public void Working(View view) {

    }

}
