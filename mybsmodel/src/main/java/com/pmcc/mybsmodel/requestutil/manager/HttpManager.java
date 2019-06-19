package com.pmcc.mybsmodel.requestutil.manager;

import com.pmcc.mybsmodel.BuildConfig;
import com.pmcc.mybsmodel.requestutil.observer.BaseObserver;
import com.pmcc.mybsmodel.requestutil.progress.HttpReqeustInterceptor;
import com.pmcc.mybsmodel.requestutil.progress.HttpResponseInterceptor;
import com.pmcc.mybsmodel.requestutil.service.UrlContract;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * 网络请求工具类，注意初始化
 */
public class HttpManager {
    private static HttpManager mInstance;
    private OkHttpClient.Builder mClient;
    private Retrofit.Builder builder;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        // 初始化okhttp
        // 初始化Retrofit
        initOkHttp();
        initRetrofit();

    }

    private void initRetrofit() {
        builder = new Retrofit.Builder()
                .baseUrl(UrlContract.ip)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create());
    }

    private void initOkHttp() {
        mClient = new OkHttpClient.Builder();
        //超时时间
        mClient.connectTimeout(15, TimeUnit.SECONDS)//15S连接超时
                .readTimeout(20, TimeUnit.SECONDS)//20s读取超时
                .writeTimeout(20, TimeUnit.SECONDS)//20s写入超时
                .retryOnConnectionFailure(true);//错误重连
        if (BuildConfig.DEBUG) {
            //mClient.addInterceptor(new HttpLoggingInterceptor());
        }
    }

    //获取对应的Service
    public <T> T createService(Class<T> service) {
        return builder.client(mClient.build()).build().create(service);
    }

    //获取对应的上传Service(带响应进度)
    public <T> T createReqeustService(Class<T> service, BaseObserver progressObserver) {
        mClient.addInterceptor(new HttpReqeustInterceptor(progressObserver));
        return builder.client(mClient.build()).build().create(service);
    }

    //获取对应的下载Service(带响应进度)
    public <T> T createResponseService(Class<T> service, BaseObserver progressObserver) {
        mClient.addInterceptor(new HttpResponseInterceptor(progressObserver));
        return builder.client(mClient.build()).build().create(service);
    }
}
