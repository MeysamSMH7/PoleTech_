package com.pol.poletech.Fragments;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.pol.poletech.R;

import org.json.JSONArray;
import org.json.JSONObject;



public class Tab_accOne_PolUser extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ft_accone_poluser, container, false);

        Toast.makeText(getContext(), "ft_accone_poluser", Toast.LENGTH_SHORT).show();


        return view;
    }



    public void GetJSONAccOne(String res) {

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
                int UserID = object.getInt("UserID");
                int TechID = object.getInt("TechID");
                int AcceptOne = object.getInt("AcceptOne");
                int Price = object.getInt("Price");
                int PeriodWork = object.getInt("PeriodWork");
                int AcceptTwo = object.getInt("AcceptTwo");
                int AccpectPriceUser = object.getInt("AccpectPriceUser");
                String FirstName = object.getString("FirstName");
                String LastName = object.getString("LastName");
                int PhoneNum = object.getInt("PhoneNum");

//                listAccOne.add("" +
//                        "موضوع: " + Subject + "\n" +
//                        "تاریخ: " + DateDay + "/" + DateMonth + "/" + DateYear + "\t" + NameWeek + "\n" +
//                        "نام و نام خانوادگی متخصص: " + FirstName + " " + LastName + "\n" +
//                        "قیمت: " + Price +
//                        "بازه زمانی کار: " + PeriodWork + "");
//                listAccOneAlert.add("" +
//                        "موضوع: " + Subject + "\n" +
//                        "تاریخ: " + DateDay + "/" + DateMonth + "/" + DateYear + "\t" + NameWeek + "\n" +
//                        "بازه زمانی: " + PeriodTime + "\n" +
//                        "آدرس: " + Address + "\n" +
//                        "مشکل: " + txt + "\n" +
//                        "نام و نام خانوادگی متخصص: " + FirstName + " " + LastName + "\n" +
//                        "قیمت: " + Price +
//                        "بازه زمانی کار: " + PeriodWork +
//                        "شماره تلفن: " + PhoneNum + "");
//                listIDPost.add(IDPost);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}