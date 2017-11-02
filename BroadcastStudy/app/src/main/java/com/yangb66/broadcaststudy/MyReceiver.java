package com.yangb66.broadcaststudy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by 彪 on 2017/11/2.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("mainButton1.onClick.Broadcast")){
            Toast.makeText(context, "Receive mainButton1 onClick Broadcast", Toast.LENGTH_LONG).show();
        }
        if(intent.getAction().equals("mainButton2.onClick.Broadcast")){
            Toast.makeText(context, "Receive mainButton2 onClick Broadcast", Toast.LENGTH_LONG).show();
        }
    }
}
