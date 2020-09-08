package com.boomerdroid.remotehelper;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notify extends Application {
    public  static final String channelId="serviceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel serviceNotification=new NotificationChannel(
                channelId,
                    "Remote Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceNotification);
        }
    }
}
