package com.pmcc.mybsmodel.requestutil.schedulers;

import android.content.Context;

import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.components.RxActivity;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * retrofit的线程切换
 */
public class SchedulerProvider {

    private static SchedulerProvider mInstance;

    //不可以new创建对象
    private SchedulerProvider() {
    }

    public static SchedulerProvider getInstance() {
        if (mInstance == null) {
            synchronized (SchedulerProvider.class) {
                if (mInstance == null) {
                    mInstance = new SchedulerProvider();
                }
            }
        }
        return mInstance;
    }

    /**
     * 用于计算
     **/
    public Scheduler computation() {
        return Schedulers.computation();
    }

    /**
     * 子线程
     **/
    public Scheduler io() {
        return Schedulers.io();
    }

    /**
     * ui线程
     **/
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    /**
     * io线程转化为ui线程
     **/
    public <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(io())
                .observeOn(ui());
    }

    /**
     * 利用Rxlifecycle取消订阅，避免rxjava内存泄漏
     **/
    public <T> ObservableTransformer<T, T> composeContext(LifecycleProvider lifecycle) {
        return observable -> observable.compose(lifecycle.bindUntilEvent(ActivityEvent.DESTROY));
    }

    /**
     * 上面的另种实现方式（暂不使用）
     **/
    public <T> ObservableSource<T> composeContext1(Context context, Observable<T> observable) {
        if (context instanceof RxActivity) {
            return observable.compose(((RxActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        } else if (context instanceof RxFragmentActivity) {
            return observable.compose(((RxFragmentActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        } else if (context instanceof RxAppCompatActivity) {
            return observable.compose(((RxAppCompatActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        } else {
            return observable;
        }
    }

}
