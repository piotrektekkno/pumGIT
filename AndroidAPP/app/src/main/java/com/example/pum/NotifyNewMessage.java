package com.example.pum;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class NotifyNewMessage {
    private Context appCtx;
    NotifyNewMessage(Context appCtx){
        this.appCtx = appCtx;
    }
    public void PlayRingtone(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(appCtx, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
