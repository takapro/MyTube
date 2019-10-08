package com.example.mytube;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchTask extends AsyncTask<String, Void, JSONObject> {

    private static final String API = "https://www.googleapis.com/youtube/v3/search";
    private static final String PARAMS = "type=video&part=snippet&maxResults=10";
    private static final String KEY = BuildConfig.YOUTUBE_KEY;

    private MainActivity activity;

    public SearchTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        HttpURLConnection conn = null;
        try {
            String urlString = API + "?" + PARAMS + "&key=" + KEY + "&q=" + strings[0];
            conn = (HttpURLConnection) new URL(urlString).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            return new JSONObject(builder.toString());
        } catch (Exception e) {
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if (result != null) {
            activity.setResult(result);
        }
    }
}
