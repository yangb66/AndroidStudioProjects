package com.yangb66.media;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 彪 on 2017/11/29.
 */

public class myApp extends Application {
    List<String> musicPath, musicName;
    int musicId = 0;
    boolean isPlay = false;
    int modeId=1;        //播放模式，0单曲循环，1列表循环，2随机播放
    public myApp(){
        musicPath = new ArrayList<>();
        musicName = new ArrayList<>();
    }
    String getMusicPath(){
        return musicPath.get(musicId);
    }
    String getMusicName(){return musicName.get(musicId);}
}
