package com.example.pawch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    public static TextView tmpGetyKey;
    TextView userNameTxt;
    public static TextView textKeyView0;
    public static TextView textKeyView1;
    public static TextView textKeyView2;
    public static TextView textKeyView3;
    public static TextView textKeyView4;

    EditText editTextKey;

    public static  ManageStorageData manageStorageData;
    String userName = "NA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        manageStorageData = new ManageStorageData(SettingsActivity.this);

        userNameTxt = (TextView) findViewById(R.id.txtUserName);

        textKeyView0 = (TextView)  findViewById(R.id.textKeyView0);
        textKeyView1 = (TextView)  findViewById(R.id.textKeyView1);
        textKeyView2 = (TextView)  findViewById(R.id.textKeyView2);
        textKeyView3 = (TextView)  findViewById(R.id.textKeyView3);
        textKeyView4 = (TextView)  findViewById(R.id.textKeyView4);

        editTextKey = (EditText) findViewById(R.id.editTextKey);


        editTextKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_GO) {
                    setKeyFromEditText(editTextKey);
                }
                return handled;
            }
        });


        refreshActivityFilleds();
    }

    void setKeyFromEditText(EditText edtTxt){

        String freeSlot ="-1";
        String keyVal ="";
        keyVal = edtTxt.getText().toString();
        if(keyVal.length() != 10) {
            //Perform your Actions here.
            Toast.makeText(SettingsActivity.this, "Klucz powinien zawierać 10 znaków",
                    Toast.LENGTH_LONG).show();
            return;
        }

        freeSlot = freeSlots();

        if(freeSlot.equals("-1")){
            Toast.makeText(SettingsActivity.this, "Brak pustego miejsca dla nowego klucza",
                    Toast.LENGTH_LONG).show();
            return;
        }

        manageStorageData.setKeyVal(freeSlot, keyVal);
        refreshActivityFilleds();

    }

    void refreshActivityFilleds(){

        String[] txtArray = manageStorageData.getKeyValArray();
        userName =  manageStorageData.getUserName();
        userNameTxt.setText("Nazwa użytkownika:\n" +
                             userName + "\n" +
                            "Używany klucz: " +
                             manageStorageData.getActualUsedKey().replace("NOTSET","Pusty")
        );


        textKeyView0.setText(txtArray[0].replace("NOTSET","Pusty"));
        textKeyView1.setText(txtArray[1].replace("NOTSET","Pusty"));
        textKeyView2.setText(txtArray[2].replace("NOTSET","Pusty"));
        textKeyView3.setText(txtArray[3].replace("NOTSET","Pusty"));
        textKeyView4.setText(txtArray[4].replace("NOTSET","Pusty"));

    }

    String freeSlots(){
        String slot ="-1";

        if(String.valueOf(textKeyView0.getText()).equals("Pusty")) {
            slot = "0";
            tmpGetyKey = textKeyView0;
        }

        if(String.valueOf(textKeyView1.getText()).equals("Pusty")) {
            slot = "1";
            tmpGetyKey = textKeyView1;
        }

        if(String.valueOf(textKeyView2.getText()).equals("Pusty")) {
            slot = "2";
            tmpGetyKey = textKeyView2;
        }

        if(String.valueOf(textKeyView3.getText()).equals("Pusty")) {
            slot = "3";
            tmpGetyKey = textKeyView3;
        }

        if(String.valueOf(textKeyView4.getText()).equals("Pusty")) {
            slot = "4";
            tmpGetyKey = textKeyView4;
        }

        return slot;
    }

    public void ManageButtons(View view) {

        int btnId = ((Button) view).getId();

        String freeSlot = "-1";

        if (btnId == R.id.btnRemoveKey0) manageStorageData.setKeyVal("0", "NOTSET");
        if (btnId == R.id.btnRemoveKey1) manageStorageData.setKeyVal("1", "NOTSET");
        if (btnId == R.id.btnRemoveKey2) manageStorageData.setKeyVal("2", "NOTSET");
        if (btnId == R.id.btnRemoveKey3) manageStorageData.setKeyVal("3", "NOTSET");
        if (btnId == R.id.btnRemoveKey4) manageStorageData.setKeyVal("4", "NOTSET");

        if (btnId == R.id.btnSetKey0 && !String.valueOf(textKeyView0.getText()).equals("Pusty"))
            manageStorageData.setActualUsedKey(String.valueOf(textKeyView0.getText()));

        if (btnId == R.id.btnSetKey1 && !String.valueOf(textKeyView1.getText()).equals("Pusty"))
            manageStorageData.setActualUsedKey(String.valueOf(textKeyView1.getText()));

        if (btnId == R.id.btnSetKey2 && !String.valueOf(textKeyView2.getText()).equals("Pusty"))
            manageStorageData.setActualUsedKey(String.valueOf(textKeyView2.getText()));

        if (btnId == R.id.btnSetKey3 && !String.valueOf(textKeyView3.getText()).equals("Pusty"))
            manageStorageData.setActualUsedKey(String.valueOf(textKeyView3.getText()));

        if (btnId == R.id.btnSetKey4 && !String.valueOf(textKeyView4.getText()).equals("Pusty"))
            manageStorageData.setActualUsedKey(String.valueOf(textKeyView4.getText()));


        if (btnId == R.id.buttonGetNewKey){

             freeSlot = freeSlots();

            if(freeSlot.equals("-1")){
                Toast.makeText(SettingsActivity.this, "Brak pustego miejsca dla nowego klucza",
                        Toast.LENGTH_LONG).show();
                return;
            } else {
                GetNewKeyFromServer process = new GetNewKeyFromServer(freeSlot, SettingsActivity.this, userName);
                process.execute();
            }
        }

        refreshActivityFilleds();
    }
}
