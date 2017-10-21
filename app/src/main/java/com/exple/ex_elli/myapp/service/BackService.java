package com.exple.ex_elli.myapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.exple.ex_elli.myapp.entries.event.TestEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;

public class BackService extends Service {
    public BackService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Flowable.interval(5, TimeUnit.SECONDS).take(20).onBackpressureBuffer()
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                        Logger.i("backservice---------->>>>>>>start");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Logger.i("backservice---------->>>>>>>"+aLong);
                        TestEvent testEvent = new TestEvent();
                        testEvent.setCode(aLong);
                        EventBus.getDefault().post(testEvent);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.i("backservice---------->>>>>>>"+t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Logger.i("backservice---------->>>>>>>Complete");
                    }
                });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
