package com.pol.poletech.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pol.poletech.Activity_NewWork_2;
import com.pol.poletech.R;
import com.pol.poletech.connectClasses.connect_AccOne;
import com.pol.poletech.connectClasses.connect_Works;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tab_Tech_NewWorks extends Fragment {

    android.support.v7.app.AlertDialog alertNewWorkAcc1;
    private ListView lstWorks;

    private ArrayAdapter adapterWorks;
    private SharedPreferences mainShared;
    private List<String> listWorks, listWorksAlert, listSubjects;
    private List<Integer> listIDPost;
    private int IDTech = 0, IDPost = 0, haveJob = 0;
    private String Skills = "", State = "";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fr_tech_newworks, container, false);
        mainShared = getActivity().getSharedPreferences("polTech", 0);
        haveJob = mainShared.getInt("HaveJob_Tech", 0);

        lstWorks = view.findViewById(R.id.lstWorks_NewWorks);
        DontHaveJob();

        return view;
    }


    // DONOT HAVE JOB *********************************************************
    private void DontHaveJob() {

        IDTech = mainShared.getInt("ID_Tech", 0);
        Skills = mainShared.getString("SkillName_Tech", "خالی");
        State = mainShared.getString("StateName_Tech", "خالی");

        listWorks = new ArrayList<>();
        listWorksAlert = new ArrayList<>();
        listSubjects = new ArrayList<>();
        listIDPost = new ArrayList<>();


        new connect_Works(getString(R.string.LinkWorks), ishowWorksRes, Skills, State).execute();


        lstWorks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                alertDilogNewWorkACC1(listWorksAlert.get(position) + "");

/*
                Intent intent = new Intent(getContext(), Activity_NewWork_2.class);
                intent.putExtra("KEYNewWork_2", listWorksAlert.get(position) + "");
                startActivity(intent);
                getActivity().finish();
                */
            }
        });

    }

    private void alertDilogNewWorkACC1(String data) {

        android.support.v7.app.AlertDialog.Builder builder_Acc1 = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.DialogWithAnim);
        LinearLayout linear_Acc1 = (LinearLayout) getLayoutInflater().inflate(R.layout.custom_listview_newwork, null, false);

        TextView txtSubjectsDoing_thisWork = linear_Acc1.findViewById(R.id.txtSubjectsDoing_thisWork);
        TextView txtDate_FrThisWork = linear_Acc1.findViewById(R.id.txtDate_FrThisWork);
        TextView txtTime_FrThisWork = linear_Acc1.findViewById(R.id.txtTime_FrThisWork);
        TextView txtFnameLname_FrThisWork = linear_Acc1.findViewById(R.id.txtFnameLname_FrThisWork);
        TextView txtPhone_FrThisWork = linear_Acc1.findViewById(R.id.txtPhone_FrThisWork);
        TextView txtAddress_FrThisWork = linear_Acc1.findViewById(R.id.txtAddress_FrThisWork);
        TextView txtTXT_FrThisWork = linear_Acc1.findViewById(R.id.txtTXT_FrThisWork);

        Button btnlst_CancelAlertACC1 = linear_Acc1.findViewById(R.id.btnlst_CancelAlertNewWorkACC1);
        Button btnlst_getWorkAlertACC1 = linear_Acc1.findViewById(R.id.btnlst_getWorkAlertACC1);

        String[] NewWork_2Arr = {};
        NewWork_2Arr = data.split("/");
        IDPost = Integer.parseInt(NewWork_2Arr[10]);

        txtDate_FrThisWork.setText(NewWork_2Arr[0] + "\t" + NewWork_2Arr[1] + "/" + NewWork_2Arr[2] + "/" + NewWork_2Arr[3]);
        txtTime_FrThisWork.setText(NewWork_2Arr[4] + "");
        txtFnameLname_FrThisWork.setText(NewWork_2Arr[5] + " " + NewWork_2Arr[6]);
        txtAddress_FrThisWork.setText(NewWork_2Arr[7] + "");
        txtPhone_FrThisWork.setText(NewWork_2Arr[8] + "");
        txtTXT_FrThisWork.setText(NewWork_2Arr[9] + "");
        txtSubjectsDoing_thisWork.setText(NewWork_2Arr[11] + "");


        btnlst_CancelAlertACC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertNewWorkAcc1.dismiss();
            }
        });

        btnlst_getWorkAlertACC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haveJob = mainShared.getInt("HaveJob_Tech", 0);
                if (haveJob == 1) {
                    Toast.makeText(getContext(), "شما نمیتونید کار جدیدی رو بگیرید", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = mainShared.edit();
                    editor.putInt("HaveJob_Tech", 0);
                    editor.putInt("reqID_Tech", 0);
                    editor.apply();

                    new connect_AccOne(getString(R.string.LinkAcceptOne), iAcceptOne, IDTech + "",IDPost+"").execute();

                }

            }
        });

        builder_Acc1.setView(linear_Acc1);
        alertNewWorkAcc1 = builder_Acc1.create();
        alertNewWorkAcc1.getWindow().setGravity(Gravity.BOTTOM);
        alertNewWorkAcc1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertNewWorkAcc1.show();


    }

    connect_AccOne.IshowAccOneRes iAcceptOne = new connect_AccOne.IshowAccOneRes() {
        @Override
        public void AccOneTechResult(String res) {
            Toast.makeText(getContext(), res + "", Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = mainShared.edit();
            editor.putInt("HaveJob_Tech", 1);
            editor.putInt("reqID_Tech", IDPost);
            editor.apply();

            alertNewWorkAcc1.dismiss();
        }
    };


    // show new Jobs *********************************************************
    connect_Works.IshowWorksRes ishowWorksRes = new connect_Works.IshowWorksRes() {
        @Override
        public void WorksResult(String res) {

            GetJsonWorks(res);
            adapterWorks = new ArrayAdapter(getContext(), R.layout.custom_listview_works, listWorks) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    convertView = getLayoutInflater().inflate(R.layout.custom_listview_works, parent, false);
                    TextView txtlstWorks = convertView.findViewById(R.id.txtlstWorks);
                    TextView txtlstSubjects = convertView.findViewById(R.id.txtlstSubjects);
                    txtlstWorks.setText(listWorks.get(position));
                    txtlstSubjects.setText(listSubjects.get(position));
                    return convertView;
                }
            };

            lstWorks.setAdapter(adapterWorks);
        }
    };


    //GetJson *****************************************************************************
    private void GetJsonWorks(String res) {
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

                listSubjects.add(Subject);
                listIDPost.add(IDPost);

                listWorks.add("" + "تاریخ: " + NameWeek + "\t" + DateDay + "/" + DateMonth + "/" + DateYear + "\n" + "بازه زمانی: " + PeriodTime + "\n");

                listWorksAlert.add(NameWeek + "/" + DateDay + "/" + DateMonth + "/" + DateYear + "/" + PeriodTime + "/" + FirstName + "/" + LastName + "/" + Address + "/" + PhoneNum + "/" + txt + "/" + IDPost + "/" + Subject);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}