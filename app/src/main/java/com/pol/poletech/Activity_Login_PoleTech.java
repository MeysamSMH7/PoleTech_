package com.pol.poletech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pol.poletech.connectClasses.connect_ForgetPass;
import com.pol.poletech.connectClasses.connect_LoginTech;

import org.json.JSONArray;
import org.json.JSONObject;

public class Activity_Login_PoleTech extends AppCompatActivity {

    //var of elements
    private EditText edtPhoneNumLogin, edtPassLogin, edtPhoneNumberForget;
    private LinearLayout LinearLogin, LinearRegForgetPass, linearUp, linearBtnForget;
    private Button btnLoginOnClick, btnBackOnClickForgetPass, btnGetPass;
    private TextView txtForgetPass;
    private ImageView imgPhone, imgPass, imgTop, imgForgetPass;

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
        linearBtnForget = findViewById(R.id.linearBtnForget);
        btnLoginOnClick = findViewById(R.id.btnLoginOnClick);
        txtForgetPass = findViewById(R.id.txtForgetPass);
        btnBackOnClickForgetPass = findViewById(R.id.btnBackOnClickForgetPass);
        btnGetPass = findViewById(R.id.btnGetPass);
        imgPhone = findViewById(R.id.imgPhone);
        imgPass = findViewById(R.id.imgPass);
        imgTop = findViewById(R.id.imgTop);
        imgForgetPass = findViewById(R.id.imgForgetPass);

        linearUp = findViewById(R.id.linearUp);
        LinearLogin.setVisibility(View.VISIBLE);
        txtForgetPass.setVisibility(View.VISIBLE);
        LinearRegForgetPass.setVisibility(View.GONE);

        SetHeightWidth();
    }


    private void SetHeightWidth() {

        int heightPixelsBtn = (int) (getResources().getDisplayMetrics().heightPixels / 13);
        int widthPixelsBtn = (int) (getResources().getDisplayMetrics().widthPixels / 2.2);
        int heightPixelsEdt = (int) (getResources().getDisplayMetrics().heightPixels / 13);
        int widthPixelsEdt = (int) (getResources().getDisplayMetrics().widthPixels / 1.2) - 60;
        int heightPixelsImgTop = (int) (getResources().getDisplayMetrics().heightPixels / 6);
        int widthPixelsImgTop = (int) (getResources().getDisplayMetrics().widthPixels / 2);

        btnLoginOnClick.setLayoutParams(new LinearLayout.LayoutParams(widthPixelsEdt + 60, heightPixelsBtn));
        btnBackOnClickForgetPass.setLayoutParams(new LinearLayout.LayoutParams(widthPixelsBtn, heightPixelsBtn));
        btnGetPass.setLayoutParams(new LinearLayout.LayoutParams(widthPixelsBtn, heightPixelsBtn));
        edtPhoneNumLogin.setLayoutParams(new LinearLayout.LayoutParams(widthPixelsEdt, heightPixelsEdt));
        edtPassLogin.setLayoutParams(new LinearLayout.LayoutParams(widthPixelsEdt, heightPixelsEdt));
        edtPhoneNumberForget.setLayoutParams(new LinearLayout.LayoutParams(widthPixelsEdt, heightPixelsEdt));

        imgPhone.getLayoutParams().height = heightPixelsEdt;
        imgPass.getLayoutParams().height = heightPixelsEdt;
        imgForgetPass.getLayoutParams().height = heightPixelsEdt;

        imgTop.getLayoutParams().height = heightPixelsImgTop;
        imgTop.getLayoutParams().width = widthPixelsImgTop;


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnLoginOnClick.getLayoutParams();
        params.setMargins(0, 10, 0, 0);
        btnLoginOnClick.setLayoutParams(params);

        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) linearBtnForget.getLayoutParams();
        params2.setMargins(0, 10, 0, 0);
        linearBtnForget.setLayoutParams(params2);
    }


    //forget password ******************************************************************************
    public void txtForgetPass(View view) {
        LinearLogin.setVisibility(View.GONE);
        txtForgetPass.setVisibility(View.INVISIBLE);
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
        txtForgetPass.setVisibility(View.VISIBLE);
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
