package com.exple.ex_elli.myapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.exple.ex_elli.myapp.BaiduVoice.BaiduSpeechCompound;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class MyService extends Service {
    private String arg;
    private String arg1;

    protected CompositeSubscription subscription = new   CompositeSubscription();

    public MyService() {
    }

    protected void addSub(Subscription sub) {
        if (sub != null && !sub.isUnsubscribed()) {
            subscription.add(sub);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaiduSpeechCompound.getInstance().init(getApplication());
        Flowable.interval(2, TimeUnit.SECONDS).take(60).flatMap(new Function<Long, Flowable<String>>() {
            @Override
            public Flowable<String> apply(@NonNull Long aLong) throws Exception {

                return Flowable.just("Jack"+aLong);
            }
        }).takeUntil(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                boolean flag = s.equals("Jack" + 20);
                return flag;
            }
        }).onBackpressureBuffer()
                .subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(org.reactivestreams.Subscription s) {
                s.request(Long.MAX_VALUE);

            }

            @Override
            public void onNext(String s) {
                BaiduSpeechCompound.getInstance().speak("到账33.33元，请查看！");
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                BaiduSpeechCompound.getInstance().onRelease();
                stopSelf();
            }
        });
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            Bundle bundle = intent.getExtras();
            arg = bundle.getString("test");
            arg1 = bundle.getString("test1");
        }

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(subscription.hasSubscriptions()){
            subscription.unsubscribe();
        }
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
