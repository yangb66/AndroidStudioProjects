package com.yangb66.layoutstudy;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Appset extends AppCompatActivity {
    private ToggleButton musicSet;
    private SeekBar volumeSet;
    private Button setButton;
    private Button textTypeChoose;
    private Button cancelButton;
    private TextView music, textType;

    private ConstraintLayout layout;

    int textTypeId=0, textTypeId0;
    String[] textTypeStr={"中山纪念字体","繁体"};
    String[] textTypeSrc={"sunsatsen.ttf","fanti.ttf"};

    int backgroundId=0, backgroundId0;
    String 

    String musicState="开", musicState0;
    int volumeValue, volumeValue0, maxVolume;

    //音量管理器
    AudioManager mAudioManager;

    //部件初始化
    void widgetInit(){
        //音乐开关按钮
        musicSet=(ToggleButton)findViewById(R.id.musicSet);

        //音量调节bar
        volumeSet=(SeekBar)findViewById(R.id.volumeSet);

        //设置按钮
        setButton=(Button)findViewById(R.id.setButton);

        //字体选择按钮
        textTypeChoose=(Button)findViewById(R.id.textTypeChoose);

        //取消按钮
        cancelButton=(Button)findViewById(R.id.cancelButton);

        //文字信息控件
        music=(TextView)findViewById(R.id.music);
        textType=(TextView)findViewById(R.id.textType);
    }

    //部件数据更新
    void widgetUpdate(){
        //更新音乐开关按钮文字
        musicSet.setText(musicState);

        //更新音量bar
        volumeSet.setMax(maxVolume);
        volumeSet.setProgress(volumeValue);

        //更新字体按钮文字
        textTypeChoose.setText(textTypeStr[textTypeId]);

        //更新文字信息控件字体
        music.setTypeface(Typeface.createFromAsset(Appset.this.getAssets(), textTypeSrc[textTypeId]));
        textType.setTypeface(Typeface.createFromAsset(Appset.this.getAssets(), textTypeSrc[textTypeId]));
    }

    //数据更新
    void dataUpdata(){
        //音乐开关,musicState0为数据备份，用于取消设置时恢复，设置时更新
        musicState0=musicState;
        //音量bar
        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //当前音量
        volumeValue=volumeValue0=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //最大音量
        maxVolume=mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //字体
        textTypeId0=textTypeId;
    }


    //初始化
    void init(){
        dataUpdata();
        widgetInit();
        widgetUpdate();
    }

    //本地设置
    void localSet(){
        dataUpdata();
        widgetUpdate();
        Toast.makeText(Appset.this, "设置成功", Toast.LENGTH_SHORT).show();
    }

    //广播传递设置
    void broadcastSet(){

    }


    //数据库设置
    void dataBaseSet(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appset);

        //初始化
        init();

        //设置按钮
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localSet();
                broadcastSet();
                dataBaseSet();
                //修改背景色
                layout=(ConstraintLayout)findViewById(R.id.appSetLayout);
                layout.setBackground(getDrawable(R.drawable.info_bg));
            }
        });
        //取消
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //恢复数据
                textTypeId=textTypeId0;
                musicState=musicState0;
                volumeValue=volumeValue0;
                widgetUpdate();
                Toast.makeText(Appset.this, "取消设置", Toast.LENGTH_LONG).show();
            }
        });
        //设置音乐开关
        musicSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicSet.getText().toString().equals("开")){
                    musicState="关";
                    musicSet.setText(musicState);
                }
                else if(musicSet.getText().toString().equals("关")){
                    musicState="开";
                    musicSet.setText(musicState);
                }
            }
        });
        //设置音量
        volumeSet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeValue=progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        //设置字体
        textTypeChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Appset.this);
                builder.setTitle("选择字体");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });
                //final String[] items=new String[]{"繁体","中山纪念字体"};
                builder.setItems(textTypeStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textTypeId=which;
                        textTypeChoose.setText(textTypeStr[which]);
                        music.setTypeface(Typeface.createFromAsset(Appset.this.getAssets(), textTypeSrc[which]));
                        textType.setTypeface(Typeface.createFromAsset(Appset.this.getAssets(), textTypeSrc[which]));
                    }
                });
                builder.create().show();
            }
        });
    }
}
