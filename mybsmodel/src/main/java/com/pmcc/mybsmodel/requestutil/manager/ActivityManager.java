package com.pmcc.mybsmodel.requestutil.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.ArrayMap;

import androidx.annotation.RequiresApi;

import java.util.Set;

import io.reactivex.disposables.Disposable;

public class ActivityManager {
    private static volatile ActivityManager mInstance;
    private ArrayMap<String, Activity> mMaps;//处理,请求列表

    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (RequestManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)//版本大于4.4
    private ActivityManager() {
        mMaps = new ArrayMap<>();
    }

    /**
     * 添加activity
     */
    public void add(String tag, Activity activity) {
        mMaps.put(tag, activity);
    }

    /**
     * 移除activity
     */
    public void remove(String tag) {
        if (mMaps.containsKey(tag)) {
            mMaps.remove(tag);
        }
    }

    /**
     * 移除所有activity
     */
    public void removeAll() {
        if (mMaps.isEmpty()) {
            return;
        }
        Set<String> keySet = mMaps.keySet();
        for (Object key : keySet) {
            if (!mMaps.get(key).isFinishing()) {
                mMaps.get(key).finish();
            }
        }
        mMaps.clear();
    }
}
