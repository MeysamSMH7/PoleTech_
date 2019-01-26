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
    private TextView txtSubjectsDoing, txtNoJob_thisWork, txtDate_FrThisWork, txtTime_FrThisWork, txtFnameLname_FrThisWork, txtPhone_FrThisWork, txtAddress_FrThisWork, txtTXT_FrThisWork, txtHour, txtPeriodWork, txtDontHavePrice;
    private LinearLayout linearDontHavePrice, linearHavePrice, linearDetails;

    private SharedPreferences mainShared;
    private int IDPost = 0, haveJob = 0, IDTech = 0;

    // Acc2
    private EditText edtFirstPriceAcc2, edtPeriodWorkAcc2;
    private Button btnNoGetWorkAcc2, btnGetWorkAcc2;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tech_thiswork, container, false);
        mainShared = getActivity().getSharedPreferences("polTech", 0);
        haveJob = mainShared.getInt("HaveJob_Tech", 0);
        IDPost = mainShared.getInt("reqID_Tech", 0);
        IDTech = mainShared.getInt("ID_Tech", 0);

        // this work (Doing)
        txtSubjectsDoing = view.findViewById(R.id.txtSubjectsDoing_thisWork);
        txtNoJob_thisWork = view.findViewById(R.id.txtNoJob_thisWork);
        txtDontHavePrice = view.findViewById(R.id.txtDontHavePrice);
        txtDate_FrThisWork = view.findViewById(R.id.txtDate_FrThisWork);
        txtTime_FrThisWork = view.findViewById(R.id.txtTime_FrThisWork);
        txtFnameLname_FrThisWork = view.findViewById(R.id.txtFnameLname_FrThisWork);
        txtPhone_FrThisWork = view.findViewById(R.id.txtPhone_FrThisWork);
        txtAddress_FrThisWork = view.findViewById(R.id.txtAddress_FrThisWork);
        txtTXT_FrThisWork = view.findViewById(R.id.txtTXT_FrThisWork);
        txtHour = view.findViewById(R.id.txtHour);
        txtPeriodWork = view.findViewById(R.id.txtPeriodWork);

        linearDontHavePrice = view.findViewById(R.id.linearDontHavePrice);
        linearHavePrice = view.findViewById(R.id.linearHavePrice);
        linearDetails = view.findViewById(R.id.linearDetails);

        if (haveJob == 0 || IDPost == 0) {
            linearHavePrice.setVisibility(View.GONE);
            txtDontHavePrice.setVisibility(View.GONE);
            linearDontHavePrice.setVisibility(View.GONE);
            linearDetails.setVisibility(View.GONE);

            txtNoJob_thisWork.setVisibility(View.VISIBLE);

        } else {

            txtNoJob_thisWork.setVisibility(View.GONE);
            linearDetails.setVisibility(View.VISIBLE);
            HaveJob();
        }


        // Acc2

        edtFirstPriceAcc2 = view.findViewById(R.id.edtFirstPriceAcc2_thisWork);
        edtPeriodWorkAcc2 = view.findViewById(R.id.edtPeriodWorkAcc2_thisWork);
        btnNoGetWorkAcc2 = view.findViewById(R.id.btnNoGetWorkAcc2_thisWork);
        btnGetWorkAcc2 = view.findViewById(R.id.btnGetWorkAcc2_thisWork);

        btnNoGetWorkAcc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new connect_Acc2(getString(R.string.LinkAcc2), ishowAcc2Res, 0 + "", 0 + "", 0 + "", 0 + "", IDTech + "").execute();

                SharedPreferences.Editor editor = mainShared.edit();
                editor.putInt("HaveJob_Tech", 0);
                editor.putInt("reqID_Tech", 0);
                editor.apply();

                linearHavePrice.setVisibility(View.GONE);
                txtDontHavePrice.setVisibility(View.GONE);
                linearDontHavePrice.setVisibility(View.GONE);
                linearDetails.setVisibility(View.GONE);

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


                if (Price == 0 && PeriodWork == 0) {

                    txtDontHavePrice.setVisibility(View.VISIBLE);
                    linearDontHavePrice.setVisibility(View.VISIBLE);
                    linearHavePrice.setVisibility(View.GONE);
                    linearDetails.setVisibility(View.VISIBLE);
                    txtNoJob_thisWork.setVisibility(View.GONE);

                } else {

                    txtDontHavePrice.setVisibility(View.GONE);
                    linearDontHavePrice.setVisibility(View.GONE);
                    linearHavePrice.setVisibility(View.VISIBLE);
                    linearDetails.setVisibility(View.VISIBLE);
                    txtNoJob_thisWork.setVisibility(View.GONE);

                }


                txtSubjectsDoing.setText(Subject + "");

                txtDate_FrThisWork.setText(DateYear + "/" + DateMonth + "/" + DateDay + "\t" + NameWeek);
                txtTime_FrThisWork.setText(PeriodTime + "");
                txtFnameLname_FrThisWork.setText(FirstName + " " + LastName + "");
                txtPhone_FrThisWork.setText(PhoneNum + "");
                txtAddress_FrThisWork.setText(Address + "");
                txtTXT_FrThisWork.setText(txt + "");
                txtHour.setText(Price + "");
                txtPeriodWork.setText(PeriodWork + "");

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


