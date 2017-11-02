package com.yangb66.myapplication;


import android.support.design.widget.Snackbar;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.text.TextUtils;


public class MainActivity extends AppCompatActivity {
    private TextInputLayout mNum;
    private TextInputLayout mPW;
    private RadioButton mRB1;
    private RadioButton mRB2;
    private RadioGroup mRG;
    private ImageView mImage;
    private Button mLogin;
    private Button mRegister;
    private EditText mEditNum;
    private EditText mEditPW;

    void init(){
        mNum = (TextInputLayout)findViewById(R.id.Num);
        mPW = (TextInputLayout)findViewById(R.id.PW);
        mRB1 = (RadioButton)findViewById(R.id.RB1);
        mRB2 = (RadioButton)findViewById(R.id.RB2);
        mRG = (RadioGroup)findViewById(R.id.RG);
        mImage = (ImageView)findViewById(R.id.Image);
        mLogin = (Button)findViewById(R.id.Login);
        mRegister = (Button)findViewById(R.id.Register);
        mEditNum = mNum.getEditText();
        mEditPW = mPW.getEditText();

        mNum.setHint("请输入学号");
        mPW.setHint("请输入密码");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID) {
                if(checkedID == mRB1.getId()){
                    Snackbar.make(mRG, "您选择了学生", Snackbar.LENGTH_SHORT)
                            .setAction("确认", new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    Toast.makeText(MainActivity.this, "确定按钮被点击了", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })
                            .setActionTextColor((getResources().getColor(R.color.colorPrimary)))
                            .setDuration(5000)
                            .show();
                }
                else if(checkedID == mRB2.getId()) {
                    Snackbar.make(mRG, "您选择了教职工", Snackbar.LENGTH_SHORT)
                            .setAction("确认", new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    Toast.makeText(MainActivity.this, "确定按钮被点击了", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })
                            .setActionTextColor((getResources().getColor(R.color.colorPrimary)))
                            .setDuration(5000)
                            .show();
                }
            }
        });

        final AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
        final String[] items = new String[]{"从相册选择","拍摄"};
        mImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mbuilder.setTitle("上传头像")
                        .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "您选择了[取消]", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                Toast.makeText(MainActivity.this, "您选择了[" + items[i] + "]", Toast.LENGTH_SHORT).show();
                            }
                        });
                mbuilder.create().show();
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mEditNum.getText().toString();
                String password = mEditPW.getText().toString();
                if(number.isEmpty()) {
                    mNum.setErrorEnabled(true);
                    mNum.setError("学号不能为空");
                }
                else {
                    mNum.setErrorEnabled(false);
                }
                if(password.isEmpty()) {
                    mPW.setErrorEnabled(true);
                    mPW.setError("密码不能为空");
                }
                else {
                    mPW.setErrorEnabled(false);
                }
                if(TextUtils.equals(number, "123456") && TextUtils.equals(password, "6666")) {
                    Snackbar.make(mLogin, "登录成功", Snackbar.LENGTH_SHORT)
                            .setAction("确认", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "确定按钮被点击了", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .setDuration(5000)
                            .show();
                }
                else {
                    Snackbar.make(mLogin, "学号或密码错误", Snackbar.LENGTH_SHORT)
                            .setAction("确认", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "确定按钮被点击了", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .setDuration(5000)
                            .show();
                }
            }
        });
        mRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                for(int i=0; i<mRG.getChildCount(); i++) {
                    RadioButton RB = (RadioButton)mRG.getChildAt(i);
                    if(RB.isChecked()) {
                        if(RB.getText().toString().equals("教职工")){
                            Toast.makeText(MainActivity.this, RB.getText() + "注册功能尚未启用", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Snackbar.make(mRegister, RB.getText() + "注册功能尚未启用", Snackbar.LENGTH_SHORT)
                                    .setAction("确认", new View.OnClickListener(){
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(MainActivity.this, "确定按钮被点击了", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                    .setDuration(5000)
                                    .show();
                        }
                    }
                }
            }
        });

    }

}

