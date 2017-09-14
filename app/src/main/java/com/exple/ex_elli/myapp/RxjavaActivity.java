package com.exple.ex_elli.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exple.ex_elli.myapp.service.MyService;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {

    @BindView(R.id.tv_rxjava)
    TextView tvRxjava;
    @BindView(R.id.btn_click)
    Button btnClick;
    @BindView(R.id.btn_cc)
    Button btnCc;
    @BindView(R.id.btn_service)
    Button btnService;
    @BindView(R.id.btn_rxjava2)
    Button btnRxjava2;

//    三者在执行速度方面的比较：StringBuilder >  StringBuffer  >  String
//    StringBuilder：线程非安全的　　StringBuffer：线程安全的
//    1.如果要操作少量的数据用 = String
//    2.单线程操作字符串缓冲区 下操作大量数据 = StringBuilder
//    3.多线程操作字符串缓冲区 下操作大量数据 = StringBuffer

    private StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);
        stringBuilder = new StringBuilder();
        tvRxjava.setVisibility(View.GONE);
    }

    private void rxJavaTest() {
        //first way create observable
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 20; i++) {
                    subscriber.onNext("Jack" + i);
                }
                subscriber.onCompleted();
            }
        });

        //second way

        //在下面的例子代码中，我们从一个已有的列表中创建一个Observable序列：
        List<String> items = new ArrayList<String>();
        items.add("1");
        items.add("10");
        items.add("100");
        items.add("200");
        Observable<String> observableFrom = Observable.from(items);

        //third way
        Observable<String> observableJust = Observable.just("i", "love", "you", "very", "much");

        //just()方法可以传入一到九个参数，它们会按照传入的参数的顺序来发射它们。
        // just()方法也可以接受列表或数组，就像from()方法，但是它不会迭代列表发射每个值,
        // 它将会发射整个列表。通常，当我们想发射一组已经定义好的值时会用到它。


        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Logger.i("rxjava", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("rxjava", "error");
            }

            @Override
            public void onNext(String s) {
                Logger.i("rxjava", s);
            }
        };

        //通过调用subscribe方法使观察者和订阅者产生关联，一旦订阅观察者就开始发送消息
        observable.subscribe(subscriber);

        //有了observable,再调用1中的subscribe方法即可开始打印
        observableFrom.subscribe(subscriber);
        observableJust.subscribe(subscriber);
    }

    private void operntion() {
        //假如你想对一个Observable重复发射三次数据。例如，我们用just()例子中的Observable：
        Observable<String> observableString = Observable.just("i", "love", "you", "very", "much").repeat(3);
        //first way create subscriber
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Logger.i("operntion", s);
            }
        };
        //second way
        Action1<String> onnext = new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };
        Action1 onerror = new Action1() {
            @Override
            public void call(Object o) {

            }
        };
        Action0 oncomplete = new Action0() {
            @Override
            public void call() {

            }
        };
        observableString.subscribe(subscriber);//通过添加repeat(3)，just里面的内容会被打印3次
        observableString.subscribe(onnext);
        observableString.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                // onnext

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //on error
            }
        }, new Action0() {
            @Override
            public void call() {
                //on complete
            }
        });

    }

    private void showOperation() {
        List<String> items = new ArrayList<>();
        items.add("1");
        items.add("10");
        items.add("100");
        items.add("200");
        items.add("300");
        items.add("400");
        items.add("500");
        items.add("600");
        items.add("700");
        Observable<String> observableFrom = Observable.from(items);
        observableFrom.map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return Integer.parseInt(s);
            }
        }).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer + "";
            }
        })
                .flatMap(new Func1<String, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(String s) {
                        final Integer inte = Integer.parseInt(s);
                        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                subscriber.onStart();
                                subscriber.onNext(inte);
                                subscriber.onCompleted();
                            }
                        });
                        return observable;
                    }
                }).concatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                return Observable.just(integer.toString());
            }
        }).map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return Integer.parseInt(s);
            }
        })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvRxjava.setVisibility(View.VISIBLE);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                //        observeOn作用于该操作符之后操作符直到出现新的observeOn操作符
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())


                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        stringBuilder.setLength(0);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        stringBuilder.append(integer + ",");
                        tvRxjava.setText(stringBuilder);
                    }
                });
    }

    private void lunxuntest() {
        //每隔两秒，发送前十个数
        Observable.interval(2, TimeUnit.SECONDS).take(20)
                .flatMap(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long aLong) {
                        String name = "Jack" + aLong;
                        return Observable.just(name);
                    }
                })
                .takeUntil(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        boolean flag = false;
                        if (s.equals("Jack10")) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                        return flag;
                    }
                }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvRxjava.setVisibility(View.VISIBLE);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        stringBuilder.setLength(0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        tvRxjava.setText(e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        stringBuilder.append(s + ",");
                        tvRxjava.setText(stringBuilder);
                    }
                });
    }

    private void rxjava2() {
       /* Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
//                        Log.e(TAG, "flatMap : accept : " + s + "\n");
                        tvRxjava.append("flatMap : accept : " + s + "\n");
                    }
                });*/
    }


    private void lunxuntest1() {
        Observable.interval(2, TimeUnit.SECONDS).take(10)
                .takeUntil(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        //retrun true  stop
                        return aLong > 6;
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvRxjava.setVisibility(View.VISIBLE);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        stringBuilder.setLength(0);
                    }

                    @Override
                    public void onError(Throwable e) {
                        tvRxjava.setText(e.toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        stringBuilder.append(aLong + ",");
                        tvRxjava.setText(stringBuilder);
                    }
                });
    }

    private void lunxun() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
        Observable.interval(5, TimeUnit.SECONDS).flatMap(new Func1<Long, Observable<String>>() {
            @Override
            public Observable<String> call(Long aLong) {
                return Observable.just(aLong + "");
            }
        }).take(60).takeUntil(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return Integer.parseInt(s) > 100;
            }
        })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        tvRxjava.setVisibility(View.VISIBLE);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        stringBuilder.setLength(0);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        stringBuilder.append(s + ",");
                        tvRxjava.setText(stringBuilder);
                    }
                });
    }

    private void schurlThread() {
        /*Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        threadInfo("OnSubscribe.call()");
                        subscriber.onNext("RxJava");
                    }
                })
                .subscribeOn(getNamedScheduler("create之后的subscribeOn"))
                .doOnSubscribe(() -> threadInfo(".doOnSubscribe()-1"))
                .subscribeOn(getNamedScheduler("doOnSubscribe1之后的subscribeOn"))
                .doOnSubscribe(() -> threadInfo(".doOnSubscribe()-2"))
                .subscribe(s -> {
                    threadInfo(".onNext()");
                    System.out.println(s + "-onNext");
                });*/

//        subscribeOn 作用于该操作符之前的 Observable 的创建操符作以及 doOnSubscribe 操作符 ，
//        换句话说就是 doOnSubscribe 以及 Observable 的创建操作符总是被其之后最近的 subscribeOn 控制
//        result
//        .doOnSubscribe()-2 => main
//                .doOnSubscribe()-1 => doOnSubscribe1之后的subscribeOn
//        OnSubscribe.call() => create之后的subscribeOn
//                .onNext() => create之后的subscribeOn
//        RxJava-onNext

       /* Observable.just("RxJava")
       .observeOn(getNamedScheduler("map之前的observeOn"))
                .map(s -> {
                    threadInfo(".map()-1");
                    return s + "-map1";
                })
                .map( s -> {
                    threadInfo(".map()-2");
                    return s + "-map2";
                })
                .observeOn(getNamedScheduler("subscribe之前的observeOn"))
                .subscribe(s -> {
                    threadInfo(".onNext()");
                    System.out.println(s + "-onNext");
                });*/

//        result
//        .map()-1 => map之前的observeOn
//                .map()-2 => map之前的observeOn
//                .onNext() => subscribe之前的observeOn
//        RxJava-map1-map2-onNext

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @OnClick({R.id.btn_click, R.id.btn_cc, R.id.btn_service,R.id.btn_rxjava2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_click:
                showOperation();
                break;
            case R.id.btn_cc:
//                lunxun();
                lunxuntest();

                break;
            case R.id.btn_service:
                Intent intent = new Intent(RxjavaActivity.this, MyService.class);
                Bundle bundle = new Bundle();
                bundle.putString("test","AAAA");
                bundle.putString("test1","BBBB");
                intent.putExtras(bundle);
                startService(intent);
                break;
            case R.id.btn_rxjava2:
                startActivity(new Intent(RxjavaActivity.this,Rxjava2Activity.class));
                break;
        }
    }

    public static Scheduler getNamedScheduler(final String name) {
//        return Schedulers.from(Executors.newCachedThreadPool(r -> new Thread(r, name)));
        return Schedulers.from(Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable runnable) {
                return newThread(runnable);
            }
        }));
    }

    public static void threadInfo(String caller) {
        System.out.println(caller + " => " + Thread.currentThread().getName());
    }

}
