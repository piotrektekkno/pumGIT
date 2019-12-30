package com.example.pawch;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Date;


public class ManageStorageData {
    SharedPreferences sharedPreferences;
    private String userName = "NONE";


    public ManageStorageData(Activity myActivity) {
        sharedPreferences = myActivity.getSharedPreferences("ComunicatorSettings", 0);
        String userName = sharedPreferences.getString("UserName", "NOTSET");
        if(userName.equals("NOTSET")){
            Date dt = new Date();

            this.userName =  Build.MANUFACTURER + "_" +
                    dt.getYear()  +
                    dt.getMonth() +
                    dt.getDay()  +
                    dt.getHours()  +
                    + dt.getMinutes();
            SharedPreferences.Editor spEditor = sharedPreferences.edit();
            spEditor.putString("UserName", this.userName);
            spEditor.commit();
            
        } else {
            this.userName = userName;
        }
    }

    String getUserName(){
      return userName;
    }

    String [] getKeyValArray(){
        String[] keyValArray = new String[5];
        keyValArray[0] = sharedPreferences.getString("keyVal0", "NOTSET");
        keyValArray[1] = sharedPreferences.getString("keyVal1", "NOTSET");
        keyValArray[2] = sharedPreferences.getString("keyVal2", "NOTSET");
        keyValArray[3] = sharedPreferences.getString("keyVal3", "NOTSET");
        keyValArray[4] = sharedPreferences.getString("keyVal4", "NOTSET");
        return keyValArray;
    }

    String getActualUsedKey(){
        String str;
        str = sharedPreferences.getString("ActualUsedKey", "NOTSET");
        return str;
    }

    void setKeyVal(String idx, String keyVal){
        String tmpKey = "keyVal" + idx;
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.putString(tmpKey, keyVal);
        spEditor.commit();
    }

    void setActualUsedKey(String keyVal){
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.putString("ActualUsedKey", keyVal);
        spEditor.commit();
    }
}
