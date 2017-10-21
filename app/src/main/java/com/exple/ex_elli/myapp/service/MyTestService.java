package com.exple.ex_elli.myapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MyTestService extends Service {
    private final static String TAG = MyTestService.class.getName();
    private StringBuilder stringBuilder;
    public MyTestService() {
    }

    /**
     * 进度条的最大值
     */
    public static final int MAX_PROGRESS = 100;
    /**
     * 进度条的进度值
     */
    private int progress = 0;

    /**
     * 更新进度的回调接口
     */
    private OnProgressListener onProgressListener;

    private Long fla ;
    /**
     * 注册回调接口的方法，供外部调用
     * @param onProgressListener
     */
    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    /**
     * 增加get()方法，供Activity调用
     * @return 下载进度
     */
    public int getProgress() {
        return progress;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        stringBuilder = new StringBuilder();
        Logger.init(TAG);
        Logger.i("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i("onStartCommand");
        showL();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Logger.i("onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.i("onUnbind");
        return super.onUnbind(intent);
    }

    /**
     * 模拟下载任务，每秒钟更新一次
     */
    public void startDownLoad(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                while(progress < MAX_PROGRESS){
                    progress += 5;

                    //进度发生变化通知调用方
                    if(onProgressListener != null){
                        onProgressListener.onProgress("");
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    private void showL(){
        Observable.interval(1,3, TimeUnit.SECONDS).take(6).flatMap(new Function<Long, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Long aLong) throws Exception {
                fla = aLong;
                return Observable.just("Jack"+aLong);
            }
        }).takeUntil(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                boolean flag = false;
                if (s.equalsIgnoreCase("jack3"))
                    flag = true;
                return false;
            }
        })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Logger.i("onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Logger.i("onNext");
                stringBuilder.append(s+"-->");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.i("onError");
                onProgressListener.onFailed();
            }

            @Override
            public void onComplete() {
                Logger.i("onComplete");
                if (fla == 5){
                    onProgressListener.onProgress("轮询结束，没有返回争取结果");
                }else {
                    onProgressListener.onProgress(stringBuilder.toString());
                }

                stopSelf();
            }
        });
    }

    /**
     * 返回一个Binder对象
     */
    @Override
    public IBinder onBind(Intent intent) {
        Logger.i("onBind");
        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         * @return
         */
        public MyTestService getService(){
            return MyTestService.this;
        }
    }

    public interface OnProgressListener {
        void onProgress(String progress);
        void onFailed();
    }
}
