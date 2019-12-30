package com.example.pum;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SendMessageByPost extends AsyncTask<Void,Void,Void> {

    String data = "";
    String res = "";
    String idxKey;
    Activity actv;
    String user;
    String msg;

    SendMessageByPost(String idxKey, Activity actv, String user, String  msg){
        this.idxKey = idxKey;
        this.actv = actv;
        this.user = user;
        this.msg = msg;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        String urlString = "http://46.41.139.170:3018/addMesage";
        URL url = null;
        InputStream stream = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String data = URLEncoder.encode("who", "UTF-8")
                    + "=" + URLEncoder.encode(user, "UTF-8");

            data += "&" + URLEncoder.encode("msg", "UTF-8") + "="
                    + URLEncoder.encode(msg, "UTF-8");

            data += "&" + URLEncoder.encode("keyconv", "UTF-8") + "="
                    + URLEncoder.encode(idxKey, "UTF-8");

            urlConnection.connect();

            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(data);
            wr.flush();

            stream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
            res  = reader.readLine();



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(this.res.equals("OK")) {
            //GetConversationForKey  process = new GetConversationForKey(idxKey, actv, user);
        } else {
            Toast.makeText(actv, "Problem z wyslaniem konwersacji" + res,
                    Toast.LENGTH_LONG).show();
        }

    }
}
