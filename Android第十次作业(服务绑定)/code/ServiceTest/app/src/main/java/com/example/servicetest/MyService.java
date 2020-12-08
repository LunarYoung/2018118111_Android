package com.example.servicetest;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("MyService","startDownload executed");
        }

        public int getProgress(){
            Log.d("MyService","getProgress executed");
            return 0;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate executed");
    }
    public int onStartCommand(Intent intent,int flags,int startedId){
        Log.d("MyService","onStartCommand executed");

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                stopSelf();
            }
        }).start();*/
        return super.onStartCommand(intent,flags,startedId);

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyService","onDestroy executed");
    }
}
