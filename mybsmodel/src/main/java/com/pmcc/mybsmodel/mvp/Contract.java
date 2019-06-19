package com.pmcc.mybsmodel.mvp;


import com.pmcc.mybsmodel.requestutil.observer.BaseObserver;
import com.pmcc.mybsmodel.requestutil.response.BaseResponse;
import com.trello.rxlifecycle3.LifecycleProvider;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Zaifeng on 2018/3/1.
 */

public class Contract {

    public interface PersenterIntfc {
        void getDatas(String url, Map<String, Object> parameter,boolean setTag, LifecycleProvider lifecycle);

        void postDatas(String url, Map<String, Object> parameter,boolean setTag, LifecycleProvider lifecycle);

        void postFiles(String url, Map<String, Object> parameter, List<File> fileList,boolean setTag, LifecycleProvider lifecycle);

        void downFile(String url,  File file,boolean setTag, LifecycleProvider lifecycle);
    }

    public interface ViewIntfc {
        void getDataSuccess(String url, String data);
        void getDataFail(String url,String code, String errorMessage);
    }



    public interface ModelIntfc {
        Observable<BaseResponse<String>> getData(String url, Map<String, Object> parameter, Map<String, Object> header);

        Observable<BaseResponse<String>> postData(String url, Map<String, Object> parameter, Map<String, Object> header);

        Observable<BaseResponse<String>> uploadData(String url, Map<String, Object> parameter, Map<String, Object> header, List<File> fileList, BaseObserver baseObserver);

        Observable<ResponseBody> downData(String url, Map<String, Object> header,BaseObserver baseObserver);
    }

}
