package fr.enssat.alhadad_ngameni.enrichedvideo;

/**
 * Created by zaid on 30/11/17.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONParser {

    static BufferedInputStream in = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    private String get(String urlString) {
// Making HTTP request
        try {
            // defaultHttpClient
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(in);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "n");
                }
                in.close();
                json = sb.toString();
            } catch (Exception e) {
                Log.e("chaka", e.getMessage());
            } finally {
                urlConnection.disconnect();
            }

        } catch (UnsupportedEncodingException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (IOException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return json;
    }

    public JSONObject getJSONFromUrl(String urlString) {
        JSONObject jObj = null;
        String json = this.get(urlString);
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }

    public JSONArray getJSONArrayFromUrl(String url) {
        String json = this.get(url);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jsonArray;
    }
}