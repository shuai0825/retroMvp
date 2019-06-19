package com.pmcc.mybsmodel.requestutil.observer;


import android.text.TextUtils;
import android.util.Log;

import com.pmcc.mybsmodel.requestutil.manager.RequestCancel;
import com.pmcc.mybsmodel.requestutil.manager.RequestManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 为请求取消，添加标记
 * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<T>, RequestCancel {

    /*请求标识*/
    private String mTag;

    @Override
    public void onNext(T t) {
        if (!TextUtils.isEmpty(mTag)) {
            RequestManager.getInstance().remove(mTag);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (!TextUtils.isEmpty(mTag)) {
            RequestManager.getInstance().remove(mTag);
        }
    }

    public abstract void onProgressChanges(long bytesWritten, long contentLength, int progress);

    @Override
    public void onComplete() {
        if (!TextUtils.isEmpty(mTag)&&!RequestManager.getInstance().isDisposed(mTag)) {
            cancel();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (!TextUtils.isEmpty(mTag)) {
            RequestManager.getInstance().add(mTag, d);
        }
    }


    @Override
    public void cancel() {
        if (!TextUtils.isEmpty(mTag)) {
            RequestManager.getInstance().cancel(mTag);
        }
    }

    @Override
    public void onCanceled() {
        Log.d("%s","onCanceled1");
        if (!TextUtils.isEmpty(mTag)) {
            RequestManager.getInstance().cancel(mTag);
            Log.d("%s","onCanceled2");
        }
    }

    /**
     * 是否已经处理
     *
     * @author ZhongDaFeng
     */
    public boolean isDisposed() {
        if (TextUtils.isEmpty(mTag)) {
            return true;
        }
        return RequestManager.getInstance().isDisposed(mTag);
    }

    /**
     * 设置标识请求的TAG
     *
     * @param tag
     */
    public void setTag(String tag) {
        this.mTag = tag;
    }


}
