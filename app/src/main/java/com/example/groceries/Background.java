package com.example.groceries;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;

public class Background extends Service {
    int counter =0;
    @SuppressLint("WrongConstant")
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        startMyOwnForeground();
        }


    @Override
    public void onStart(Intent intent, int startId){
            // TODO Auto-generated method stub
            super.onStart(intent, startId);

        }


    @SuppressLint("WrongConstant")
    private void startMyOwnForeground()
    {


        Notification.Builder builder = new Notification.Builder(this.getApplicationContext())// 设置PendingIntent
                .setSmallIcon(R.drawable.shopping) // 设置状态栏内的小图标
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("Hi!! The price of grocery reach what you set!!")
                .setWhen(System.currentTimeMillis());
        builder.setOngoing(false);





                builder.setPriority(NotificationCompat.PRIORITY_LOW);
                builder.setVisibility(Notification.VISIBILITY_SECRET);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    NotificationChannel notificationChannel = new NotificationChannel("1", "channel", NotificationManager.IMPORTANCE_MIN);
                    notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
                    notificationChannel.setShowBadge(false);//是否显示角标
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.createNotificationChannel(notificationChannel);
                    builder.setChannelId(String.valueOf(1));
                    Notification notification = builder.build(); // 获取构建好的Notification
                    notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
                   manager.notify(1,notification);

                }

    }



    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    System.out.println(1);
        startMyOwnForeground();


        /*PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE);
        NotificationChannel chan = new NotificationChannel(
                "1",
                "My Foreground Service",
                NotificationManager.IMPORTANCE_LOW);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                this, "1");
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Price Tracked")
                        .setContentText("Hello!! The price of grocery has reached the price that you set!")
                        .setContentIntent(pendingIntent)
                        .build();

        // Notification ID cannot be 0.
        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();*/
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Alarm.class);
        this.sendBroadcast(broadcastIntent);
    }



    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                Log.i("Count", "=========  "+ (counter++));
            }
        };
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
