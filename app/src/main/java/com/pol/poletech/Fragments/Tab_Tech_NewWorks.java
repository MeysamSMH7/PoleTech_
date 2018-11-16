package com.pol.poletech.Fragments;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.pol.poletech.R;
import com.pol.poletech.connectClasses.connect_AccOne;
import com.pol.poletech.connectClasses.connect_Works;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tab_Tech_NewWorks extends Fragment {

    private AlertDialog ShowWorksAlert;
    private ListView lstWorks;

    private ArrayAdapter adapterWorks;
    private SharedPreferences mainShared;
    private List<String> listWorks, listWorksAlert, listSubjects;
    private List<Integer> listIDPost;
    private int IDTech = 0, IDPost = 0, haveJob = 0;
    private String Skills = "", State = "";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tech_newworks, container, false);
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


                AlertDialog.Builder builder_Works = new AlertDialog.Builder(getContext());
                LinearLayout layout_Works = (LinearLayout) getLayoutInflater().inflate(R.layout.custom_listview_worksalert, null, false);
                TextView txtlstWorksAlert = layout_Works.findViewById(R.id.txtlstWorksAlert);
                TextView txtlstSubjectsAlet = layout_Works.findViewById(R.id.txtlstSubjectsAlet);
                Button btnlst_CancelAlert = layout_Works.findViewById(R.id.btnlst_CancelAlert);
                Button btnlst_getWorkAlert = layout_Works.findViewById(R.id.btnlst_getWorkAlert);

                txtlstWorksAlert.setText(listWorksAlert.get(position) + "");
                txtlstSubjectsAlet.setText(listSubjects.get(position) + "");

                btnlst_CancelAlert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowWorksAlert.dismiss();
                    }
                });

                btnlst_getWorkAlert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        haveJob = mainShared.getInt("HaveJob_Tech", 0);
                        if (haveJob == 1) {
                            Toast.makeText(getContext(), "شما نمیتونید کار جدیدی رو بگیرید", Toast.LENGTH_SHORT).show();
                        } else {

                            SharedPreferences.Editor editor = mainShared.edit();
                            editor.putInt("HaveJob_Tech", 0);
                            editor.putInt("reqID_Tech", 0);
                            editor.commit();

                            IDPost = listIDPost.get(position);
                            new connect_AccOne(getString(R.string.LinkAcceptOne), iAcceptOne, IDTech + "", IDPost + "").execute();
                            ShowWorksAlert.dismiss();

                            listWorks.clear();
                            listSubjects.clear();
                            adapterWorks.clear();
                            new connect_Works(getString(R.string.LinkWorks), ishowWorksRes, Skills, State).execute();


                        }
                    }
                });

                builder_Works.setView(layout_Works);
                ShowWorksAlert = builder_Works.create();
                ShowWorksAlert.show();


            }
        });

    }

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

    connect_AccOne.IshowAccOneRes iAcceptOne = new connect_AccOne.IshowAccOneRes() {
        @Override
        public void AccOneTechResult(String res) {
            Toast.makeText(getContext(), res + "", Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = mainShared.edit();
            editor.putInt("HaveJob_Tech", 1);
            editor.putInt("reqID_Tech", IDPost);
            editor.commit();

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

                listWorks.add("" +
                        "تاریخ: " + NameWeek + "\t" + DateDay + "/" + DateMonth + "/" + DateYear + "\n" +
                        "بازه زمانی: " + PeriodTime + "\n");

                listWorksAlert.add("" +
                        "تاریخ: " + NameWeek + "\t" + DateDay + "/" + DateMonth + "/" + DateYear + "\n" +
                        "بازه زمانی: " + PeriodTime + "\n" +
                        "نام درخواست دهنده: " + FirstName + " " + LastName + "\n" +
                        "آدرس: " + Address + "\n" +
                        "شماره تلفن: " + PhoneNum + "\n" +
                        "متن درخواست: " + txt + "\n");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}