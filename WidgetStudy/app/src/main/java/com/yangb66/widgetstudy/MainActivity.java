package com.yangb66.widgetstudy;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {
    private int mAppWidgetId;
    private EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText=(EditText)findViewById(R.id.editText);
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            mAppWidgetId=extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID
            );
        }//获取App Widget ID
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(MainActivity.this);//获取AppWidgetManager的实例
        RemoteViews remoteViews=new RemoteViews(getPackageName(), R.layout.new_app_widget);
        remoteViews.setTextViewText(R.id.appwidget_text, mEditText.getText());//设置显示内容
        appWidgetManager.updateAppWidget(mAppWidgetId, remoteViews); //更新App Widget
        Intent resultValue=new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
        setResult(RESULT_OK,resultValue);//设置完成
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }
}
