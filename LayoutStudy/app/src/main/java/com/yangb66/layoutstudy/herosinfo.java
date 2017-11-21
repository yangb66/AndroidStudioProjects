package com.yangb66.layoutstudy;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class herosinfo extends AppCompatActivity {
    private EditText name;
    private EditText type;
    private EditText sex;
    private EditText year;
    private EditText place;
    private Button edit;


    private EditText info;

    myApp myApp0;
    private LinearLayout layout;


    private static final String SETRECEIVEDYNAMIC="com.example.ex4.SetReceiveDynamic";
//    private TextView name0;
//    private TextView type0;
//    private TextView year0;
//    private TextView place0;
//    private TextView sex0;

    void widgetInit(){
        name=(EditText)findViewById(R.id.nametext);
        type=(EditText)findViewById(R.id.typetext);
        sex=(EditText)findViewById(R.id.sextext);
        place=(EditText)findViewById(R.id.placetext);
        year=(EditText)findViewById(R.id.yeartext);
        info=(EditText)findViewById(R.id.infotext);
        edit=(Button)findViewById(R.id.edit);
        layout =(LinearLayout)findViewById(R.id.layout);
    }

    void setWidgetTypeface(){
        name.setTypeface(Typeface.createFromAsset(this.getAssets(),myApp0.getTypefaceSrc()));
        type.setTypeface(Typeface.createFromAsset(this.getAssets(),myApp0.getTypefaceSrc()));
        sex.setTypeface(Typeface.createFromAsset(this.getAssets(),myApp0.getTypefaceSrc()));
        place.setTypeface(Typeface.createFromAsset(this.getAssets(),myApp0.getTypefaceSrc()));
        year.setTypeface(Typeface.createFromAsset(this.getAssets(),myApp0.getTypefaceSrc()));
        info.setTypeface(Typeface.createFromAsset(this.getAssets(),myApp0.getTypefaceSrc()));
        edit.setTypeface(Typeface.createFromAsset(this.getAssets(),myApp0.getTypefaceSrc()));
    }

    void setBackground(){
        myApp0=(myApp)getApplication();
        layout.setBackground(getDrawable(getResources().getIdentifier(myApp0.getBackgroundSrc(), "drawable", getPackageName())));
        layout.getBackground().setAlpha(myApp0.alpha);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.herosinfo);

        myApp0=(myApp)getApplication();
        //setBackground();

        widgetInit();

        setWidgetTypeface();


//        name0=(TextView)findViewById(R.id.name);
//        type0=(TextView)findViewById(R.id.type);
//        sex0=(TextView)findViewById(R.id.sex);
//        place0=(TextView)findViewById(R.id.place);
//        year0=(TextView)findViewById(R.id.year);


//        msgReceiver receiver=new msgReceiver();
//        IntentFilter intentFilter=new IntentFilter(SETRECEIVEDYNAMIC);
//        registerReceiver(receiver,intentFilter);

        //初始化

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

//    public class msgReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int textTypeId=intent.getIntExtra("textTypeId",0);
//            String[] textTypeSrc={"sunsatsen.ttf","fanti.ttf"};
//            TextView text=(TextView)findViewById(R.id.text);
//            text.setTypeface(Typeface.createFromAsset(herosinfo.this.getAssets(),textTypeSrc[textTypeId]));
//
//            for(int i=0; i<layout.getChildCount(); i++) {
//                View view=layout.getChildAt(i);
//                String s=view.getAccessibilityClassName().toString();
//                if(s.equals("TextView")){
//                    TextView e=(TextView)view;
//                    e.setTypeface(Typeface.createFromAsset(herosinfo.this.getAssets(),textTypeSrc[textTypeId]));
//                }
//                else if(s.equals("EditText")){
//                    EditText e=(EditText)view;
//                    e.setTypeface(Typeface.createFromAsset(herosinfo.this.getAssets(),textTypeSrc[textTypeId]));
//                }
//            }
//        }
//    }
}
