package com.yangb66.layoutstudy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Appset extends AppCompatActivity {
    //设置变量
    private ToggleButton musicStateSetButton;  //音乐开关设置按钮
    private SeekBar volumeSetBar;      //音量设置条
    private Button setButton;       //设置按钮
    private Button typefaceChooseButton; //文字选择按钮
    private Button cancelButton;   //取消按钮
    private TextView music, typeface, background, musicSrc;   //文字显示
    private Button backgroundChooseButton;                 //背景设置按钮
    private ConstraintLayout layout;                   //布局视图
    private Button musicSrcSetButton; //背景乐设计
    //全局变量
    myApp myApp0;
    //局部设置变量
    int typefaceId = 0;
    int backgroundId = 0;
    boolean musicState = false;
    int volumeValue = 0;
    int musicId, musicId2;
    //音量管理器
    AudioManager mAudioManager;
    int maxVolume;

    //部件初始化
    void widgetInit() {
        //音乐开关按钮
        musicStateSetButton = (ToggleButton) findViewById(R.id.musicStateSetButton);
        //字体选择按钮
        typefaceChooseButton = (Button) findViewById(R.id.typefaceChooseButton);
        //背景设置按钮
        backgroundChooseButton = (Button) findViewById(R.id.backgroundChooseButton);
        //音量调节bar
        volumeSetBar = (SeekBar) findViewById(R.id.volumeSetBar);
        //设置按钮
        setButton = (Button) findViewById(R.id.setButton);
        //取消按钮
        cancelButton = (Button) findViewById(R.id.cancelButton);
        //文字信息控件
        music = (TextView) findViewById(R.id.music);
        typeface = (TextView) findViewById(R.id.typeface);
        background = (TextView) findViewById(R.id.background);
        musicSrc=(TextView) findViewById(R.id.musicSrc);
        //背景乐
        musicSrcSetButton=(Button)findViewById(R.id.musicSrcSetButton);
        //背景布局
        myApp0=(myApp)getApplication();
        layout = (ConstraintLayout) findViewById(R.id.appSetLayout);
    }
    void setWidgetTypeface(String src) {
        //textView
        Typeface type=Typeface.createFromAsset(Appset.this.getAssets(), src);
        music.setTypeface(type);
        typeface.setTypeface(type);
        background.setTypeface(type);
        musicSrc.setTypeface(type);
        //Button
        musicStateSetButton.setTypeface(type);
        setButton.setTypeface(type);
        cancelButton.setTypeface(type);
        typefaceChooseButton.setTypeface(type);
        backgroundChooseButton.setTypeface(type);
        musicSrcSetButton.setTypeface(type);
    }
    //设置时部件数据更新
    void widgetUpdate() {
        //更新音乐开关按钮文字
        musicStateSetButton.setText(myApp0.getMusicStateStr());
        //设置音乐
        musicSrcSetButton.setText(myApp0.getMusicStr());
        //更新音量bar
        volumeSetBar.setMax(maxVolume);
        volumeSetBar.setProgress(volumeValue);
        //更新字体按钮文字
        typefaceChooseButton.setText(myApp0.getTypefaceStr());
        //更新文字信息控件字体
        setWidgetTypeface(myApp0.getTypefaceSrc());
        //背景按钮文字
        backgroundChooseButton.setText(myApp0.getBackgroundStr());
        //设置背景
        layout.setBackground(getDrawable(getResources().getIdentifier(myApp0.getBackgroundSrc(), "drawable", getPackageName())));
        layout.getBackground().setAlpha(myApp0.alpha);
    }
    //取消设置时恢复部件设置
    void widgetRecover() {
        //恢复音量bar
        if (volumeValue != myApp0.getVolume())
            volumeSetBar.setProgress(myApp0.getVolume());
        //更新文字信息控件字体
        if (typefaceId != myApp0.getTypefaceId()) {
            setWidgetTypeface(myApp0.getTypefaceSrc());
        }
        //更新字体设置按钮文字内容
        if (typefaceId != myApp0.getTypefaceId())
            typefaceChooseButton.setText(myApp0.getTypefaceStr());
        //更新音乐开关按钮文字
        if (musicState != myApp0.getMusicState())
            musicStateSetButton.setText(myApp0.getMusicStateStr());
        //背景按钮文字
        if (backgroundId != myApp0.getBackgroundId())
            backgroundChooseButton.setText(myApp0.getBackgroundStr());
        //设置背景
        if (backgroundId != myApp0.getBackgroundId())
            layout.setBackground(getDrawable(getResources().getIdentifier(myApp0.getBackgroundSrc(), "drawable", getPackageName())));
        //恢复音乐开关设置
        if (musicState != myApp0.getMusicState()) {
            if (musicState == false) {
                Intent intent = new Intent(Appset.this, MusicServer.class);
                stopService(intent);
            } else {
                Intent intent = new Intent(Appset.this, MusicServer.class);
                startService(intent);
            }
        }
        //恢复音乐名称
        if(musicId != myApp0.getMusicId())
            musicSrcSetButton.setText(myApp0.getMusicStr());
    }
    //设置时全局数据更新
    void setMyApp() {
        myApp0.setTypefaceId(typefaceId);
        myApp0.setBackgroundId(backgroundId);
        myApp0.setMusicState(musicState);
        myApp0.setVolume(volumeValue);
        myApp0.setMusicId(musicId);
    }
    //恢复时全局数据恢复
    void getMyApp() {
        typefaceId = myApp0.getTypefaceId();
        backgroundId = myApp0.getBackgroundId();
        musicState = myApp0.getMusicState();
        volumeValue = myApp0.getVolume();
        musicId=myApp0.getMusicId();
    }
    //数据库设置
    void dataBaseSet() {
        /*设置的时候将应用重启时需要初始化的量存到数据库
        * 先把带0的几个设置复原，再数据恢复*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appset);
        //获得全局变量
        myApp0 = (myApp) getApplication();
        //获得音量管理器，获得最大音量和当前音量
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        myApp0.volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        musicId2=myApp0.getMusicId();

        //初始化,获得全局变量的设置
        getMyApp();
        //获得布局控件
        widgetInit();
        //布局控件更新
        widgetUpdate();
        //设置按钮click
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMyApp();
                musicId2=musicId;
            }
        });
        //取消
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApp0.setMusicId(musicId2);
                widgetRecover();
                getMyApp();
            }
        });
        //设置音乐开关
        musicStateSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicStateSetButton.getText().toString().equals("开")) {
                    musicState = false;
                    musicStateSetButton.setText("关");
                    //开音乐
                    Intent intent=new Intent(Appset.this, MusicServer.class);
                    startService(intent);
                } else if (musicStateSetButton.getText().toString().equals("关")) {
                    musicState = true;
                    musicStateSetButton.setText("开");
                    //关音乐
                    Intent intent=new Intent(Appset.this, MusicServer.class);
                    stopService(intent);
                }
            }
        });
        //设置音量
        volumeSetBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeValue = progress;
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeSetBar.getProgress(), 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //设置字体
        typefaceChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Appset.this);
                builder.setTitle("选择字体");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final String[] items = myApp0.getTypefaceStrArr();
                final String[] srcs = myApp0.getTypefaceSrcArr();
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        typefaceId = which;
                        typefaceChooseButton.setText(items[which]);
                        setWidgetTypeface(srcs[which]);
                    }
                });
                builder.create().show();
            }
        });
        //设置背景
        backgroundChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Appset.this);
                builder.setTitle("选择背景");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final String[] items = myApp0.getBackgroundStrArr();
                final String[] srcs = myApp0.getBackgroundSrcArr();
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        backgroundId = which;
                        backgroundChooseButton.setText(items[which]);
                        layout.setBackground(getDrawable(getResources().getIdentifier(srcs[which], "drawable", getPackageName())));
                        layout.getBackground().setAlpha(myApp0.alpha);
                    }
                });
                builder.create().show();}
        });
        //设置音乐
        musicSrcSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Appset.this);
                builder.setTitle("选择背景音乐");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final String[] items = myApp0.getMusicStrArr();
                builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                musicId = which;
                                musicSrcSetButton.setText(items[which]);
                                if(musicId != myApp0.getMusicId()){
                                    myApp0.setMusicId(musicId);
                                    if(musicState == false){
                                        Intent intent = new Intent(Appset.this, MusicServer.class);
                                        stopService(intent);
                                        Intent intent2=new Intent(Appset.this, MusicServer.class);
                                        startService(intent2);
                                    }
                                }
                            }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            //mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeValue = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            myApp0.setVolume(volumeValue);
            volumeSetBar.setProgress(volumeValue);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}

