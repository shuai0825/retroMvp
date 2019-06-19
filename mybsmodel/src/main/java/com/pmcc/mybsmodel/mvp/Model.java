package com.pmcc.mybsmodel.mvp;

import com.pmcc.mybsmodel.requestutil.manager.HttpManager;
import com.pmcc.mybsmodel.requestutil.observer.BaseObserver;
import com.pmcc.mybsmodel.requestutil.response.BaseResponse;
import com.pmcc.mybsmodel.requestutil.service.ApiService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 数据model，封装请求方法
 */

public class Model implements Contract.ModelIntfc {
    /**
     * get请求
     * @param url
     * @param parameter
     * @param header
     * @return
     */
    @Override
    public Observable<BaseResponse<String>> getData(String url, Map<String, Object> parameter, Map<String, Object> header) {
        return HttpManager.getInstance().createService(ApiService.class).get(url, parameter, header);
    }

    /**
     * post请求
     * @param url
     * @param parameter
     * @param header
     * @return
     */
    @Override
    public Observable<BaseResponse<String>> postData(String url, Map<String, Object> parameter, Map<String, Object> header) {
        return HttpManager.getInstance().createService(ApiService.class).post(url, parameter, header);
    }

    /**
     * 上传文件，
     * @param url
     * @param parameter
     * @param fileList
     * @return
     */
    @Override
    public Observable<BaseResponse<String>> uploadData(String url, Map<String, Object> parameter,Map<String, Object> header, List<File> fileList,BaseObserver baseObserver) {
        //处理文件
        ArrayList<MultipartBody.Part> parts = new ArrayList<>();
        for (File file : fileList) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            parts.add(body);
        }
        //处理参数
        Map<String, RequestBody> params = new HashMap<>();
        MediaType textType = MediaType.parse("text/plain");
        for (String key : parameter.keySet()) {
            params.put(key, RequestBody.create(textType, String.valueOf(parameter.get(key))));
        }
        //uploadBody,上传
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), files[i]);
//        params.put("file\"; filename=\"" + files[i].getName() + "", requestBody);

        return HttpManager.getInstance().createReqeustService(ApiService.class,baseObserver).upload(url, params, header,parts);
    }

    /**
     * 下载文件
     * @param url
     * @param header
     * @return
     */
    @Override
    public Observable<ResponseBody> downData(String url, Map<String, Object> header, BaseObserver baseObserver) {
        return HttpManager.getInstance().createResponseService(ApiService.class,baseObserver).download("12", url, header);
    }


}
