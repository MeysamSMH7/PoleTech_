package com.pol.poletech;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pol.poletech.connectClasses.connect_AccOne;
import com.pol.poletech.connectClasses.connect_Finished;

import org.json.JSONArray;
import org.json.JSONObject;

public class Activity_Finished_PoleTech extends AppCompatActivity {

    //var Views ************************************************
    TextView txtShowWorkFinished;
    EditText edtAddPriceFinished, edtChangePriceResFinished;

    //public var **********************************************
    SharedPreferences preferencesFinishedTech;
    int IDPost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_poletech);


        //open shared ******************************************************************
        preferencesFinishedTech = getSharedPreferences("polTech", 0);
        IDPost = preferencesFinishedTech.getInt("reqID_Tech", 0);


        //create objects of view *************************************************************************************
        txtShowWorkFinished = findViewById(R.id.txtShowWorkFinished);
        edtAddPriceFinished = findViewById(R.id.edtAddPriceFinished);
        edtChangePriceResFinished = findViewById(R.id.edtChangePriceResFinished);


        new connect_AccOne(getString(R.string.LinkWhatIsTheJobFinished), ishowAccOneRes, "", IDPost + "").execute();

    }

    connect_AccOne.IshowAccOneRes ishowAccOneRes = new connect_AccOne.IshowAccOneRes() {
        @Override
        public void AccOneTechResult(String res) {
            GetJsonWhatsJobFinished(res);
        }
    };

    public void btnFinished(View view) {
        String ChengePrice = edtAddPriceFinished.getText().toString() + "";
        String ChengePriceReason = edtChangePriceResFinished.getText().toString();
        new connect_Finished(getString(R.string.LinkFinishedWithChange), ishowFinishedRes, IDPost + "", ChengePrice, ChengePriceReason).execute();

    }

    connect_Finished.IshowFinishedRes ishowFinishedRes = new connect_Finished.IshowFinishedRes() {
        @Override
        public void FinishedTechResult(String res) {
            Toast.makeText(Activity_Finished_PoleTech.this, res + "", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    //Json for show Work **********************

    private void GetJsonWhatsJobFinished(String res) {
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

                txtShowWorkFinished.setText("" +
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
                        "شماره تلفن: " + PhoneNum + "\n");

                edtAddPriceFinished.setText(ChengePrice + "");
                edtChangePriceResFinished.setText(ChengePriceReason + "");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}