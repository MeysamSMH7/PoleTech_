package com.pol.poletech.connectClasses;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class connect_Ticket extends AsyncTask {

    public String link = "";
    public String subject = "";
    public String text = "";
    public String Year = "";
    public String Month = "";
    public String Day = "";
    public String whoSent = "";
    public String userID = "";
    private connect_Ticket.IshowTicketRes _ITicketTechResult;
    StringBuilder stringBuilder;

    public connect_Ticket(String link, connect_Ticket.IshowTicketRes result, String subject, String text, String Year, String Month, String Day, String whoSent, String userID) {
        this.link = link;
        this.subject = subject;
        this.text = text;
        this.Year = Year;
        this.Month = Month;
        this.Day = Day;
        this.whoSent = whoSent;
        this.userID = userID;
        _ITicketTechResult = result;
    }

    public interface IshowTicketRes {
        public void TicketTechResult(String res);
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            String sendData = URLEncoder.encode("subject", "UTF8") + "=" + URLEncoder.encode(subject, "UTF8");
            sendData += "&" + URLEncoder.encode("text", "UTF8") + "=" + URLEncoder.encode(text, "UTF8");
            sendData += "&" + URLEncoder.encode("year", "UTF8") + "=" + URLEncoder.encode(Year, "UTF8");
            sendData += "&" + URLEncoder.encode("month", "UTF8") + "=" + URLEncoder.encode(Month, "UTF8");
            sendData += "&" + URLEncoder.encode("day", "UTF8") + "=" + URLEncoder.encode(Day, "UTF8");
            sendData += "&" + URLEncoder.encode("whoSent", "UTF8") + "=" + URLEncoder.encode(whoSent, "UTF8");
            sendData += "&" + URLEncoder.encode("userID", "UTF8") + "=" + URLEncoder.encode(userID, "UTF8");

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
            if (_ITicketTechResult != null) {
                _ITicketTechResult.TicketTechResult(stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
