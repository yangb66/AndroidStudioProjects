package com.example.lenovo.myapplication5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Created by lenovo on 2017/10/29.
 */

public class Receiver extends BroadcastReceiver{
    private static final String STATICACTION = "com.example.ex4.MyStaticFliter";  //静态广播
    private static final String DYNAMICACTION = "com.example.ex4.MyDynamicFliter";//动态广播
    static int times = 1; //广播的id
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(STATICACTION)){  //动作检测
//            Goods good = (Goods)intent.getExtras().get("goods");
//            assert good != null;
//            int image = good.path;
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), image);
//            //获取通知栏管理
//            NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//            //实例化通知栏构造器
//            Notification.Builder builder = new Notification.Builder(context);
//            //对Builder进行配置
//            builder.setContentTitle("新商品热卖")
//                    .setContentText(good.name+"仅售"+good.price+"!")
//                    .setTicker("您有一条新消息")  //通知首次出现在通知栏，带上升动画效果
//                    .setSmallIcon(image)
//                    .setLargeIcon(bm)
//                    .setAutoCancel(true);  //自助取消
//            Intent mIntent = new Intent(context, goodsactivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("goods", good);
//            mIntent.putExtras(bundle);
//            PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(mPendingIntent);
//            //绑定Notification, 发送通知请求
//            Notification notify = builder.build();
//            manager.notify(times++, notify);
        }
        else if(intent.getAction().equals(DYNAMICACTION)){  //动作检测
//            Goods good = (Goods)intent.getExtras().get("goods");
//            assert good != null;
//            int image = good.path;
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), image);
//            //获取通知栏管理
//            NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//            //实例化通知栏构造器
//            Notification.Builder builder = new Notification.Builder(context);
//            //对Builder进行配置
//            builder.setContentTitle("马上下单")
//                    .setContentText(good.name+"已添加到购物车")
//                    .setTicker("您有一条新消息")  //通知首次出现在通知栏，带上升动画效果
//                    .setLargeIcon(bm)
//                    .setSmallIcon(image)
//                    .setAutoCancel(true);  //自助取消
//            Intent mIntent = new Intent(context, cartactivity.class);
//            PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(mPendingIntent);
//            //绑定Notification, 发送通知请求
//            Notification notify = builder.build();
//            manager.notify(times++, notify);
            //加入购物车引起广播，在广播中触发更新widget
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Goods get_goods = (Goods)bundle.getSerializable("goods");
                //获取Views
                RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                mRemoteViews.setImageViewResource(R.id.widget_image, get_goods.path);
                mRemoteViews.setTextViewText(R.id.widget_text, get_goods.name+"已加入购物车");
                Intent intent2 = new Intent(context, cartactivity.class);
                PendingIntent pi = PendingIntent.getActivity(context,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
                mRemoteViews.setOnClickPendingIntent(R.id.widget, pi);
                //更新widget
                ComponentName mComponentName = new ComponentName(context, Widget.class);
                AppWidgetManager.getInstance(context).updateAppWidget(mComponentName, mRemoteViews);
            }
        }
    }

}
