package com.pol.poletech;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pol.poletech.connectClasses.connect_AccOne;

import org.json.JSONArray;
import org.json.JSONObject;

public class Activity_GetMoney_PoleTech extends AppCompatActivity {

    SharedPreferences preferencesGetMoneyTech;
    int IDPost = 0;
    int IDTech = 0;
    TextView txtShowWorkGetMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmoney_poletech);

        //open shared ******************************************************************
        preferencesGetMoneyTech = getSharedPreferences("polTech", 0);
        IDPost = preferencesGetMoneyTech.getInt("reqID_Tech", 0);

        txtShowWorkGetMoney = findViewById(R.id.txtShowWorkGetMoney);
        new connect_AccOne(getString(R.string.LinkWhatIsTheJobGetMoney), ishowAccOneRes, "", IDPost + "").execute();

    }


    connect_AccOne.IshowAccOneRes ishowAccOneRes = new connect_AccOne.IshowAccOneRes() {
        @Override
        public void AccOneTechResult(String res) {
            if (IDTech == 0) {
                GetJsonWhatsJobGetMoney(res);
            } else {
                Toast.makeText(Activity_GetMoney_PoleTech.this, res + "", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void btnGetMoney(View view) {
        IDTech = preferencesGetMoneyTech.getInt("ID_Tech", 0);

        new connect_AccOne(getString(R.string.LinkGetMoney), ishowAccOneRes, IDTech + "", IDPost + "").execute();


    }


    private void GetJsonWhatsJobGetMoney(String res) {
        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                int IDPost = object.getInt("ID");
                String Subject = object.getString("Subject");
                int DateDay = object.getInt("DateDay");
                String DateMonth = object.getString("DateMonth");
                int DateYear = object.getInt("DateYear");
                String NameWeek = object.getString("NameWeek");
                String PeriodTime = object.getString("PeriodTime");
                String Address = object.getString("Address");
                String txt = object.getString("txt");
                String FirstName = object.getString("FirstName");
                String LastName = object.getString("LastName");
                int PhoneNum = object.getInt("PhoneNum");
                int PeriodWork = object.getInt("PeriodWork");
                int Price = object.getInt("Price");
                int ChengePrice = object.getInt("ChengePrice");
                String ChengePriceReason = object.getString("ChengePriceReason");
                int finalPrice = Price + ChengePrice;

                txtShowWorkGetMoney.setText("" +
                        "موضوع: " + Subject + "\n" +
                        "تاریخ: " + DateDay + "/" + DateMonth + "/" + DateYear + "\t" + NameWeek + "\n" +
                        "بازه زمانی: " + PeriodTime + "\n" +
                        "آدرس: " + Address + "\n" +
                        "متن درخواست: " + txt + "\n" +
                        "قیمت اولیه: " + Price + "\n" +
                        "بازه زمانی: " + PeriodWork + "\n" +
                        "قیمت اضافه: " + ChengePrice + "\n" +
                        "دلیل قیمت اضافه: " + ChengePriceReason + "\n" +
                        "قیمت کل: " + finalPrice + "\n" +
                        "نام و نام خانوادگی درخواست دهنده: " + FirstName + " " + LastName + "\n" +
                        "شماره تلفن: " + PhoneNum + "\n" +
                        "پرداخت نقدی است");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}