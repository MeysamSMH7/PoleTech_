package com.pol.poletech.connectClasses;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class connect_Acc2 extends AsyncTask {

    public String link = "";
    public String AcceptTwo = "";
    public String Price = "";
    public String PeriodWork = "";
    public String HaveJob = "";
    public String IDTech = "";
    private connect_Acc2.IshowAcc2Res _IAcc2TechResult;
    StringBuilder stringBuilder;

    public connect_Acc2(String link, connect_Acc2.IshowAcc2Res result, String AcceptTwo, String Price, String PeriodWork, String HaveJob, String IDTech) {
        this.link = link;
        this.AcceptTwo = AcceptTwo;
        this.Price = Price;
        this.PeriodWork = PeriodWork;
        this.HaveJob = HaveJob;
        this.IDTech = IDTech;
        _IAcc2TechResult = result;
    }

    public interface IshowAcc2Res {
        public void Acc2TechResult(String res);
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            String sendData = URLEncoder.encode("AcceptTwo", "UTF8") + "=" + URLEncoder.encode(AcceptTwo, "UTF8");
            sendData += "&" + URLEncoder.encode("Price", "UTF8") + "=" + URLEncoder.encode(Price, "UTF8");
            sendData += "&" + URLEncoder.encode("PeriodWork", "UTF8") + "=" + URLEncoder.encode(PeriodWork, "UTF8");
            sendData += "&" + URLEncoder.encode("HaveJob", "UTF8") + "=" + URLEncoder.encode(HaveJob, "UTF8");
            sendData += "&" + URLEncoder.encode("IDTech", "UTF8") + "=" + URLEncoder.encode(IDTech, "UTF8");

            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(sendData);

            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String data = null;

            while ((data = reader.readLine()) != null) {
                stringBuilder.append(data);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        try {
            if (_IAcc2TechResult != null) {
                _IAcc2TechResult.Acc2TechResult(stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
