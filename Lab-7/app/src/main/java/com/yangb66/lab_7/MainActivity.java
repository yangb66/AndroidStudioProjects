package com.yangb66.lab_7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText fileName, fileContent;
    Button save, load, clear, delete;

    void init(){
        fileName=(EditText)findViewById(R.id.fileName);
        fileContent=(EditText) findViewById(R.id.fileContent);
        save=(Button) findViewById(R.id.save);
        load=(Button)findViewById(R.id.load);
        clear=(Button)findViewById(R.id.clear);
        delete=(Button)findViewById(R.id.delete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSave();
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoad();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClear();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDelete();
            }
        });
    }

    //save按钮点击事件
    void setSave(){
        try(FileOutputStream fileOutputStream = openFileOutput(fileName.getText().toString(), MODE_PRIVATE)){
            fileOutputStream.write(fileContent.getText().toString().getBytes());
            Toast.makeText(getApplicationContext(), "Save successfully",  Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Fail to save file", Toast.LENGTH_LONG).show();
        }
    }

    //load按钮点击事件
    void setLoad(){
        try(FileInputStream fileInputStream = openFileInput(fileName.getText().toString())){
            byte[] tem = new byte[fileInputStream.available()];
            fileInputStream.read(tem);
            fileContent.setText(new String(tem));
            Toast.makeText(getApplicationContext(), "Load successfully",  Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Fail to load file",  Toast.LENGTH_LONG).show();
        }
    }



    //clear按钮点击事件
    void setClear(){
        fileContent.setText("");
    }

    //delete按钮点击事件
    void setDelete(){
        try{
            deleteFile(fileName.getText().toString());
            Toast.makeText(getApplicationContext(), "Delete successfully",  Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Fail to delete file",  Toast.LENGTH_LONG).show();
        }
    }

    //重写返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myApp myApp0 = (myApp)getApplication();
        myApp0.removeAll();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myApp myApp0 = (myApp)getApplication();
        myApp0.removeAll();
        finish();
    }
}
