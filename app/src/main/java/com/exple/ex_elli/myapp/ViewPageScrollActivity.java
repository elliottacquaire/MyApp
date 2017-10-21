package com.exple.ex_elli.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.exple.ex_elli.myapp.viewpagescroll.SpeedScroller;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ViewPageScrollActivity extends AppCompatActivity {

    @BindView(R.id.viewpage)
    ViewPager viewpager;
    @BindView(R.id.ttt)
    Button ttt;
    @BindView(R.id.ttt1)
    Button ttt1;

    private Subscription mViewPagerSubscribe;

    private int currentPosition = 0;

    private MyViewPagerAdapter myViewPagerAdapter;
    private List<View> listView;
    private SpeedScroller mScroller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page_scroll);
        ButterKnife.bind(this);
        initData();

        ttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.i("msss3333333333333333-------");
            }
        });
        ttt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.i("sdfsadfsdafasf-------");
            }
        });
    }

    private void initData() {
        listView = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            View view = getLayoutInflater().inflate(R.layout.item_auto_poll, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_content);
            textView.setText("jfdasjfas----" + i);

            listView.add(view);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initview();
    }

    private void initview() {
        myViewPagerAdapter = new MyViewPagerAdapter<View>(listView);
        viewpager.setAdapter(myViewPagerAdapter);
        viewpager.setCurrentItem(0);
//        viewpager.setPageTransformer(true,new ViewPageTransformer());
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new SpeedScroller(viewpager.getContext(), new LinearInterpolator());
            mField.set(viewpager, mScroller);
            mScroller.setmDuration(1000);//ms
        } catch (Exception e) {
            e.printStackTrace();
        }
//        start();
//        if (handler != null) {
//            Message message = new Message();
//            message.what = 1;
//            handler.sendMessageDelayed(message,3000);
//            handler.sendEmptyMessageDelayed(1, 3000);
        timer.schedule(task, 1000, 3000); // 1s后执行task,经过1s再次执行
//        }

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state != ViewPager.SCROLL_STATE_IDLE) return;
                if (listView == null && listView.size() <= 0) return;

//                if (currentPosition == 0) {
//                    viewpager.setCurrentItem(listView.size() - 2, false);
//                } else if (currentPosition == listView.size() - 1) {
//                    viewpager.setCurrentItem(1, false);
//                }
            }
        });

        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Logger.i("msss3333333333333333-------");
                stop();
                startActivity(new Intent(ViewPageScrollActivity.this, MainActivity.class));
                return true;
            }
        });
    }

    @OnClick(R.id.ttt)
    public void onViewClicked() {
        Logger.i("msss3344444444444444------");
        timer.cancel();
        handler.removeCallbacksAndMessages(null);
    }

    private class MyViewPagerAdapter<T extends View> extends PagerAdapter {
        private List<T> mList;

        public MyViewPagerAdapter(List<T> mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
//            return mList.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mList.size();
            if (position < 0) {
                position = mList.size() + position;
            }
            ViewParent viewParent = mList.get(position).getParent();
            if (viewParent != null) {
                ViewGroup viewGroup = (ViewGroup) viewParent;
                viewGroup.removeView(mList.get(position));
            }
            container.addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            position = position % mList.size();
//            container.removeView(mList.get(position));
        }
    }

    public void start() {
        mViewPagerSubscribe = Observable.interval(2, 3, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        // 进行轮播操作
                        if (viewpager != null && listView != null && listView.size() > 1) {
//                            int currentPosition1 = viewpager.getCurrentItem();
//                            if(currentPosition1 == viewpager.getAdapter().getCount() - 1){
//                                // 最后一页
//                                viewpager.setCurrentItem(0);
//                            }else{
//                                viewpager.setCurrentItem(currentPosition1 + 1);
//                            }

                            currentPosition++;
                            viewpager.setCurrentItem(currentPosition);
                        }
                    }
                });
    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
//            handler.sendMessage(message);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
//        stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        stop();
    }

    public void stop() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
            handler.removeMessages(1);
        }
        if (mViewPagerSubscribe != null && mViewPagerSubscribe.isUnsubscribed()) {
            mViewPagerSubscribe.unsubscribe();
        }
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
                    currentPosition++;
                    viewpager.setCurrentItem(currentPosition);
                    //间隔一秒
//                    Message message = new Message();
//                    message.what = 1;
//                    if (handler!=null){
//                        handler.sendMessageDelayed(message, 3000);
//                    }
//                    handler.sendEmptyMessageDelayed(1, 3000);
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
}
