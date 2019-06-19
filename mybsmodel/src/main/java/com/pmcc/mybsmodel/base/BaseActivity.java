package com.pmcc.mybsmodel.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.pmcc.mybsmodel.mvp.BasePresenter;
import com.pmcc.mybsmodel.requestutil.manager.ActivityManager;
import com.pmcc.mybsmodel.utils.ScreenSizeUtils;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends RxFragmentActivity {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConfig();
    }

    private void initConfig() {
        ActivityManager.getInstance().add(getLocalClassName(), this);
        mPresenter = createPresenter();
        if (!setNoScreenFit()) {
            ScreenSizeUtils.setCustomDensity();
        }
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        try {
            initData();
            initView();
            initListener();
        } catch (Exception e) {
            //异常处理
        }
    }



    /**
     * 设置mvp
     */
    protected abstract T createPresenter();

    /**
     * 是否采用屏幕适配
     */
    public abstract boolean setNoScreenFit();

    /**
     * 设置布局
     */
    public abstract int getLayoutResID();

    /**
     * 初始化数据
     */
    protected void initData() throws Exception {

    }

    /**
     * 初始化界面
     */
    protected void initView() throws Exception {

    }

    /**
     * 设置监听
     */
    protected void initListener() throws Exception {

    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().remove(getLocalClassName());
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
