package com.yangb66.lab_7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText password1, password2;
    Button ok, clear;
    //初始化
    void init(){
        password1 = (EditText)findViewById(R.id.password1);
        password1.setHint("New Password");
        password2 = (EditText)findViewById(R.id.password2);
        password2.setHint("Confirm Password");
        ok = (Button)findViewById(R.id.ok);
        ok.setText("OK");
        clear = (Button)findViewById(R.id.clear);
        clear.setText("CLEAR");
        myApp myApp0 = (myApp)getApplication();
        myApp0.add(this);
    }

    public static final String PREFERENCE_NAME = "PASSWORD_OF_LAB7";
    public static final int PREFERENCE_MODE = MODE_PRIVATE;
    String pw = PREFERENCE_NAME;                //密码
    SharedPreferences sharedPreferences;        //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, PREFERENCE_MODE);
        pw = sharedPreferences.getString("password", PREFERENCE_NAME);      //读取密码

        if(pw != PREFERENCE_NAME){                      //有密码,修改布局
            password1.setVisibility(View.GONE);         //设置不可见
            password2.setHint("Password");              //修改提示
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOk();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClear();
            }
        });
    }
    //OK按钮点击事件
    void setOk(){
        if(password1.getVisibility() != View.GONE){     //第一次登录
            if(password1.getText().length() == 0 || password2.getText().length() == 0){      //有一项为空
                Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_LONG).show();
            } else if(password1.getText().toString().equals(password2.getText().toString()) == false) {         //不匹配
                Toast.makeText(getApplicationContext(), "Password Mismatch",  Toast.LENGTH_LONG).show();
            } else {        //匹配
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("password", password2.getText().toString());           //设置密码
                editor.commit();//提交
                login();
            }
        } else {        //第二次登录
            if(password2.getText().toString().equals(pw)){          //匹配
                login();
            } else {
                Toast.makeText(getApplicationContext(), "Password Mismatch",  Toast.LENGTH_LONG).show();
            }
        }

    }
    //CLEAR按钮点击事件
    void setClear(){
        password1.setText("");
        password2.setText("");
    }
    //登录
    void login(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
