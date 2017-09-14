package com.exple.ex_elli.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class Rxjava2Activity extends AppCompatActivity {
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.tv_display)
    TextView tvDisplay;
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.btn_backpresure)
    Button btnBackpresure;

    private Disposable mDisposable;
    private Subscription msubscription;
    private StringBuilder stringBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        ButterKnife.bind(this);
        tvShow.setVisibility(View.GONE);
        tvDisplay.setVisibility(View.GONE);
        stringBuilder = new StringBuilder();
    }

    private void operationCreate() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onNext("4");
                e.onNext("5");
                e.onComplete();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(@NonNull String s) throws Exception {
                return Integer.parseInt(s);
            }
        })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                        return Observable.just("jack"+integer);
                    }
                })
               .concatMap(new Function<String, ObservableSource<String>>() {
                   @Override
                   public ObservableSource<String> apply(@NonNull String s) throws Exception {
                       return Observable.just(s);
                   }
               })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        stringBuilder.append(s+",");
                        tvShow.setText(stringBuilder);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        stringBuilder.setLength(0);
                    }
                });
    }

    private void flowable(){
        Flowable.interval(2, TimeUnit.SECONDS).take(20)
                .flatMap(new Function<Long, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(@NonNull Long aLong) throws Exception {

                        return Flowable.just("jack"+aLong);
                    }
                })
                .onBackpressureDrop()
                .takeUntil(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                return false;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });


        Observable.just("1","2","3","4").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }
    private void onBackpressure() {
        Flowable.create(new FlowableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onNext("4");
                e.onNext("5");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        tvShow.setVisibility(View.VISIBLE);
                        subscription.request(Long.MAX_VALUE);
                        msubscription = subscription;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String s) {
                        stringBuilder.append(s+",");
                        tvShow.setText(stringBuilder);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        stringBuilder.setLength(0);
                    }
                });
    }

    @OnClick({R.id.btn_create, R.id.btn_backpresure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                operationCreate();
                break;
            case R.id.btn_backpresure:
                onBackpressure();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

}
