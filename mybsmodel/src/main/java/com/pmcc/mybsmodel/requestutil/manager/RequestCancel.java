package com.pmcc.mybsmodel.requestutil.manager;

/**
 * 请求接口管理
 */
public interface RequestCancel {
    /**
     * 取消请求
     */
    void cancel();

    /**
     * 请求被取消
     */
    void onCanceled();
}
