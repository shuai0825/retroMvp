package com.pmcc.mybsmodel.requestutil.manager;

import androidx.collection.ArrayMap;

import java.util.Set;

import io.reactivex.disposables.Disposable;

/**
 * http请求连接管理类
 */
public class RequestManager {
    private static volatile RequestManager mInstance;
    private ArrayMap<Object, Disposable> mMaps;//处理,请求列表
    public static RequestManager getInstance() {
        if (mInstance == null) {
            synchronized (RequestManager.class) {
                if (mInstance == null) {
                    mInstance = new RequestManager();
                }
            }
        }
        return mInstance;
    }
    private RequestManager() {
        mMaps = new ArrayMap<>();
    }
    /**
     *   添加tag
      */
    public void add(Object tag, Disposable disposable) {
        mMaps.put(tag, disposable);
    }

    /**
     * 移除tag
     * @param tag
     */
    public void remove(Object tag) {
        if (!mMaps.isEmpty()) {
            mMaps.remove(tag);
        }
    }

    /**
     * 移除指定tag
     * @param tag
     */
    public void cancel(Object tag) {
        if (mMaps.isEmpty()) {
            return;
        }
        if (mMaps.get(tag) == null) {
            return;
        }
        if (!mMaps.get(tag).isDisposed()) {
            mMaps.get(tag).dispose();
        }
        mMaps.remove(tag);
    }


    /**
     * 移除所有的请求连接
     */
    public void cancelAll() {
        if (mMaps.isEmpty()) {
            return;
        }
        //遍历取消请求
        Disposable disposable;
        Set<Object> keySet = mMaps.keySet();
        for (Object key : keySet) {
            disposable = mMaps.get(key);
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        mMaps.clear();
    }
    public boolean isDisposed(Object tag) {
        if (mMaps.isEmpty() || mMaps.get(tag) == null) return true;
        return mMaps.get(tag).isDisposed();
    }
}
