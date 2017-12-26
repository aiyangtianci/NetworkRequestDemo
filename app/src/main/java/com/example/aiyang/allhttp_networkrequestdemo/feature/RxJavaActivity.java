package com.example.aiyang.allhttp_networkrequestdemo.feature;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aiyang.allhttp_networkrequestdemo.R;
import com.example.aiyang.allhttp_networkrequestdemo.ui.TopBarBaseActivity;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by djmsh on 17/7/31.
 */

public class RxJavaActivity extends TopBarBaseActivity implements DialogInterface.OnClickListener {

    TextView toshow_tv;
    String txt ="";
    @Override
    protected int getConentView() {
        return R.layout.activity_rxjava;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("RxJava练习");
        setTopLeftButton(R.mipmap.back_whait, new onClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        toshow_tv = (TextView) findViewById(R.id.toshow_tv);


        //原始 被观察者  猪蹄店老板
        Observable<String> mObservable = Observable.create(
                new Observable.OnSubscribe<String>(){
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("hello world！");
                        subscriber.onCompleted();
                    }
                }
        );

        //原始版 订阅者  顾客
        Subscriber<String> mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                txt = s;
            }
        };

        //新版 订阅者  顾客
        Action1<String> mNewSubscriber =new Action1<String>() {

            @Override
            public void call(String s) {
                txt =s;
            }
        };

        //猪蹄店老板接单 = 顾客订阅
//        mObservable.subscribe(mSubscriber);
        mObservable.subscribe(mNewSubscriber);
        /**
         * 店面升级  简化版
         */


        //新版 被观察者   猪蹄店老板
        Observable<String> myObservable =Observable.just("hello world！");
        myObservable.subscribe(mNewSubscriber);


        /**
         * 最终升级
         */

        Observable.just("hello world!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        txt =s;
                    }
                });

        toshow_tv.setText(txt);


    }


    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

}
