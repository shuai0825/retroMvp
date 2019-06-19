package com.pmcc.retromvp;

import com.pmcc.mybsmodel.base.BaseApp;
import com.pmcc.mybsmodel.requestutil.manager.HttpManager;

public class MyApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpManager.getInstance().init();//初始化网络请求工具类
    }

}
