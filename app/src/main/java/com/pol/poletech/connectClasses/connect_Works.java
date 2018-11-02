package com.pol.poletech.connectClasses;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class connect_Works extends AsyncTask {

    public String link = "";
    public String Skill = "";
    public String State = "";
    private connect_Works.IshowWorksRes _IWorksResult;
    StringBuilder stringBuilder;

    public connect_Works(String link, connect_Works.IshowWorksRes result, String Skill, String State) {
        this.link = link;
        this.Skill = Skill;
        this.State = State;
        _IWorksResult = result;
    }

    public interface IshowWorksRes {
        public void WorksResult(String res);
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            String sendData = URLEncoder.encode("Skill", "UTF8") + "=" + URLEncoder.encode(Skill, "UTF8");
            sendData += "&" + URLEncoder.encode("State", "UTF8") + "=" + URLEncoder.encode(State, "UTF8");

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
            if (_IWorksResult != null) {
                _IWorksResult.WorksResult(stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
