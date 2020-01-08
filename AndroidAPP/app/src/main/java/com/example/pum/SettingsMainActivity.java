package com.example.pum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.pawch.R;



public class SettingsMainActivity extends AppCompatActivity {
    private ManageStorageData manageStorageData;

    private Switch soundSwtch;
    private Switch vibSwtch;
    private SeekBar secRefBar;
    private TextView secRefTxt;
    int valSeconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);
        manageStorageData = new ManageStorageData(SettingsMainActivity.this);

        soundSwtch = (Switch)  findViewById(R.id.SoundSwitch);
        vibSwtch = (Switch) findViewById(R.id.vibSwitch);
        secRefBar = (SeekBar) findViewById(R.id.seekBar);
        secRefTxt = (TextView) findViewById(R.id.textRefreshSecond);

        soundSwtch.setChecked(manageStorageData.getSoundSetting());
        vibSwtch.setChecked(manageStorageData.getVibSetting());

        valSeconds = manageStorageData.getRefreshTime();

        secRefBar.setProgress(valSeconds);
        secRefTxt.setText(new Integer(valSeconds).toString());

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

        secRefBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int val =0;
            int minValSeekBar = 15;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                val = progress;
                secRefTxt.setText(String.valueOf(val));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(val < minValSeekBar){
                    val = minValSeekBar;
                    secRefBar.setProgress(val);
                }
                manageStorageData.setRefreshSetting(val);
            }
        });
    }
}



