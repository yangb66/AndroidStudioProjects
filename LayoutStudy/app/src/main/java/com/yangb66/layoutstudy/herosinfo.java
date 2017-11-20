package com.yangb66.layoutstudy;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class herosinfo extends AppCompatActivity {
    private EditText name;
    private EditText type;
    private EditText sex;
    private EditText year;
    private EditText place;
    private Button edit;


    private EditText info;

//    private TextView name0;
//    private TextView type0;
//    private TextView year0;
//    private TextView place0;
//    private TextView sex0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.herosinfo);

        name=(EditText)findViewById(R.id.nametext);
        type=(EditText)findViewById(R.id.typetext);
        sex=(EditText)findViewById(R.id.sextext);
        place=(EditText)findViewById(R.id.placetext);
        year=(EditText)findViewById(R.id.yeartext);
        info=(EditText)findViewById(R.id.infotext);
        edit=(Button)findViewById(R.id.edit);

//        name0=(TextView)findViewById(R.id.name);
//        type0=(TextView)findViewById(R.id.type);
//        sex0=(TextView)findViewById(R.id.sex);
//        place0=(TextView)findViewById(R.id.place);
//        year0=(TextView)findViewById(R.id.year);

        //初始化
        name.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
        type.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
        sex.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
        place.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
        year.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
        info.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
        edit.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
//        name0.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
//        type0.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
//        sex0.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
//        place0.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));
//        year0.setTypeface(Typeface.createFromAsset(this.getAssets(),"sunsatsen.ttf"));

        edit.setText("编辑");
        name.setFocusable(false);
        sex.setFocusable(false);
        type.setFocusable(false);
        place.setFocusable(false);
        year.setFocusable(false);
        info.setFocusable(false);

        //设置按钮点击
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().toString().equals("编辑")){
                    edit.setText("保存");
                    name.setFocusable(true);
                    sex.setFocusable(true);
                    type.setFocusable(true);
                    place.setFocusable(true);
                    year.setFocusable(true);
                    info.setFocusable(true);
                    name.setFocusableInTouchMode(true);
                    name.requestFocus();
                    sex.setFocusableInTouchMode(true);
                    sex.requestFocus();
                    type.setFocusableInTouchMode(true);
                    type.requestFocus();
                    place.setFocusableInTouchMode(true);
                    place.requestFocus();
                    year.setFocusableInTouchMode(true);
                    year.requestFocus();
                    info.setFocusableInTouchMode(true);
                    info.requestFocus();
                }
                else if(edit.getText().toString().equals("保存")){
                    edit.setText("编辑");
                    name.setFocusable(false);
                    sex.setFocusable(false);
                    type.setFocusable(false);
                    place.setFocusable(false);
                    year.setFocusable(false);
                    info.setFocusable(false);
                }
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
