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

public class GetConversationForKey extends AsyncTask<Void,Void,Void> {

    String data = "";
    String singleParsed = "";
    String dataParsed = "";
    String idxKey;
    Activity actv;




    GetConversationForKey(String idxKey, Activity actv, String user){
        this.idxKey = idxKey;
        this.actv = actv;

    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            MainActivity.convRows =0;
            URL url = new URL("http://46.41.139.170:3018/getConv?keyConv=" + idxKey);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputSream = httpUrlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputSream));
            String line ="";
            String tmpUser;
            String tmpConv;
            String tmpTime;

            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for(int i = 0; i <JA.length(); i++){
                if(i > 30){
                    break; // ostatnie 30 wiqdomosci
                }
                JSONObject JO = (JSONObject) JA.get(i);

                tmpUser = (String) JO.get("CREATEDBY");
                tmpConv = (String) JO.get("CONTEXT");
                tmpTime = (String) JO.get("CREATETS");

                tmpTime = tmpTime.replace("T", " ").replace(".000Z", "");
                singleParsed = (String) JO.get("CONTEXT")  + " " + (String) JO.get("CREATEDBY") + " " + (String) JO.get("CREATETS");

                ConvClass tmpCnvObj = new ConvClass(tmpUser,tmpConv,tmpTime);
                MainActivity.convObjArray[i] = tmpCnvObj;
                MainActivity.convRows++;
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

        Toast.makeText(actv, "Odwieżanie \n pobrano wpisów: " + MainActivity.convRows,
                Toast.LENGTH_LONG).show();
        MainActivity.SetConvTxt((byte) 0);

        if(MainActivity.convRows == 0) {
            Toast.makeText(actv, "Nie można pobrać rozmowy dla klucza: " + idxKey,
                    Toast.LENGTH_LONG).show();
        }

    }


}
