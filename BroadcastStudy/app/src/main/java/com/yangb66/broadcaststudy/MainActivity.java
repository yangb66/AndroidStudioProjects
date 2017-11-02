package com.yangb66.broadcaststudy;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mainButton1;
    private Button mainButton2;
    void init(){
        mainButton1=(Button)findViewById(R.id.mainButton1);
        mainButton2=(Button)findViewById(R.id.mainButton2);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Intent from = getIntent();
        Bundle bundle = from.getExtras();
        mainButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "mainButton1 onClickListener", Toast.LENGTH_SHORT).show();
                sendBroadcast(new Intent("mainButton1.onClick.Broadcast"));
            }
        });
        mainButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "mainButton2 onClickListener", Toast.LENGTH_SHORT).show();
                sendBroadcast(new Intent("mainButton2.onClick.Broadcast"));
            }
        });


    }
}
