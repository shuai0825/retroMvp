package com.pmcc.mybsmodel.requestutil.progress;

import com.pmcc.mybsmodel.requestutil.observer.BaseObserver;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  文件上传拦截器
 */
public class HttpReqeustInterceptor implements Interceptor {

    private BaseObserver progressObserver;

    public HttpReqeustInterceptor(BaseObserver progressObserver) {
        this.progressObserver = progressObserver;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(null == request.body()){
            return chain.proceed(request);
        }

        Request build = request.newBuilder()
                .method(request.method(),
                        new ProgressRequestBody(request.body(),
                                progressObserver))
                .build();
        return chain.proceed(build);
    }

}
