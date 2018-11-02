package com.pol.poletech.connectClasses;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class connect_AccOne extends AsyncTask {

    public String link = "";
    public String IDTech = "";
    public String IDPost = "";
    private connect_AccOne.IshowAccOneRes _IAccOneTechResult;
    StringBuilder stringBuilder;

    public connect_AccOne(String link, connect_AccOne.IshowAccOneRes result, String IDTech, String IDPost) {
        this.link = link;
        this.IDTech = IDTech;
        this.IDPost = IDPost;
        _IAccOneTechResult = result;
    }

    public interface IshowAccOneRes {
        public void AccOneTechResult(String res);
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            String sendData = URLEncoder.encode("IDTech", "UTF8") + "=" + URLEncoder.encode(IDTech, "UTF8");
            sendData += "&" + URLEncoder.encode("IDPost", "UTF8") + "=" + URLEncoder.encode(IDPost, "UTF8");

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
            if (_IAccOneTechResult != null) {
                _IAccOneTechResult.AccOneTechResult(stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
