package com.pol.poletech.Fragments;



import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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


import com.pol.poletech.Activity_Works_PoleTech;
import com.pol.poletech.R;
import com.pol.poletech.connectClasses.connect_AccOne;
import com.pol.poletech.connectClasses.connect_Works;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Tab_main_PolUser extends Fragment {

    AlertDialog ShowWorksAlert;
    ListView lstWorks;
    ArrayAdapter adapterWorks;
    List<String> listWorks, listWorksAlert;
    List<Integer> listIDPost;
    int IDTech = 0;

    String Skills = "", State = "";

    SharedPreferences WorksSP;
    int haveJob = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ft_main_layout_poltech, container, false);

        WorksSP = getActivity().getSharedPreferences("polTech", 0);
        haveJob = WorksSP.getInt("HaveJob_Tech", 0);

        if (haveJob == 0){

            IDTech = WorksSP.getInt("ID_Tech", 0);
            Skills = WorksSP.getString("SkillName_Tech", "خالی");
            State = WorksSP.getString("StateName_Tech", "خالی");

            listWorks = new ArrayList<>();
            listWorksAlert = new ArrayList<>();
            listIDPost = new ArrayList<>();



            new connect_Works(getString(R.string.LinkWorks), ishowWorksRes, Skills, State).execute();

            lstWorks = view.findViewById(R.id.lstWorks);
            lstWorks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                    AlertDialog.Builder builder_Works = new AlertDialog.Builder(getContext());
                    LinearLayout layout_Works = (LinearLayout) getLayoutInflater().inflate(R.layout.custom_listview_worksalert, null, false);
                    TextView txtlstWorksAlert = layout_Works.findViewById(R.id.txtlstWorksAlert);
                    Button btnlst_CancelAlert = layout_Works.findViewById(R.id.btnlst_CancelAlert);
                    Button btnlst_getWorkAlert = layout_Works.findViewById(R.id.btnlst_getWorkAlert);

                    txtlstWorksAlert.setText(listWorksAlert.get(position) + "");

                    btnlst_CancelAlert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShowWorksAlert.dismiss();
                        }
                    });

                    btnlst_getWorkAlert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new connect_AccOne(getString(R.string.LinkAcceptOne), iAcceptOne, IDTech + "", listIDPost.get(position) + "").execute();
                            ShowWorksAlert.dismiss();
                            //finish();
                        }
                    });

                    builder_Works.setView(layout_Works);
                    ShowWorksAlert = builder_Works.create();
                    ShowWorksAlert.show();


                }
            });

        }else {

        }



        return view;
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
                    txtlstWorks.setText(listWorks.get(position));
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

                listWorks.add("" +
                        "موضوع: " + Subject + "\n" +
                        "تاریخ: " + DateDay + "/" + DateMonth + "/" + DateYear + "\t" + NameWeek + "\n" +
                        "بازه زمانی: " + PeriodTime + "\n");

                listWorksAlert.add("" +
                        "موضوع: " + Subject + "\n" +
                        "تاریخ: " + DateDay + "/" + DateMonth + "/" + DateYear + "\t" + NameWeek + "\n" +
                        "بازه زمانی: " + PeriodTime + "\n" +
                        "آدرس: " + Address + "\n" +
                        "متن درخواست: " + txt + "\n" +
                        "نام و نام خانوادگی درخواست دهنده: " + FirstName + " " + LastName + "\n" +
                        "شماره تلفن: " + PhoneNum + "\n");
                listIDPost.add(IDPost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}