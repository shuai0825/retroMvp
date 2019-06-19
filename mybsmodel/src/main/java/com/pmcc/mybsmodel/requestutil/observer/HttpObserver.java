package com.pmcc.mybsmodel.requestutil.observer;

import com.pmcc.mybsmodel.requestutil.exception.ApiException;

/**
 * 请求返回值
 *
 * @param <T>
 */
public abstract class HttpObserver<T> extends BaseObserver<T> {

    @Override
    public void onNext(T t) {
        super.onNext(t);
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (e instanceof ApiException) {
            onFail((ApiException) e);
        }

    }

    /**
     * 该方法主要用于baseobserver回调，直接使用调用onProgress
     *
     * @param bytesWritten
     * @param contentLength
     * @param progress
     */
    @Override
    public void onProgressChanges(long bytesWritten, long contentLength, int progress) {
        onProgress(bytesWritten, contentLength, progress);
    }

    //进度成功的回调
    public abstract void onSuccess(T t);

    //进度失败回调
    public abstract void onFail(ApiException e);

    //进度回调变化
    public void onProgress(long bytesWritten, long contentLength, int progress) {
    }


}
