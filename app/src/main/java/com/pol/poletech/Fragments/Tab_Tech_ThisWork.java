package com.pol.poletech.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pol.poletech.R;
import com.pol.poletech.connectClasses.connect_Acc2;
import com.pol.poletech.connectClasses.connect_AccOne;

import org.json.JSONArray;
import org.json.JSONObject;

public class Tab_Tech_ThisWork extends Fragment {

    // this work (Doing)
    private TextView txtSubjectsDoing, txtHaveJob, txtNoJob_thisWork;
    private LinearLayout linearFragment_thisWork;

    private SharedPreferences mainShared;
    private int IDPost = 0, haveJob = 0, IDTech = 0;

    // Acc2
    private EditText edtFirstPriceAcc2, edtPeriodWorkAcc2;
    private Button btnNoGetWorkAcc2, btnGetWorkAcc2;
    private LinearLayout linearacc2_thisWork;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tech_thiswork, container, false);
        mainShared = getActivity().getSharedPreferences("polTech", 0);
        haveJob = mainShared.getInt("HaveJob_Tech", 0);
        IDTech = mainShared.getInt("ID_Tech", 0);

        // this work (Doing)
        txtSubjectsDoing = view.findViewById(R.id.txtSubjectsDoing_thisWork);
        txtHaveJob = view.findViewById(R.id.txtHaveJob_thisWork);
        txtNoJob_thisWork = view.findViewById(R.id.txtNoJob_thisWork);
        linearFragment_thisWork = view.findViewById(R.id.linearFragment_thisWork);

        if (haveJob == 0) {
            linearFragment_thisWork.setVisibility(View.GONE);
            txtNoJob_thisWork.setVisibility(View.VISIBLE);

        } else {
            linearFragment_thisWork.setVisibility(View.VISIBLE);
            txtNoJob_thisWork.setVisibility(View.GONE);
            HaveJob();
        }


        // Acc2

        edtFirstPriceAcc2 = view.findViewById(R.id.edtFirstPriceAcc2_thisWork);
        edtPeriodWorkAcc2 = view.findViewById(R.id.edtPeriodWorkAcc2_thisWork);
        btnNoGetWorkAcc2 = view.findViewById(R.id.btnNoGetWorkAcc2_thisWork);
        btnGetWorkAcc2 = view.findViewById(R.id.btnGetWorkAcc2_thisWork);
        linearacc2_thisWork = view.findViewById(R.id.linearacc2_thisWork);

        btnNoGetWorkAcc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new connect_Acc2(getString(R.string.LinkAcc2), ishowAcc2Res, 0 + "", 0 + "", 0 + "", 0 + "", IDTech + "").execute();

                SharedPreferences.Editor editor = mainShared.edit();
                editor.putInt("HaveJob_Tech", 0);
                editor.putInt("reqID_Tech", 0);
                editor.commit();

                linearFragment_thisWork.setVisibility(View.GONE);
                txtNoJob_thisWork.setVisibility(View.VISIBLE);

            }
        });


        btnGetWorkAcc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = edtFirstPriceAcc2.getText().toString() + "";
                String periodWork = edtPeriodWorkAcc2.getText().toString() + "";

                new connect_Acc2(getString(R.string.LinkAcc2), ishowAcc2Res, 1 + "", price, periodWork, 1 + "", IDTech + "").execute();


                HaveJob();

            }
        });


        return view;
    }


    // this work (Doing)

    // HAVE JOB *********************************************************
    private void HaveJob() {
        IDPost = mainShared.getInt("reqID_Tech", 0);
        new connect_AccOne(getString(R.string.LinkDoingJob), ishowAccOneRes, "", IDPost + "").execute();

    }

    // show Job *********************************************************
    connect_AccOne.IshowAccOneRes ishowAccOneRes = new connect_AccOne.IshowAccOneRes() {
        @Override
        public void AccOneTechResult(String res) {

            GetWorkDOING(res);
        }
    };

    //GetJson *****************************************************************************
    private void GetWorkDOING(String res) {
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
                String PhoneNum = object.getString("PhoneNum") + "";
                int PeriodWork = object.getInt("PeriodWork");
                int Price = object.getInt("Price");
                String PriceST = "", PeriodWorkST = "";

                if (Price == 0) {
                    PriceST = "تعیین نشده";
                } else {
                    PriceST = Price + " ساعت";
                }
                if (PeriodWork == 0) {
                    PeriodWorkST = "تعیین نشده";
                } else {
                    PeriodWorkST = PeriodWork + " تومان";
                }

                if (Price == 0 && PeriodWork == 0) {
                    linearacc2_thisWork.setVisibility(View.VISIBLE);
                } else {
                    linearacc2_thisWork.setVisibility(View.GONE);
                }


                txtSubjectsDoing.setText(Subject + "");
                txtHaveJob.setText("" +
                        "تاریخ: " + NameWeek + "\t" + DateDay + "/" + DateMonth + "/" + DateYear + "\n" +
                        "بازه زمانی: " + PeriodTime + "\n" +
                        "نام درخواست دهنده: " + FirstName + " " + LastName + "\n" +
                        "آدرس: " + Address + "\n" +
                        "شماره تلفن: " + PhoneNum + "\n" +
                        "متن درخواست: " + txt + "\n" +
                        "قیمت: " + PriceST + "\n" +
                        "بازه زمانی: " + PeriodWorkST + "\n"
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Acc2

    connect_Acc2.IshowAcc2Res ishowAcc2Res = new connect_Acc2.IshowAcc2Res() {
        @Override
        public void Acc2TechResult(String res) {
            Toast.makeText(getActivity(), res + "", Toast.LENGTH_SHORT).show();
        }
    };


}


