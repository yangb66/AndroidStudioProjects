package com.yangb66.layoutstudy;

import android.app.Application;

/**
 * Created by 彪 on 2017/11/20.
 */

public class GlobalVariables extends Application {
    //字体，字体资源地址，字体名称,字号
    int typefaceId;
    String[] typefaceSrc;
    String[] typefaceStr;
    int textSize;

    //音量，扬声器状态
    int volume;
    int musicState;

    //背景
    int backgroundId;
    String[] backgroundSrc;
    String[] backgroundStr;

    //

    @Override
    public void onCreate() {
        super.onCreate();

        initText();


        volume=0;
        musicState=0;
    }

    void initText(){
        typefaceId=0;
        typefaceSrc=new String[]{"fanti","sunsatsen"};
        textSize=4;
    }
}
