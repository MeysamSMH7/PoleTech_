package com.pol.poletech;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pol.poletech.connectClasses.connect_AccOne;
import com.pol.poletech.connectClasses.connect_Works;

public class Activity_NewWork_2 extends AppCompatActivity {

    private SharedPreferences NewWork_2_Shared;
    private String NewWork_2 = "", IDPost = "";
    private String[] NewWork_2Arr = {};
    private TextView txtDate_FrThisWork, txtTime_FrThisWork, txtFnameLname_FrThisWork, txtPhone_FrThisWork, txtAddress_FrThisWork, txtTXT_FrThisWork;
    private Button btnlst_CancelAlert, btnlst_getWorkAlert;
    private int IDTech = 0, haveJob = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_newwork_2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            NewWork_2_Shared = getSharedPreferences("polTech", 0);
            IDTech = NewWork_2_Shared.getInt("ID_Tech", 0);

            NewWork_2 = extras.getString("KEYNewWork_2");
            NewWork_2Arr = NewWork_2.split("/");

            txtDate_FrThisWork = findViewById(R.id.txtDate_FrThisWork);
            txtTime_FrThisWork = findViewById(R.id.txtTime_FrThisWork);
            txtFnameLname_FrThisWork = findViewById(R.id.txtFnameLname_FrThisWork);
            txtPhone_FrThisWork = findViewById(R.id.txtPhone_FrThisWork);
            txtAddress_FrThisWork = findViewById(R.id.txtAddress_FrThisWork);
            txtTXT_FrThisWork = findViewById(R.id.txtTXT_FrThisWork);

            btnlst_CancelAlert = findViewById(R.id.btnlst_CancelAlert);
            btnlst_getWorkAlert = findViewById(R.id.btnlst_getWorkAlert);

            txtDate_FrThisWork.setText(NewWork_2Arr[0] + "\t" + NewWork_2Arr[1] + "/" + NewWork_2Arr[2] + "/" + NewWork_2Arr[3]);
            txtTime_FrThisWork.setText(NewWork_2Arr[4] + "");
            txtFnameLname_FrThisWork.setText(NewWork_2Arr[5] + " " + NewWork_2Arr[6]);
            txtAddress_FrThisWork.setText(NewWork_2Arr[7] + "");
            txtPhone_FrThisWork.setText(NewWork_2Arr[8] + "");
            txtTXT_FrThisWork.setText(NewWork_2Arr[9] + "");

            btnlst_CancelAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            Toast.makeText(this, IDPost+"", Toast.LENGTH_SHORT).show();

            btnlst_getWorkAlert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    haveJob = NewWork_2_Shared.getInt("HaveJob_Tech", 0);
                    if (haveJob == 1) {
                        Toast.makeText(Activity_NewWork_2.this, "شما نمیتونید کار جدیدی رو بگیرید", Toast.LENGTH_SHORT).show();
                    } else {

                        SharedPreferences.Editor editor = NewWork_2_Shared.edit();
                        editor.putInt("HaveJob_Tech", 0);
                        editor.putInt("reqID_Tech", 0);
                        editor.apply();

                        new connect_AccOne(getString(R.string.LinkAcceptOne), iAcceptOne, IDTech + "", IDPost + "").execute();

                    }
                }
            });


        } else {
            finish();
        }


    }

    connect_AccOne.IshowAccOneRes iAcceptOne = new connect_AccOne.IshowAccOneRes() {
        @Override
        public void AccOneTechResult(String res) {
            Toast.makeText(Activity_NewWork_2.this, res + "", Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = NewWork_2_Shared.edit();
            editor.putInt("HaveJob_Tech", 1);
            editor.putInt("reqID_Tech", Integer.parseInt(IDPost));
            editor.apply();
            finish();

        }
    };


}
