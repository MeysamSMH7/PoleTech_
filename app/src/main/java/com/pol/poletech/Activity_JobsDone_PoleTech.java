package com.pol.poletech;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pol.poletech.connectClasses.connect_AccOne;
import com.pol.poletech.connectClasses.connect_Works;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_JobsDone_PoleTech extends AppCompatActivity {

    private ListView lstJobsDone;
    private TextView txtNoJobsDone;

    private AlertDialog ShowWorksAlert;
    private ArrayAdapter adapterWorks;
    private SharedPreferences mainShared;
    private int IDTech = 0;
    private List<String> listWorks, listWorksAlert1, listWorksAlert2, listWorksAlert3, listSubjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobsdone_poletech);
        mainShared = getSharedPreferences("polTech", 0);
        IDTech = mainShared.getInt("ID_Tech", 0);

        lstJobsDone = findViewById(R.id.lstJobsDone);
        txtNoJobsDone = findViewById(R.id.txtNoJobsDone);

        listWorks = new ArrayList<>();
        listWorksAlert1 = new ArrayList<>();
        listWorksAlert2 = new ArrayList<>();
        listWorksAlert3 = new ArrayList<>();
        listSubjects = new ArrayList<>();

        new connect_AccOne(getString(R.string.LinkJobsDone), ishowAccOneRes, IDTech + "", "").execute();


        lstJobsDone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder_Works = new AlertDialog.Builder(Activity_JobsDone_PoleTech.this);
                LinearLayout layout_Works = (LinearLayout) getLayoutInflater().inflate(R.layout.custom_listview_jobsdone, null, false);
                TextView txtlstWorksAlert1 = layout_Works.findViewById(R.id.txtlstWorksAlert1);
                TextView txtlstWorksAlert2 = layout_Works.findViewById(R.id.txtlstWorksAlert2);
                TextView txtlstWorksAlert3 = layout_Works.findViewById(R.id.txtlstWorksAlert3);

                TextView txtlstSubjectsAlet = layout_Works.findViewById(R.id.txtlstSubjectsAlet);
                Button btnlst_CancelAlert = layout_Works.findViewById(R.id.btnlst_CancelAlert);

                txtlstWorksAlert1.setText(listWorksAlert1.get(position) + "");
                txtlstWorksAlert2.setText(listWorksAlert2.get(position) + "");
                txtlstWorksAlert3.setText(listWorksAlert3.get(position) + "");

                txtlstSubjectsAlet.setText(listSubjects.get(position) + "");

                btnlst_CancelAlert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowWorksAlert.dismiss();
                    }
                });


                builder_Works.setView(layout_Works);
                ShowWorksAlert = builder_Works.create();
                ShowWorksAlert.show();


            }
        });
    }

    connect_AccOne.IshowAccOneRes ishowAccOneRes = new connect_AccOne.IshowAccOneRes() {
        @Override
        public void AccOneTechResult(String res) {

            if (res.contains("[]")) {
                txtNoJobsDone.setVisibility(View.VISIBLE);
                lstJobsDone.setVisibility(View.GONE);
            } else {
                txtNoJobsDone.setVisibility(View.GONE);
                lstJobsDone.setVisibility(View.VISIBLE);
                GetJsonJobsDone(res);

                adapterWorks = new ArrayAdapter(Activity_JobsDone_PoleTech.this, R.layout.custom_listview_works, listWorks) {
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

                lstJobsDone.setAdapter(adapterWorks);


            }

        }
    };

    private void GetJsonJobsDone(String res) {
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
                int MetodPaimant = object.getInt("MetodPaimant");


                listSubjects.add(Subject);

                listWorks.add("" +
                        "موضوع: " + Subject + "\n" +
                        "تاریخ: " + NameWeek + "\t" + DateDay + "/" + DateMonth + "/" + DateYear + "\n");

                listWorksAlert1.add("" +
                        "اطلاعات کارفرما" + "\n" +
                        FirstName + " " + LastName + "\t" + "تلفن: " + PhoneNum + "\n" +
                        "آدرس: " + Address + "\n" +
                        "درخواست: " + txt + "\n" +
                        "زمان مراجعه: " + PeriodTime + "\n\n" +
                        "اطلاعات کارفرما" + "\n" +
                        "قیمت اولیه: " + Price + " تومان" + "\n" +
                        "مدت انجام کار: " + PeriodWork + " ساعت");


                if (ChengePrice > 0) {
                    listWorksAlert2.add(
                            "تغییر قیمت: " + "دارد" + "\n" +
                                    "نوع تغییر: " + "افزایشی" + "\n" +
                                    "مبلغ تغییر یافته: " + ChengePrice + "تومان" + "\n" +
                                    "علت تغییر قیمت: " + ChengePriceReason);
                } else if (ChengePrice < 0) {
                    listWorksAlert2.add(
                            "تغییر قیمت: " + "دارد" + "\n" +
                                    "نوع تغییر: " + "کاهشی" + "\n" +
                                    "مبلغ تغییر یافته: " + ChengePrice + "تومان" + "\n" +
                                    "علت تغییر قیمت: " + ChengePriceReason);
                } else {
                    listWorksAlert2.add("تغییر قیمت: " + "ندارد");
                }


                if (MetodPaimant == 0) {
                    listWorksAlert3.add(
                            "مبلغ نهایی: " + finalPrice + "تومان" + "\n" +
                                    "نوع پرداخت: " + "نقدی" + "\n" +
                                    "وضعیت پرداخت: " + "تسویه حساب کامل");
                } else {
                    listWorksAlert3.add(
                            "مبلغ نهایی: " + finalPrice + "تومان" + "\n" +
                                    "نوع پرداخت: " + "آنلاین" + "\n" +
                                    "وضعیت پرداخت: " + "تسویه حساب کامل");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
