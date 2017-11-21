package com.yangb66.layoutstudy;

import android.app.Application;

/**
 * Created by 彪 on 2017/11/20.
 */

public class myApp extends Application {
    //字体，字体资源地址，字体名称,字号
    int typefaceId;
    String[] typefaceStr,typefaceSrc;
    //背景
    int backgroundId;
    String[] backgroundStr,backgroundSrc;
    int alpha; //透明度
    //音量，扬声器状态,使用service，不需要全局变量
    boolean musicState;
    String[] musicStateStr;
    int volume;
    //音乐类型
    int musicId;
    String[] musicStr, musicSrc;
    @Override
    public void onCreate() {
        super.onCreate();

        initText();
        initBackground();
        initMusic();
    }
    //init
    void initText(){
        typefaceId=1;
        typefaceStr=new String[]{"中山纪念字体","楷体","繁体隶书","宝丽行书"};
        typefaceSrc=new String[]{"sunyatsen.ttf","kaiti.ttf","fanli.ttf","Baoli.ttc"};
    }
    void initBackground(){
        backgroundId=0;
        alpha=100;
        backgroundStr=new String[]{"清新","战场","田园","暮色","书香"};
        backgroundSrc=new String[]{"qingxin","war","xianjing","huanghun","gufeng"};
    }
    void initMusic(){
        musicState=true;
        musicStateStr=new String[]{"关","开"};
        volume=0;
        musicId=3;
        musicStr=new String[]{"高山流水","酒狂","长风吟","独坐幽篁"};
        musicSrc=new String[]{"flowwater","wanemad","longwind","sitalone"};
    }
    //get
    String getTypefaceSrc(){return typefaceSrc[typefaceId];}
    String getBackgroundSrc(){return backgroundSrc[backgroundId];}
    String getTypefaceStr(){return typefaceStr[typefaceId];}
    String getBackgroundStr(){return backgroundStr[backgroundId];}
    String[] getTypefaceStrArr(){return typefaceStr;}
    String[] getTypefaceSrcArr(){return typefaceSrc;}
    String[] getBackgroundStrArr(){return backgroundStr;}
    String[] getBackgroundSrcArr(){return backgroundSrc;}
    String[] getMusicStrArr(){return musicStr;}
    String[] getMusicSrcArr(){return musicSrc;}
    String getMusicSrc(){return musicSrc[musicId];}
    String getMusicStr(){return musicStr[musicId];}
    int getMusicId(){return musicId;}
    int getTypefaceId(){return typefaceId;}
    int getBackgroundId(){return backgroundId;}
    boolean getMusicState(){return musicState;}
    int getVolume(){return volume;}
    String getMusicStateStr(){if(musicState==false)return musicStateStr[0]; else return musicStateStr[1];}
    //set
    void setTypefaceId(int a){typefaceId=a;}
    void setBackgroundId(int a){backgroundId=a;}
    void setMusicState(boolean a){musicState=a;}
    void setVolume(int a){volume=a;}
    void setMusicId(int a){musicId=a;}
}
