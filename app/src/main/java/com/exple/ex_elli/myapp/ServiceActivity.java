package com.exple.ex_elli.myapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.exple.ex_elli.myapp.service.MyTestService;
import com.orhanobut.logger.Logger;

import static com.exple.ex_elli.myapp.R.id.tv_show;

public class ServiceActivity extends AppCompatActivity {
    private MyTestService msgService;
    private int progress = 0;
    private ProgressBar mProgressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //绑定Service
//        Intent intent = new Intent("com.example.communication.MSG_ACTION");


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        textView = (TextView)findViewById(tv_show);
        Button mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //开始下载
//                msgService.startDownLoad();
//                //监听进度
//                listenProgress();

                Intent intent1 = new Intent(ServiceActivity.this,MyTestService.class);
                bindService(intent1, conn, Context.BIND_AUTO_CREATE);
                startService(intent1);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (conn!=null){
//            unbindService(conn);
//        }

//        stopService()
    }

    /**
     * 监听进度，每秒钟获取调用MsgService的getProgress()方法来获取进度，更新UI
     */
    public void listenProgress(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                while(progress < MyTestService.MAX_PROGRESS){
                    progress = msgService.getProgress();
                    mProgressBar.setProgress(progress);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
            msgService = ((MyTestService.MsgBinder)service).getService();
            msgService.setOnProgressListener(new MyTestService.OnProgressListener() {
                @Override
                public void onProgress(String progress) {
                    Logger.i("onProgress");
                    textView.setText(progress);
                    if (conn!=null){
                        unbindService(conn);
                    }

                }

                @Override
                public void onFailed() {
                    Logger.i("onFailed");
                }
            });
        }
    };
}
