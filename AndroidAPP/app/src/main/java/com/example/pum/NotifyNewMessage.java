package com.example.pum;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;


public class NotifyNewMessage {
    private Context appCtx;
    private ManageStorageData appSettings;
    private Activity actv;

    NotifyNewMessage(Context appCtx, Activity activity){
        this.appCtx = appCtx;
        this.actv = activity;
        appSettings = new ManageStorageData(activity);
    }

    void runNotify(){
        if(appSettings.getSoundSetting())
            playRingtone();

        if(appSettings.getVibSetting())
            runVibrate();
    }
    void playRingtone() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(appCtx, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void runVibrate(){
        Vibrator v = (Vibrator) appCtx.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

    }

}
