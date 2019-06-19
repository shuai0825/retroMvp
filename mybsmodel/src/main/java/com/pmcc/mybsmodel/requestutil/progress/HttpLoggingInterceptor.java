package com.pmcc.mybsmodel.requestutil.progress;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 打印返回的json数据拦截器
 */
public class HttpLoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.d("%a", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        //发送request请求
        Response response = chain.proceed(request);
        //得到请求后的response实例，做相应操作
        long t2 = System.nanoTime();
        Log.d("%a", String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return chain.proceed(request);
    }
}
