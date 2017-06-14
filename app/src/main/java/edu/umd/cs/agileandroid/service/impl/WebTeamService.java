package edu.umd.cs.agileandroid.service.impl;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.agileandroid.service.TeamService;

/**
 * Created by Beijie on 4/6/2017.
 */

public class WebTeamService implements TeamService{
    HttpURLConnection connection;
    String jsonString;
    @Override
    public List<String> getDefinitionOfDone(String teamId) {
        List<String> dod = new ArrayList<String>();
        try {
            String urlString = Uri.parse("http://www.csfalcon.com/team/dod").buildUpon()
                    .appendQueryParameter("teamId", teamId).build().toString();
            URL url = new URL(urlString);
//           URL url = new URL("http://vertigo.cs.umd.edu/test.txt");
            connection = (HttpURLConnection) url.openConnection();

            connection.connect();
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();

                int bytesRead = 0;

                byte[] buffer = new byte[1024];

                while ((bytesRead = inputStream.read(buffer)) >0) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();

                jsonString = new String(outputStream.toByteArray());

            } finally {
                connection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        JSONArray json = null;
        try {
            json = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            for (int i = 0; i < json.length(); i++) {
                try {
                    dod.add(json.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        return dod;
    }

    @Override
    public String getTeamReminder(String teamId) {
        return "Candor, Execution, Continuous Improvement";
    }

}
