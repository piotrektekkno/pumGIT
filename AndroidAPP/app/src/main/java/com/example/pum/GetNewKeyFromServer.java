package com.example.pum;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetNewKeyFromServer extends AsyncTask<Void,Void,Void> {

    String data = "";
    String singleParsed = "";
    String idxKey;
    Activity actv;
    String user;
    String urlPath = new ConnectionParam().geNewKeyFromServerPath();

    GetNewKeyFromServer(String idxKey, Activity actv, String user){
        this.idxKey = idxKey;
        this.actv = actv;
        this.user = user;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(urlPath + user);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputSream = httpUrlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputSream));
            String line ="";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for(int i = 0; i <JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                singleParsed = (String) JO.get("newKey");

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(this.singleParsed.length() == 10) {
            SettingsActivityKeys.tmpGetyKey.setText(this.singleParsed);
            SettingsActivityKeys.manageStorageData.setKeyVal(this.idxKey, this.singleParsed);

        } else {
            Toast.makeText(actv, "Problem z wygenerowaniem klucza",
                    Toast.LENGTH_LONG).show();
        }

    }
}
