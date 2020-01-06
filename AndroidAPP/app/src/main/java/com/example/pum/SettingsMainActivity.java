package com.example.pum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.pawch.R;



public class SettingsMainActivity extends AppCompatActivity {
    private ManageStorageData manageStorageData;

    private Switch soundSwtch;
    private Switch vibSwtch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);
        manageStorageData = new ManageStorageData(SettingsMainActivity.this);

        soundSwtch = (Switch)  findViewById(R.id.SoundSwitch);
        vibSwtch = (Switch)  findViewById(R.id.vibSwitch);

        soundSwtch.setChecked(manageStorageData.getSoundSetting());
        vibSwtch.setChecked(manageStorageData.getVibSetting());

        soundSwtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    manageStorageData.setSoundSetting("Y");
                else
                    manageStorageData.setSoundSetting("N");
            }
        });

        vibSwtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    manageStorageData.setVibSetting("Y");
                else
                    manageStorageData.setVibSetting("N");
            }
        });

    }
}
