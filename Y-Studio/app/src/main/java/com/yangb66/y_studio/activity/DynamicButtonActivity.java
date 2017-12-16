package com.yangb66.y_studio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yangb66.y_studio.R;

public class DynamicButtonActivity extends AppCompatActivity {
    RelativeLayout main_layout;
    public int titleBarY = 0, statusBarY = 0;
    public int startX = 0, startY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获得布局
        main_layout = new RelativeLayout(this);
        setContentView(main_layout);
        // 获取屏幕尺寸
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        final int maxX = outMetrics.widthPixels; // 1440
        final int maxY = outMetrics.heightPixels; // 2560
        float percentX = maxX / 100f;
        float percentY = maxY / 100f;
        // 获取状态栏
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarY = getResources().getDimensionPixelSize(resourceId);
        }
        // 新建一个按钮控件
        final Button button1 = new Button(this);
        // 设置控件文本内容
        button1.setText("Home");
        // 设置控件背景
        button1.setBackground(getDrawable(R.drawable.button80x40));
        // 设置控件ID
        button1.setId(getResources().getInteger(R.integer.buttonId1));
        // 设置控件大小
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)(20*percentX),(int)(20*percentX));
        // 设置控件位置
        layoutParams.setMargins((int)(40*percentX),(int)(40*percentY),0,0);
        // 添加控件
        main_layout.addView(button1, layoutParams);
        // 设置控件的拖动
        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int)event.getRawX();
                final int Y = (int)event.getRawY();
                switch(event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams)v.getLayoutParams();
                        startX = X - layoutParams1.leftMargin;
                        startY = Y - layoutParams1.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 移动控件
                        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams)v.getLayoutParams();
                        int newX = X - startX;
                        int newY = Y - startY;
                        // 屏幕以内才移动
                        if(newX >= 0 && newX + layoutParams2.width <= maxX &&
                                newY >= 0 && newY + layoutParams2.height + titleBarY + statusBarY <= maxY){
                            layoutParams2.leftMargin = X - startX;
                            layoutParams2.topMargin = Y - startY;
                            v.setLayoutParams(layoutParams2);
                        }
                        break;
                }
                return true;
            }
        });
        // 设置布局被点击
        main_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    makeToast("layout touched");
                }
                return true;
            }
        });
    }

    // 获取标题栏
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    void makeToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
