package com.example.pum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawch.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView txtDetail;
    public static TextView row0Conv;
    public static TextView row1Conv;
    public static TextView row2Conv;
    public static TextView row3Conv;
    public static TextView row4Conv;
    public static TextView row5Conv;

    EditText editTextKey;
    Button buttonRefresh;
    public static ConvClass[] convObjArray = new ConvClass[30];
    public static byte convRows = 0;
    private byte rowFactor = 0;
    String actUserKey = "";
    String actUser = "";
    String orignalBtnRefresText = "";
    ManageStorageData manageStorageData;
    int refresData = 0;
    int refresBySeconds = 15;
    Timer timer = new Timer();

    NotifyNewMessage playDefSound;

    @Override
    protected void onResume() {
        super.onResume();
        getConvAndRefresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manageStorageData = new ManageStorageData(MainActivity.this);
        txtDetail = (TextView)  findViewById(R.id.textUserDetails);

        //refreshDetailUserKey();

        row0Conv = (TextView)  findViewById(R.id.txtConv0);
        row1Conv = (TextView)  findViewById(R.id.txtConv1);
        row2Conv = (TextView)  findViewById(R.id.txtConv2);
        row3Conv = (TextView)  findViewById(R.id.txtConv3);
        row4Conv = (TextView)  findViewById(R.id.txtConv4);
        row5Conv = (TextView)  findViewById(R.id.txtConv5);

        editTextKey = (EditText) findViewById(R.id.writtenText);

        buttonRefresh = (Button) findViewById(R.id.btnRefresh);
        orignalBtnRefresText = (String) buttonRefresh.getText();

        playDefSound = new NotifyNewMessage(getApplicationContext(), MainActivity.this);

        getConvAndRefresh();

        timer.schedule(new UpdateTimeTask(),1,1000);

    }

    class UpdateTimeTask extends TimerTask {
        private String lastTime;
        String time ="";
        String user ="";
        boolean orignalText = true;
        public void run()
        {
            buttonRefresh.setText(orignalBtnRefresText + " " + String.valueOf(refresData));
            refresData--;
            if(refresData <= 0){
                getConvAndRefresh();
                refresData = refresBySeconds;
                //New message ?

                if(convObjArray[0] != null) {
                    user = convObjArray[0].getConvUser();
                    time = convObjArray[0].getTime();
                }

                if(!user.equals(actUser) && !time.equals(lastTime)){
                    playDefSound.runNotify();
                    lastTime = time;
                }


            }
        }
    }


    static String GetIdxConvString(byte i){
        if (convRows > i )
            return convObjArray[i].getConvUser() + " " +
                    convObjArray[i].getTime() + "\n" +
                    convObjArray[i].getConvTxt();

        return " ";
    }

    static void SetConvTxt(byte row){
        byte idx = row;

        idx = row;
        row0Conv.setText(GetIdxConvString(idx));
        idx++;
        row1Conv.setText(GetIdxConvString(idx));
        idx++;
        row2Conv.setText(GetIdxConvString(idx));
        idx++;
        row3Conv.setText(GetIdxConvString(idx));
        idx++;
        row4Conv.setText(GetIdxConvString(idx));
        idx++;
        row5Conv.setText(GetIdxConvString(idx));

    }

    void refreshDetailUserKey(){
        actUserKey = manageStorageData.getActualUsedKey().replace("NOTSET", "Pusty");
        refresBySeconds = manageStorageData.getRefreshTime();
        actUser = manageStorageData.getUserName();
        txtDetail.setText("Użytkownik: " + actUser  +
                "\nKlucz: " + actUserKey );
        rowFactor = 0;
    }

    void getConvAndRefresh(){
        refreshDetailUserKey();
        refresData = refresBySeconds;
        GetConversationForKey  process = new GetConversationForKey(actUserKey, MainActivity.this, actUser);

    }



    public void ManageButtons(View view) {

        int btnId = ((Button)view).getId();
        //Std version
        if(btnId ==  R.id.btnRefresh) {
            getConvAndRefresh();
        }

        if(btnId ==  R.id.btnSettings) {

            startActivity(new Intent(this, SettingsActivityKeys.class));
        }

        if(btnId ==  R.id.btnResSettings) {

            startActivity(new Intent(this, SettingsMainActivity.class));
        }

        if(btnId ==  R.id.btnSend) {


            String tmp = String.valueOf(editTextKey.getText());
            if(tmp.length() > 0) {
                SendMessageByPost process = new SendMessageByPost(actUserKey, MainActivity.this, actUser, tmp);
                process.execute();
                editTextKey.setText("");
                refresData = 3;
            }
        }
    }

    public void onClikTexView(View view){
        int txtView = ((TextView)view).getId();
        if(txtView == R.id.txtConv0) {

            if(rowFactor > 0)
                rowFactor--;
            SetConvTxt(rowFactor);
        }
        if(txtView == R.id.txtConv5) {

            if(rowFactor < 30 && (rowFactor + 5) < convRows) {
                rowFactor++;
                SetConvTxt(rowFactor);

            }

        }
    }
}
