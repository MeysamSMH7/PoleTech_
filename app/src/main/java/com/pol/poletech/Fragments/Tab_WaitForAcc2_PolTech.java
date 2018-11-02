package com.pol.poletech.Fragments;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pol.poletech.Activity_WaitAcc2_PoleTech;
import com.pol.poletech.R;
import com.pol.poletech.connectClasses.connect_Acc2;
import com.pol.poletech.connectClasses.connect_AccWhatsJob;


import org.json.JSONArray;
import org.json.JSONObject;



public class Tab_WaitForAcc2_PolTech extends Fragment {

    //var Views ************************************************
    TextView txtShowWorkAcc2;
    EditText edtFirstPriceAcc2,edtPeriodWorkAcc2;


    //public var **********************************************
    SharedPreferences preferencesAcc2Tech;
    int IDPost = 0;
    int IDTech = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ft_waitmoney_poluser, container, false);

        //open shared ******************************************************************
        preferencesAcc2Tech = getActivity().getSharedPreferences("polTech", 0);

        IDPost = preferencesAcc2Tech.getInt("reqID_Tech" , 0);
        IDTech = preferencesAcc2Tech.getInt("ID_Tech" , 0);



        //create objects of view *************************************************************************************
        txtShowWorkAcc2 = view.findViewById(R.id.txtShowWorkAcc2);
        edtFirstPriceAcc2 = view.findViewById(R.id.edtFirstPriceAcc2);
        edtPeriodWorkAcc2 = view.findViewById(R.id.edtPeriodWorkAcc2);




        new connect_AccWhatsJob(getString(R.string.LinkWhatIsTheJobAcc2) , ishowAccWhatsJobRes , IDPost+"").execute();



        return view;
    }


    connect_AccWhatsJob.IshowAccWhatsJobRes ishowAccWhatsJobRes = new connect_AccWhatsJob.IshowAccWhatsJobRes() {
        @Override
        public void AccWhatsJobTechResult(String res) {
            GetJsonWhatsJob(res);
        }
    };



    public void btnGetWorkAcc2(View view){
        String price = edtFirstPriceAcc2.getText().toString()+"";
        String periodWork = edtPeriodWorkAcc2.getText().toString()+"";


        new connect_Acc2(getString(R.string.LinkAcc2),ishowAcc2Res , 1+"" , price ,periodWork, 1+"" , IDTech+"").execute();
    }




    public void btnNoGetWorkAcc2(View view){
        new connect_Acc2(getString(R.string.LinkAcc2),ishowAcc2Res , 0+"" , 0+"" ,0+"", 0+"" , IDTech+"").execute();
    }


    connect_Acc2.IshowAcc2Res ishowAcc2Res = new connect_Acc2.IshowAcc2Res() {
        @Override
        public void Acc2TechResult(String res) {
            Toast.makeText(getActivity(), res+"", Toast.LENGTH_SHORT).show();
            //finish();
        }
    };


    //Json for show Work **********************

    private void GetJsonWhatsJob(String res) {
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

                txtShowWorkAcc2.setText("" +
                        "موضوع: " + Subject + "\n" +
                        "تاریخ: " + DateDay + "/" + DateMonth + "/" + DateYear + "\t" + NameWeek + "\n" +
                        "بازه زمانی: " + PeriodTime + "\n" +
                        "آدرس: " + Address + "\n" +
                        "متن درخواست: " + txt + "\n" +
                        "نام و نام خانوادگی درخواست دهنده: " + FirstName + " " + LastName + "\n" +
                        "شماره تلفن: " + PhoneNum + "\n");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}