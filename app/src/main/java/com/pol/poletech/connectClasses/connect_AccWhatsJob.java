package com.pol.poletech.connectClasses;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class connect_AccWhatsJob extends AsyncTask {

    public String link = "";
    public String IDPost = "";

    private connect_AccWhatsJob.IshowAccWhatsJobRes _IAccWhatsJobTechResult;
    StringBuilder stringBuilder;

    public connect_AccWhatsJob(String link, connect_AccWhatsJob.IshowAccWhatsJobRes result, String IDPost) {
        this.link = link;
        this.IDPost = IDPost;
        _IAccWhatsJobTechResult = result;
    }

    public interface IshowAccWhatsJobRes {
        public void AccWhatsJobTechResult(String res);
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            String sendData = URLEncoder.encode("IDPost", "UTF8") + "=" + URLEncoder.encode(IDPost, "UTF8");

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
            if (_IAccWhatsJobTechResult != null) {
                _IAccWhatsJobTechResult.AccWhatsJobTechResult(stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
