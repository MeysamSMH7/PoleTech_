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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pol.poletech.Fragments.Tab_FinishWork_PolTech;
import com.pol.poletech.Fragments.Tab_WaitForAcc2_PolTech;
import com.pol.poletech.Fragments.Tab_WaitForMoney_PolTech;
import com.pol.poletech.Fragments.Tab_main_PolUser;
import com.pol.poletech.classes.checkInternet;


public class Activity_Main_PoleTech extends AppCompatActivity {
    Tab_main_PolUser fragment1;
    Tab_WaitForAcc2_PolTech fragment2;
    Tab_FinishWork_PolTech fragment3;
    Tab_WaitForMoney_PolTech fragment4;
    ViewGroup frameLayout;
    BottomNavigationView navMainPolUser;

    DrawerLayout drawerLayout;
    NavigationView navigationview;
    SharedPreferences sharedPreferences;

    checkInternet internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_toolbar_nv);

        internet = new checkInternet(this);

        sharedPreferences = getSharedPreferences("polTech", 0);

        frameLayout = (ViewGroup) findViewById(R.id.frameMainPolUser);
        fragment1 = new Tab_main_PolUser();
        fragment2 = new Tab_WaitForAcc2_PolTech();
        fragment3 = new Tab_FinishWork_PolTech();
        fragment4 = new Tab_WaitForMoney_PolTech();

          navMainPolUser = findViewById(R.id.navMainPolUser);


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

                    case R.id.Tab_WaitForAcc2_PolTech:
                        transaction.replace(R.id.frameMainPolUser, fragment2);
                        transaction.commit();
                        break;

                    case R.id.Tab_FinishWork_PolTech:
                        transaction.replace(R.id.frameMainPolUser, fragment3);
                        transaction.commit();
                        break;

                    case R.id.Tab_WaitForMoney_PolTech:
                        transaction.replace(R.id.frameMainPolUser, fragment4);
                        transaction.commit();
                        break;
                }
                return true;
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationview = (NavigationView) findViewById(R.id.navigationview);
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switchNavigationView(id);

                drawerLayout.closeDrawer(Gravity.RIGHT);
                return true;
            }
        });

    }



    private void AlertDialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setTitle("خروج از حساب کاربری");
        builder.setMessage("آیا میخواهید از حساب کاربریتان خارج شوید؟");
        builder.setPositiveButton("آره", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("ID_User", 0);
                editor.putString("FirstName_User", null);
                editor.putString("LastName_User", null);
                editor.putInt("PhoneNum_User", 0);
                editor.putString("StateName_User", null);
                editor.putString("CityName_User", null);
                editor.putInt("CodPosty_User", 0);
                editor.putString("Address_User", null);
                editor.putString("Password_User", null);
                editor.putBoolean("statusLogin?", false);
                editor.apply();

            }
        });

        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_dr) {
            drawerLayout.openDrawer(Gravity.RIGHT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }

    }

    private void getVersionInfo() {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //Toast.makeText(this, String.format("Version name = %s \nVersion code = %d", versionName, versionCode) + "", Toast.LENGTH_SHORT).show();
    }

    private void checkNet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Main_PoleTech.this);
        builder.setTitle(getString(R.string.ToastCheckNetTitle));
        builder.setMessage(getString(R.string.ToastCheckNetMessage));
        builder.show();
    }

    private void switchNavigationView(int ID) {

        switch (ID) {
            case R.id.itmAboutUs:

                AlertDialogDraweLayout("درباره ما", "متن درباره ما");
                break;

            case R.id.FAQ:

                AlertDialogDraweLayout("قوانین", "متن قوانین");
                break;


            case R.id.itmTellUs:

                Intent intent = new Intent(Activity_Main_PoleTech.this, Activity_Ticket_PolTech.class);
                startActivity(intent);
                break;

            case R.id.itmLogOff:

                if (!internet.CheckNetworkConnection()) {
                    checkNet();
                } else {
                    AlertDialogLogout();
                }
                break;
        }

    }

    private void AlertDialogDraweLayout(String Title, String Message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Main_PoleTech.this);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.create();
        builder.show();

    }


}

