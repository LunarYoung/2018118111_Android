package com.example.asynctacktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button start_dowanload;
    Button stop_download;
    ProgressBar progressBar;
    TextView textView;
    downloadTask task;

    class downloadTask extends AsyncTask<Void ,Integer,Boolean>{

        @Override
        protected void onPreExecute(){
            textView.setText("下载中");
            progressBar.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.i("TaskTest", "当前线程id: "+Thread.currentThread().getId());
            Log.i("TaskTest", "主线程id: "+getMainLooper().getThread().getId());
            try{
                int count=0;
                while (count<99){
                    count+=1;
                    publishProgress(count);
                    Thread.sleep(50);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer...progresses){
            progressBar.setProgress(progresses[0]);
            textView.setText("loading..."+progresses[0]+"%");

        }

        @Override
        protected void onPostExecute(Boolean result){
            if (result){
                textView.setText("下载成功");
            }
            else {
                textView.setText("下载失败");
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_dowanload = (Button)findViewById(R.id.start_download);
        stop_download  = (Button)findViewById(R.id.stop_download);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        textView = (TextView)findViewById(R.id.te);
        start_dowanload.setOnClickListener(this);
        stop_download.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.start_download:
                try{
                    task = new downloadTask();
                    task.execute();
                }catch (Exception e){
                    Log.d("TaskTest",e.toString());
                }
                break;
            case R.id.stop_download:

                task.cancel(true);
                break;
            default:
                break;
        }
    }
}
