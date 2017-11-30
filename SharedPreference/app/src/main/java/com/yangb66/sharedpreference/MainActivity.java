package com.yangb66.sharedpreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //public static int MODE = Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE;  //定义保存模式
    public static final int MODE = MODE_PRIVATE;
    public static final String PREFERENCE_NAME = "SaveSetting11";         //定义名称
    private TextView text1;
    private int id = 0;
    Handler handler;
    String str = new String("hello");
    //该名称与Android文件系统中保存的XML文件同名。
      //      • (保存在:/data/data/<package name>/shared_prefs/)
        //    • 相同名称的NVP内容，都会保存在同一个文件中。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.text1);
        //编辑保存
//        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("Name", "Tom");
//        editor.putInt("Age", 20);
//        editor.putFloat("Height", 163.00f);
//        editor.commit();   //保存
//        //读取数据
//
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 101:
                        text1.setText(str);
                        break;
                }
            }
        };

        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                while(true){
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
                    if(id == 0) str = sharedPreferences.getString("Name", "jack");
                    if(id == 1) str = String.valueOf(sharedPreferences.getInt("Age", 12));
                    if(id == 2) str = String.valueOf(sharedPreferences.getFloat("Height", 123.1f));
                    id = (id+1)%3;
                    handler.obtainMessage(101).sendToTarget();
                }
            }
        };
        thread.start();
    }
}
