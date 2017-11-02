package com.yangb66.broadcaststudy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by 彪 on 2017/11/2.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("mainButton1.onClick.Broadcast")){
            Toast.makeText(context, "Receive mainButton1 onClick Broadcast", Toast.LENGTH_LONG).show();
            //获取系统服务systemService的通知栏管理NotificationManager的通知服务notificationService
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
            //实例化构造器builder
            Notification.Builder notificationBuilder = new Notification.Builder(context);
            notificationBuilder
                    .setContentTitle("title: notification test")
                    .setContentText("text: notification study")
                    .setTicker("你有一条新短信")                   //通知栏的文字，有上升效果
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);                           //单击后自动取消
            //绑定intent,返回mainActivity,点击可以返回主界面
            Intent mIntent = new Intent(context,MainActivity.class);
            mIntent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT); //pending不会立刻执行，flag用于删除当前
            notificationBuilder.setContentIntent(pendingIntent);
            //绑定notice
            Notification notification = notificationBuilder.build();
            notificationManager.notify(0, notification);
        }
        if(intent.getAction().equals("mainButton2.onClick.Broadcast")){
            Toast.makeText(context, "Receive mainButton2 onClick Broadcast", Toast.LENGTH_LONG).show();
        }
    }
}
