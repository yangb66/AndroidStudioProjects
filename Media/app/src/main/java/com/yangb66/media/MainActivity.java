package com.yangb66.media;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//
//设置全局变量，控制播放歌曲，则可以实现歌曲循环播放
//事件处理函数最好封装起来，这样其他时候要用的时候可以直接调用
//
public class MainActivity extends AppCompatActivity {
    //控件
    private TextView crtTime, ttlTime, musicText;
    private Button play, stop, quit, next, last, catalog, collect, playMode;
    private ImageView image;
    private SeekBar timeBar;
    private ConstraintLayout page1, page2;
    private HorizontalScrollView pageSet;
    //变量
    private IBinder mBinder;            //绑定对象，设置为Service的代理对象，能够通过transact函数在Activity和Service之间传递data和reply
    private ServiceConnection sc;       //服务连接对象，用于绑定的设置
    private int[] time;                 //当前播放时间
    private static int dt = 30;                     //每dt毫秒时间刷新一次
    static public boolean hasPermission = false;        //是否有播放权限
    private android.os.Handler mHandler;                   //多线程通信

    private List<Map<String, Object>> data;
    private ListView listView;
    private int maxW = 0;
    List<String> musicPath, musicName;
    String rootPath;
    int musicId = 0;
    boolean isPlay = false;

    private int pageId=0;       //页面ID
    private int modeId=1;        //播放模式，0单曲循环，1列表循环，2随机播放

    private myApp myApp0;

    void init(){
        crtTime = (TextView)findViewById(R.id.crtTime);
        ttlTime = (TextView)findViewById(R.id.ttlTime);
        musicText = (TextView)findViewById(R.id.musicText);

        play = (Button)findViewById(R.id.play);
        stop = (Button)findViewById(R.id.stop);
        quit = (Button)findViewById(R.id.quit);
        next = (Button)findViewById(R.id.next);
        last = (Button)findViewById(R.id.last);
        catalog = (Button)findViewById(R.id.catalog);
        collect = (Button)findViewById(R.id.collect);
        playMode = (Button)findViewById(R.id.playMode);
        last.setText("<<");
        next.setText(">>");
        play.setText("||");
        quit.setText("->");
        stop.setText("o");
        catalog.setText("=");


        timeBar = (SeekBar)findViewById(R.id.timeBar);
        image = (ImageView)findViewById(R.id.image);
        listView = (ListView)findViewById(R.id.list);
        page1 = (ConstraintLayout)findViewById(R.id.page1);
        page2 = (ConstraintLayout)findViewById(R.id.page2);
        pageSet = (HorizontalScrollView)findViewById(R.id.pageSet);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        maxW = displayMetrics.widthPixels;
        page1.setMaxWidth(maxW);
        page2.setMaxWidth(maxW);
        pageSet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(pageSet.getScrollX() < maxW/2){
                        pageId = 0;
                    } else {
                        pageId = 1;
                    }
                    runToPage(pageId+1);
                }
                return false;
            }
        });
        runToPage(pageId);

        //获取音乐资源
        listView = (ListView) findViewById(R.id.list);
        getMusicData();
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplication(), data, R.layout.itemlayout,
                new String[]{"name", "info"}, new int[]{R.id.name, R.id.info});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                musicId = position;
                changeMusic(musicId);
            }
        });
        musicText.setText(musicName.get(musicId));  //设置音乐列表
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     //设置布局内容
        init();     //初始化控件
        //验证权限
        verifyStoragePermissions(this);
        //绑定服务
        sc = new ServiceConnection() {            //监控监听访问者和Service的连接情况，连接成功时回调onServiceConnected方法
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = service;
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                sc = null;
            }
        };
        Intent intent = new Intent(getApplication(), MusicService.class);       //用于绑定activity和service
        startService(intent);
        bindService(intent, sc, BIND_AUTO_CREATE);      //自动创建服务如果绑定存在的话
        //子线程刷新控件
        Thread mThread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(dt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (sc != null && hasPermission) {
                        mHandler.obtainMessage(104).sendToTarget();     //每隔dt毫秒发送信号到主线程
                    }
                }
            }
        };
        mThread.start();
        //刷新界面
        mHandler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 104:
                        setFresh();
                        break;
                }
            }
        };

        //设置控件事件
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPlay();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStop();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuit();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNext();
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLast();
            }
        });
        catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCatalog();
            }
        });
        //拖动进度条，通过IBinder对象调用Service的服务函数
        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int touch = 0, ttl = 0;
                touch = seekBar.getProgress();
                ttl = seekBar.getMax();
                try {
                    int code = 105;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    data.writeFloat(((float) touch) / ttl);            //必须先转类型再除，直接除结果是0f
                    mBinder.transact(code, data, reply, 0);         //105号处理函数，传递进度条比例给
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //获得音乐列表
    void getMusicData(){
        data = new ArrayList<>();
        rootPath = Environment.getExternalStorageDirectory().getPath().toString();
        musicName = new ArrayList<>();
        musicPath = new ArrayList<>();
        File file = new File(rootPath + "/netease/cloudmusic/Music");
        File[] listFile = file.listFiles();
        for(int i=0; i<listFile.length; i++){
            if(listFile[i].isFile()){
                Map<String, Object> tmp = new LinkedHashMap<>();
                musicName.add(listFile[i].getName());
                musicPath.add(listFile[i].getPath());
                tmp.put("name", listFile[i].getName());
                tmp.put("info", listFile[i].getPath());
                data.add(tmp);
            }
        }
        file = new File(rootPath + "/kgmusic/download");
        listFile = file.listFiles();
        for(int i=0; i<listFile.length; i++){
            if(listFile[i].isFile()){
                Map<String, Object> tmp = new LinkedHashMap<>();
                musicName.add(listFile[i].getName());
                musicPath.add(listFile[i].getPath());
                tmp.put("name", listFile[i].getName());
                tmp.put("info", listFile[i].getPath());
                data.add(tmp);
            }
        }
        myApp0 = (myApp)getApplication();
        myApp0.musicName = musicName;
        myApp0.musicPath = musicPath;
    }
    //设置播放
    void setPlay(){
        try {
            int code = 101;
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            mBinder.transact(code, data, reply, 0);         //101号程序，启动/暂停音乐
            if(reply.readInt() == 1){                       //1 means set play, 0 means pause
                play.setText("|>");
                isPlay = true;
            }else{
                play.setText("||");
                isPlay = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //设置重置进度且停止播放
    void setStop(){
        try {
            isPlay = false;
            play.setText("PLAY");
            int code = 102;
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            mBinder.transact(code, data, reply, 0);         //102号程序，暂停并重置音乐，做好准备播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //设置退出程序
    void setQuit(){
        try {
            int code = 103;
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            mBinder.transact(code, data, reply, 0);     //103服务程序，退出程序前关闭音乐，释放资源
        } catch (Exception e) {
            e.printStackTrace();
        }
        unbindService(sc);                              //解除绑定
        sc = null;                                      //服务连接设为空
        try {
            MainActivity.this.finish();                 //结束程序
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //设置下一首歌
    void setNext(){
        if(musicName.size() != 0){
            musicId = (musicId+musicName.size()+1)%musicName.size();
            //changeMusic(musicId);
            myApp0 = (myApp)getApplication();
            myApp0.musicId = musicId;
            if(isPlay) setPlay();
        }
    }
    //设置上一首歌
    void setLast(){
        if(musicName.size() != 0){
            musicId = (musicId+musicName.size()-1)%musicName.size();
            changeMusic(musicId);
            if(isPlay) setPlay();
        }
    }
    //设置修改音乐资源
    void changeMusic(int musicId){
        try{
            int code = 106;
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            //data.writeString(musicPath.get(musicId));
            data.writeStringArray(new String[]{musicPath.get(musicId), musicName.get(musicId)});
            mBinder.transact(code, data, reply, 0);
            pageSet.smoothScrollTo(0,0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //设置进入目录
    void setCatalog(){
        runToPage(2);
    }
    //设置刷新
    void setFresh(){
        try {
            int code = 104;
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            mBinder.transact(code, data, reply, 0);
            time = reply.createIntArray();              //create类函数可以获得Parcel的信息
            //reply.readIntArray(time);                 //读不到
            musicText.setText(reply.readString());      //设置歌名
            if (time[2] == 1){
                if(time[0] <= time[1]) crtTime.setText(toTimeFormat(time[0] / 1000));        //设置时间信息
                ttlTime.setText(toTimeFormat(time[1] / 1000));
                timeBar.setMax(time[1]);                             //设置进度条
                timeBar.setProgress(time[0]);
                float cycle = 10;                            //cycle秒转一圈
                float degree = image.getRotation();         //设置播放时旋转
                degree = degree + 360f / (cycle * 1000f / dt);       //cycle秒有cycle*1000/dt次变化，共360度
                image.setRotation(degree);
            }
//            else if(time[2] == 0 && isPlay){           //播放已经结束,但是之前在播放
//                if(modeId == 0){        //单曲循环
//                    setPlay();
//                }else if(modeId == 1){          //列表循环
//                    setNext();
//                }else if(modeId == 2){          //随机播放
//                    Random random = new Random();
//                    while(musicName.size() > 1){
//                        int num = random.nextInt(musicName.size());
//                        if((musicId+1)%musicName.size() != num){
//                            musicId = num; break;
//                        }
//                    }
//                    setNext();
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //设置跳到第position个页面
    void runToPage(int position){           //1,2,3...
        pageId = position-1;
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                pageSet.smoothScrollTo(pageId*maxW,0);
            }
        };
        Handler tem = new Handler();
        tem.postDelayed(runnable, 200);
    }
    //动态申请权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};
    public static void verifyStoragePermissions(Activity activity){
        try{
            //检测是否有读取的权限
            int permission = ActivityCompat.checkSelfPermission(
                    activity, "android.permission.READ_EXTERNAL_STORAGE");
            if(permission != PackageManager.PERMISSION_GRANTED){
                Log.i("permission", "no");
                ActivityCompat.requestPermissions(activity,
                        PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
            else {
                Log.i("permission", "has");
                hasPermission = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length != 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED){
            //权限同意
            hasPermission = true;
            Log.i("request", "agree");
        }else{
            Log.i("request", "disagree");
            System.exit(0);
        }
    }
    //将秒转化为分秒mm:ss的格式
    public String toTimeFormat(int a){
        String s1 = String.valueOf(a/60);
        if(s1.length()<2) s1 = "0"+s1;
        String s2 = String.valueOf(a%60);
        if(s2.length()<2) s2 = "0"+s2;
        return s1+":"+s2;
    }
}
