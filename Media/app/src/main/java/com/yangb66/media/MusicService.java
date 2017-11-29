package com.yangb66.media;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class MusicService extends Service {
    private MediaPlayer mp = new MediaPlayer();
    private IBinder mBinder = new myBinder();
    private String path, name;
    private myApp myApp0;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            myApp0 = (myApp)getApplication();
            path = new String(myApp0.getMusicPath());
            name = new String(myApp0.getMusicName());
            mp.setDataSource(path);
            mp.prepare();
            Log.i("servicecreate", "success");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    public class myBinder extends Binder{
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code){
                case 101://播放按钮服务处理函数
                    myApp0 = (myApp)getApplication();
                    // if(path == myApp0.getMusicPath()){
                    if(mp.isPlaying()){                 //播放和暂停的切换
                        mp.pause();
                        reply.writeInt(0);
                    } else{
                        mp.start();
                        reply.writeInt(1);
                    }
//                    } else {                                //切换歌曲
//                        mp.stop();
//                        mp.release();
//                        path = new String(myApp0.getMusicPath());
//                        try{
//                            mp.setDataSource(path);
//                            mp.prepare();
//                            mp.start();
//                            reply.writeInt(1);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                    //  }
                    break;
                case 102://停止按钮处理函数
                    if(mp!=null) {                                              //避免多次调用时出现bug，需要先判断非空
                        mp.stop();
                        try {
                            mp.prepare();
                            mp.seekTo(0);                                       //停止播放，并重置播放进度
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    break;
                case 103://退出按钮处理函数
                    if(mp!=null) {                                             //避免多次调用时出现bug，需要先判断非空
                        mp.stop();
                        mp.release();                                           //停止播放并释放资源，准备退出程序
                    }
                    break;
                case 104://界面刷新处理函数
                    int crt = mp.getCurrentPosition();
                    int ttl = mp.getDuration();
                    int state = 0;
                    if(mp != null && mp.isPlaying()) state = 1;
                    int[] time = new int[]{crt, ttl, state};
                    reply.writeIntArray(time);                                  //返回当前播放的时间和总时间
                    reply.writeString(name);
                    break;
                case 105://拖动进度条处理函数
                    mp.seekTo((int)(mp.getDuration()*data.readFloat()));        //根据获得进度条比例设置音乐
                    break;
                case 106://切换音乐
                    if(mp != null){
                        mp.stop();
                        mp.release();
                    }
                    try{
                        mp = new MediaPlayer();
                        String[] str = data.createStringArray();
                        mp.setDataSource(str[0]);
                        name = str[1];
                        mp.prepare();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}
