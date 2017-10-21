package com.exple.ex_elli.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.btn_click_jump)
    Button btnClickJump;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.btn_click_test)
    Button btnClickTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        timer.schedule(task,1000,3000);
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    //使用handler主线程进行自动轮播

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            //获取当前条目

//            int index = viewpager.getCurrentItem();

            //向右轮播

//            viewpager.setCurrentItem(index+=1);
            switch (msg.what) {
                case 1:
                    Logger.i("msss111111---");

                    //间隔一秒
//                    Message message = new Message();
//                    message.what = 1;
//                    if (handler!=null){
//                        handler.sendMessageDelayed(message, 3000);
//                    }
                    handler.sendEmptyMessageDelayed(1, 3000);
                    break;
                case 2:
                    Logger.i("msss22222222222-------");
                    break;
                default:
                    break;
            }
        }

        ;
    };

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    @OnClick({R.id.btn_click_jump, R.id.btn_click_test,R.id.btn_click_dataconstrust})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_click_jump:
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "value");
                intent.putExtras(bundle);
                startActivity(MainActivity.makeIntent(SplashActivity.this, bundle));
//                timer.schedule(task,1000,3000);
                Logger.i("msss22222222222-------");
                break;
            case R.id.btn_click_test:
//                task.cancel();
                handler.removeCallbacksAndMessages(null);
                Logger.i("msss11111111111111112-------");
                break;
            case R.id.btn_click_dataconstrust:
//                task.cancel();
                Intent intentj = new Intent(SplashActivity.this, JavaConstructActivity.class);
                startActivity(intentj);
                Logger.i("msss11111111111111112-------");
                break;
        }
    }
}
