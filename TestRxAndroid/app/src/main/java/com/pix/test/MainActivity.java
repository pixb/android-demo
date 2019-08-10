package com.pix.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        testRx1();
//        testRxHelloWorld();
//        testOperator();
        testOperator2();
    }

    private void testRx1() {
        String [] names = new String[5];
        names[0] = "123";
        names[1] = "aaa";
        names[2] = "ddd";
        names[3] = "eee";
        names[4] = "fff";

        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,"s:" + s);
            }
        });
    }

    /**
     * hello world测试
     */
    private void testRxHelloWorld() {
        //定义一个事件源
        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello world!");
                subscriber.onCompleted();
            }
        });

        //定义一个订阅者
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String s) {
                Log.d(TAG,"mySubscriber:" + s);
            }
        };
        //事件订阅订阅者
        myObservable.subscribe(mySubscriber);

        //简化事件源
        Observable<String> justObservable = Observable.just("hello just observable");
       //简化订阅者
        Action1<String> justAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,"justAction.call(),s:" +s);
            }
        };
        //关联事件
        justObservable.subscribe(justAction);

        //链式编程
        Observable.just("hello link short just!").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,"short.just(),s:" +s);
            }
        });
    }

    /**
     * 测试Rxjava的操作符
     */
    private void testOperator() {
        //中间变换内容拼接字符串
        Observable.just("hell operator!")
                //变换
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "--pix";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "testOperator.call(),s:" + s);
                    }
                });

        //返回值变换，观察者和订阅者值不同的传递,eg.取得字符串哈希值
        Observable.just("hello")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG,"testOperator(),String hashCode:" + integer);
                    }
                });

    }

    /**
     * 高级操作符测试
     */
    private void testOperator2() {
        //多个输入多个输出
        Observable.just("aa", "bb", "cc").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "testOperator2(),s:" + s);
            }
        });

        //嵌套式
        Observable.just("hello world").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                List<String> list = new ArrayList<String>();
                list.add(s + "aaa");
                list.add(s + "bbb");
                list.add(s + "ccc");
                list.add(s + "ddd");
                Observable.from(list).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "testOperator2(),s:" + s);
                    }
                });
            }
        });
        //展平测试
        Observable.just("hello world")
            .flatMap(new Func1<String, Observable<String>>() {
                @Override
                public Observable<String> call(String s) {
                    List<String> list = new ArrayList<String>();
                    list.add(s + "aaa");
                    list.add(s + "bbb");
                    list.add(s + "ccc");
                    list.add(s + "ddd");
                    return Observable.from(list);
                }
            })
            .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,"flatMap(),s:" +s);
            }
        });
        

    }

}
