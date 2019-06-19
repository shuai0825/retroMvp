package com.pmcc.mybsmodel.mvp;

import android.util.Log;

import com.pmcc.mybsmodel.requestutil.exception.ApiException;
import com.pmcc.mybsmodel.requestutil.observer.HttpObserver;
import com.pmcc.mybsmodel.requestutil.response.ResponseTransformer;
import com.pmcc.mybsmodel.requestutil.schedulers.SchedulerProvider;
import com.pmcc.mybsmodel.requestutil.service.UrlContract;
import com.trello.rxlifecycle3.LifecycleProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

public class Presenter extends BasePresenter<Contract.ViewIntfc> {
    private Model model;

    private Contract.ViewIntfc view;

    /**
     * 初始化，并绑定view
     *
     * @param view
     */
    public Presenter(Contract.ViewIntfc view) {
        super(view);
        model = new Model();
        //mDisposable = new CompositeDisposable();
    }


    /**
     * 中断网络请求
     */
    public void onDisView() {
        detachView();
        model = null;
    }

    /**
     * 通用get请求
     *
     * @param url
     * @param parameter
     */
    @Override
    public void getDatas(String url, Map<String, Object> parameter, boolean setTag, LifecycleProvider lifecycle) {
        MyHttpObserver myHttpObserver = new MyHttpObserver(setTag, url);
        model.getData(UrlContract.getNewVersion, new HashMap<>(), new HashMap<>())
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .compose(SchedulerProvider.getInstance().composeContext(lifecycle))
                .doOnDispose(myHttpObserver::onCanceled).subscribe(myHttpObserver);

    }

    /**
     * 通用post请求
     *
     * @param url
     * @param parameter
     */
    @Override
    public void postDatas(String url, Map<String, Object> parameter, boolean setTag, LifecycleProvider lifecycle) {
        MyHttpObserver myHttpObserver = new MyHttpObserver(setTag, url);
        model.postData(UrlContract.getNewVersion, new HashMap<>(), new HashMap<>())
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .compose(SchedulerProvider.getInstance().composeContext(lifecycle))
                .doOnDispose(myHttpObserver::onCanceled).subscribe(myHttpObserver);
    }

    /**
     * 通用上传文件
     *
     * @param url
     */
    @Override
    public void postFiles(String url, Map<String, Object> parameter, List<File> fileList, boolean setTag, LifecycleProvider lifecycle) {
        MyHttpObserver myHttpObserver = new MyHttpObserver(setTag, url);
        model.uploadData(url, parameter, new HashMap<>(), fileList, myHttpObserver)
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .compose(SchedulerProvider.getInstance().composeContext(lifecycle))
                .doOnDispose(myHttpObserver::onCanceled).subscribe(myHttpObserver);

    }

    /**
     * 通用下载传文件
     *
     * @param url
     */
    @Override
    public void downFile(String url, File file, boolean setTag, LifecycleProvider lifecycle) {
        MyHttpObserver myHttpObserver = new MyHttpObserver(setTag, url);

        model.downData(url, new HashMap<>(), myHttpObserver)
                .map((Function<ResponseBody, Object>) responseBody -> {
                    writeFile(responseBody.byteStream(), file.getAbsolutePath());
                    return file.getAbsolutePath();
                })
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .compose(SchedulerProvider.getInstance().composeContext(lifecycle))
                .doOnDispose(() -> myHttpObserver.onCanceled())
                .subscribe(myHttpObserver);
    }

    class MyHttpObserver<T> extends HttpObserver<T> {
        private final String url;

        public MyHttpObserver(boolean setTag, String url) {
            this.url = url;
            if (setTag) {
                setTag(url);
            } else {
                setTag(null);
            }
        }

        @Override
        public void onSuccess(T t) {
            mView.getDataSuccess(url, String.valueOf(t));
        }

        @Override
        public void onFail(ApiException e) {
            mView.getDataFail(url, "" + e.getCode(), e.getMessage());
        }


        @Override
        public void onProgress(long bytesWritten, long contentLength, int progress) {
            Log.d("%s", "" + progress);
        }

    }

    private void writeFile(InputStream inputString, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            inputString.close();
            fos.close();

        } catch (FileNotFoundException e) {
            // listener.onFail("FileNotFoundException");
        } catch (IOException e) {
            // listener.onFail("IOException");
        }

    }

}
