package com.pol.poletech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pol.poletech.connectClasses.connect_ForgetPass;
import com.pol.poletech.connectClasses.connect_LoginTech;

import org.json.JSONArray;
import org.json.JSONObject;

public class Activity_Login_PoleTech extends AppCompatActivity {

    //var of elements
    EditText edtPhoneNumLogin, edtPassLogin, edtPhoneNumberForget;
    LinearLayout LinearLogin, LinearRegForgetPass;

    //public var
    SharedPreferences preferencesLoginTech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_poletech);
        //open shared ******************************************************************
        preferencesLoginTech = getSharedPreferences("polTech", 0);

        //build objects *****************************************************************
        edtPhoneNumLogin = findViewById(R.id.edtPhoneNumLogin);
        edtPassLogin = findViewById(R.id.edtPassLogin);
        edtPhoneNumberForget = findViewById(R.id.edtPhoneNumberForget);
        LinearLogin = findViewById(R.id.LinearLogin);
        LinearRegForgetPass = findViewById(R.id.LinearRegForgetPass);

        LinearLogin.setVisibility(View.VISIBLE);
        LinearRegForgetPass.setVisibility(View.GONE);
    }

    //forget password ******************************************************************************
    public void btnForgetPass(View view) {
        LinearLogin.setVisibility(View.GONE);
        LinearRegForgetPass.setVisibility(View.VISIBLE);
    }

    public void btnGetPass(View view) {
        String PhoneNum = edtPhoneNumberForget.getText().toString() + "";
        new connect_ForgetPass(getString(R.string.LinkForgetPass), iForgetPassRes, PhoneNum).execute();
    }

    connect_ForgetPass.IForgetPassRes iForgetPassRes = new connect_ForgetPass.IForgetPassRes() {
        @Override
        public void ForgetPassTechResult(String res) {
            Toast.makeText(Activity_Login_PoleTech.this, res + "", Toast.LENGTH_SHORT).show();
        }
    };


    public void btnBackOnClickForgetPass(View view) {
        edtPhoneNumberForget.setText("");
        LinearLogin.setVisibility(View.VISIBLE);
        LinearRegForgetPass.setVisibility(View.GONE);
    }

    //login ****************************************************************************************
    public void btnLoginOnClick(View view) {

        String PhoneNum = edtPhoneNumLogin.getText().toString() + "";
        String PassWord = edtPassLogin.getText().toString() + "";

        new connect_LoginTech(getString(R.string.LinkLoginTech), ishowLoginRes, PhoneNum, PassWord).execute();

    }

    connect_LoginTech.IshowLoginRes ishowLoginRes = new connect_LoginTech.IshowLoginRes() {
        @Override
        public void loginTechResult(String res) {

            if (res.contains("null")) {
                Toast.makeText(Activity_Login_PoleTech.this, "یه چیزی وارد کن!", Toast.LENGTH_SHORT).show();
            } else {
                if (res.contains("NO")) {
                    Toast.makeText(Activity_Login_PoleTech.this, "تو کیشمیشی؟", Toast.LENGTH_SHORT).show();
                } else if (res.contains("NYES")) {
                    Toast.makeText(Activity_Login_PoleTech.this, "تایید نشدی", Toast.LENGTH_SHORT).show();
                } else {
                    GetJSonLogin(res);
                    Toast.makeText(Activity_Login_PoleTech.this, "لاگین شد", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Activity_Login_PoleTech.this, Activity_Main_PoleTech.class);
                    startActivity(intent);
                    finish();
                }
            }


        }
    };


    //json login ****************************************************************************************
    public void GetJSonLogin(String res) {

        try {
            SharedPreferences.Editor editor = preferencesLoginTech.edit();
            JSONArray jsonArray = new JSONArray(res);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);

                editor.putInt("ID_Tech", object.getInt("ID"));
                editor.putString("FirstName_Tech", object.getString("FirstName"));
                editor.putString("LastName_Tech", object.getString("LastName"));
                editor.putInt("PhoneNum_Tech", object.getInt("PhoneNum"));
                editor.putString("StateName_Tech", object.getString("StateName"));
                editor.putString("CityName_Tech", object.getString("CityName"));
                editor.putString("Address_Tech", object.getString("Address"));
                editor.putString("Password_Tech", object.getString("pass"));
                editor.putString("SkillName_Tech", object.getString("SkillName"));
                editor.putString("confirm_Tech", object.getString("confirm"));
                editor.putInt("HaveJob_Tech", object.getInt("HaveJob"));
                editor.putInt("reqID_Tech", object.getInt("reqID"));
                editor.commit();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
