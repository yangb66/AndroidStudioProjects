package com.example.lenovo.myapplication5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static com.example.lenovo.myapplication5.R.id.textView;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        //更新后点击会进入主界面
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.widget, pi);
        ComponentName me = new ComponentName(context, Widget.class);
        appWidgetManager.updateAppWidget(me, updateViews);

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final String STATICACTION = "com.example.ex4.MyStaticFliter";  //静态广播
//        final String DYNAMICACTION = "com.example.ex4.MyDynamicFliter";//动态广播
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals(STATICACTION)){
//            Goods good = (Goods)intent.getExtras().get("goods");
//
//            Bundle bundle = new Bundle();
//            bundle = intent.getExtras();
//            Goods get_goods = (Goods)bundle.getSerializable("goods");
//            textview = (TextView) textview.findViewById(R.id.widget_text);
//            textview.setText(get_goods.name+"仅售"+get_goods.price+"元");
////
//            imageView = (ImageView)imageView.findViewById(R.id.widget_image);
//            imageView.setImageResource(get_goods.path);
//            Toast. makeText (context,"You hit me!!", Toast. LENGTH_LONG ).show();
//            RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
//            appWidgetManager.updateAppWidget(0, updateViews);
            //初始状态，显示随机的商品信息
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Goods get_goods = (Goods)bundle.getSerializable("goods");
                RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                mRemoteViews.setImageViewResource(R.id.widget_image, get_goods.path);
                mRemoteViews.setTextViewText(R.id.widget_text, get_goods.name+"仅售"+get_goods.price+"元");
//                int[] appWidgetIds = bundle.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
//                if (appWidgetIds != null && appWidgetIds.length > 0) {
//                    this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
//                }
                //设置点击事件,进入商品信息界面
                Intent intent2 = new Intent(context, goodsactivity.class);
                intent2.putExtras(bundle);
                //pendingintent在点击后执行intent
                PendingIntent pi = PendingIntent.getActivity(context,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
                mRemoteViews.setOnClickPendingIntent(R.id.widget, pi);
                //更新
                ComponentName mComponentName = new ComponentName(context, Widget.class);
                AppWidgetManager.getInstance(context).updateAppWidget(mComponentName, mRemoteViews);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

