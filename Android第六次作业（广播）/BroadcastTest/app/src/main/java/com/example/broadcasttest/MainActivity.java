package com.example.broadcasttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private IntentFilter interntFiler;
    //private NetworkChangeReceiver networkChangeReceiver;
    private LocalReceive localReceive;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_main);
        /*interntFiler = new IntentFilter();
        interntFiler.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,interntFiler);*/
        localBroadcastManager= LocalBroadcastManager.getInstance(this);
        Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
                intent.setComponent(new ComponentName("com.example.broadcasttest","com.example.broadcasttest.MyBroadcastReceiver"));
                sendBroadcast(intent);
                sendOrderedBroadcast(intent,null);*/
                Intent intent= new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
        interntFiler = new IntentFilter();
        interntFiler.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localReceive = new LocalReceive();
        localBroadcastManager.registerReceiver(localReceive,interntFiler);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //unregisterReceiver(networkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceive);
    }
    class LocalReceive extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            Toast.makeText(context,"receive local broadcast",Toast.LENGTH_SHORT).show();
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
           // Toast.makeText(context,"network changes",Toast.LENGTH_SHORT).show();

            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable() ){
                Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context,"network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
