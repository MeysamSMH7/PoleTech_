package com.pol.poletech.Fragments;



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



public class Tab_WaitMoney_PolUser extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ft_waitmoney_poluser, container, false);

        Toast.makeText(getContext(), "ft_waitmoney_poluser", Toast.LENGTH_SHORT).show();



        // connect_AcceptOne(getString(R.string.LinkWaiteForPrice), iWaitePrice, IDUser, 0).execute();

        return view;
    }




    public void GetJsonWaitPrice(String res) {

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
                int ChengePrice = object.getInt("ChengePrice");
                String ChengePriceReason = object.getString("ChengePriceReason");
                String FirstName = object.getString("FirstName");
                String LastName = object.getString("LastName");
                int PhoneNum = object.getInt("PhoneNum");
                String ChengePriceST = "";
                String ChengePriceReasonST = "";
                int finalPrice = 0;

                if (ChengePrice == 0) {
                    ChengePriceST = "خیر";
                    finalPrice = Price;
                } else {
                    ChengePriceST = ChengePrice + "";
                    finalPrice = Price + ChengePrice;
                }
                if (ChengePriceReason.equals("خالی")) {
                    ChengePriceReasonST = "ندارد";
                } else {
                    ChengePriceReasonST = ChengePriceReason + "";
                }


//                listWaitPrice.add("" +
//                        "موضوع: " + Subject + "\n" +
//                        "تاریخ: " + DateDay + "/" + DateMonth + "/" + DateYear + "\t" + NameWeek + "\n" +
//                        "نام و نام خانوادگی متخصص: " + FirstName + " " + LastName + "\n" +
//                        "قیمت: " + Price +
//                        "بازه زمانی کار: " + PeriodWork + "");
//                listWaitPriceAlert.add("" +
//                        "موضوع: " + Subject + "\n" +
//                        "تاریخ: " + DateDay + "/" + DateMonth + "/" + DateYear + "\t" + NameWeek + "\n" +
//                        "بازه زمانی: " + PeriodTime + "\n" +
//                        "آدرس: " + Address + "\n" +
//                        "مشکل: " + txt + "\n\t" +
//                        "نام و نام خانوادگی متخصص: " + FirstName + " " + LastName + "\n\t" +
//                        "بازه زمانی کار: " + PeriodWork + "\n\t" +
//                        "شماره تلفن: " + PhoneNum + "\n\t\t" +
//                        "قیمت اولیه: " + Price + "\n\t\t" +
//                        "تغییر قیمت: " + ChengePriceST + "\n\t\t" +
//                        "علت تغییر قیمت: " + ChengePriceReasonST + "\n\t\t" +
//                        "قیمت کل: " + finalPrice +
//                        "");
//                listIDPostWaitPrice.add(IDPost);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}