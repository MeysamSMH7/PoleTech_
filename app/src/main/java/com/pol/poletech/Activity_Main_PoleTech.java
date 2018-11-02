package com.pol.poletech;



import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.pol.poletech.Fragments.Tab_WaitMoney_PolUser;
import com.pol.poletech.Fragments.Tab_accOne_PolUser;
import com.pol.poletech.Fragments.Tab_main_PolUser;


public class Activity_Main_PoleTech extends AppCompatActivity {
    Tab_main_PolUser fragment1;
    Tab_accOne_PolUser fragment2;
    Tab_WaitMoney_PolUser fragment3;
    ViewGroup frameLayout;

    DrawerLayout drawerLayout;
    NavigationView navigationview;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_poletech);

        sharedPreferences = getSharedPreferences("polUser", 0);

        frameLayout = (ViewGroup) findViewById(R.id.frameMainPolUser);
        fragment1 = new Tab_main_PolUser();
        fragment2 = new Tab_accOne_PolUser();
        fragment3 = new Tab_WaitMoney_PolUser();

        BottomNavigationView navMainPolUser = findViewById(R.id.navMainPolUser);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameMainPolUser, fragment1);
        transaction.commit();

        navMainPolUser.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (menuItem.getItemId()) {
                    case R.id.MainPageFrame:
                        transaction.replace(R.id.frameMainPolUser, fragment1);
                        transaction.commit();
                        break;

                    case R.id.AccOneFrame:
                        transaction.replace(R.id.frameMainPolUser, fragment2);
                        transaction.commit();
                        break;

                    case R.id.WaitForMoneyFrame:
                        transaction.replace(R.id.frameMainPolUser, fragment3);
                        transaction.commit();
                        break;
                }
                return true;
            }
        });



    }


}
