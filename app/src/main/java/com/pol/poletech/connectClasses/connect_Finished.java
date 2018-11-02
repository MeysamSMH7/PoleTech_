package com.pol.poletech.connectClasses;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class connect_Finished extends AsyncTask {

    public String link = "";
    public String IDPost = "";
    public String ChengePrice = "";
    public String ChengePriceReason = "";
    private connect_Finished.IshowFinishedRes _IFinishedTechResult;
    StringBuilder stringBuilder;

    public connect_Finished(String link, connect_Finished.IshowFinishedRes result, String IDPost, String ChengePrice, String ChengePriceReason) {
        this.link = link;
        this.IDPost = IDPost;
        this.ChengePrice = ChengePrice;
        this.ChengePriceReason = ChengePriceReason;
        _IFinishedTechResult = result;
    }

    public interface IshowFinishedRes {
        public void FinishedTechResult(String res);
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            String sendData = URLEncoder.encode("IDPost", "UTF8") + "=" + URLEncoder.encode(IDPost, "UTF8");
            sendData += "&" + URLEncoder.encode("ChengePrice", "UTF8") + "=" + URLEncoder.encode(ChengePrice, "UTF8");
            sendData += "&" + URLEncoder.encode("ChengePriceReason", "UTF8") + "=" + URLEncoder.encode(ChengePriceReason, "UTF8");

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
            if (_IFinishedTechResult != null) {
                _IFinishedTechResult.FinishedTechResult(stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
