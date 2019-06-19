package com.pmcc.mybsmodel.requestutil.progress;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加请求头
 */
public class HttpParameterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl modifiedUrl = originalRequest.url().newBuilder()
// 公共参数
// .addQueryParameter("Connection", "close")
                .build();
        Request request = originalRequest.newBuilder()
//请求头
//               .addHeader("Connection", "close")

                .url(modifiedUrl).build();
        return chain.proceed(request);
    }
}
