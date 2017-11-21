package com.yangb66.layoutstudy;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by 彪 on 2017/11/18.
 */

public class MusicServer extends Service {
    private MediaPlayer mediaPlayer=null;
    private myApp myApp0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myApp0 = (myApp) getApplication();
        mediaPlayer = MediaPlayer.create(MusicServer.this, getResources().getIdentifier(myApp0.getMusicSrc(), "raw", getPackageName()));
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.setLooping(false);
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
