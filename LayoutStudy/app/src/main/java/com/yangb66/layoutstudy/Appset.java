package com.yangb66.layoutstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class Appset extends AppCompatActivity {
    private ToggleButton musicChange;
    private SeekBar voiceChange;
    int mp3 = R.raw.yinyu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appset);


    }
}
