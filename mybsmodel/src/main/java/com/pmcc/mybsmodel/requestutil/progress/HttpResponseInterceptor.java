package com.pmcc.mybsmodel.requestutil.progress;

import com.pmcc.mybsmodel.requestutil.observer.BaseObserver;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 文件下载拦截器
 */
public class HttpResponseInterceptor implements Interceptor {
    private final BaseObserver progressObserver;

    public HttpResponseInterceptor(BaseObserver progressObserver) {
        this.progressObserver = progressObserver;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        //包装响应体
        return response.newBuilder().body(new ProgressResponseBody(response.body(), progressObserver)).build();
    }
}
