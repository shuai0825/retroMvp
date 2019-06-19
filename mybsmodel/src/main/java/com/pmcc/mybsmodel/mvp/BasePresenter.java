package com.pmcc.mybsmodel.mvp;

import com.trello.rxlifecycle3.LifecycleProvider;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 该页面主要将present跟view绑定
 * @param <T>
 */
public class BasePresenter<T>  implements Contract.PersenterIntfc{
    protected T mView;
    /**
     * 初始化，并绑定view
     * @param view
     */
    public BasePresenter(T view){
        attachView(view);
    }

    /**
     * 绑定view
     * @param view
     */
    private void attachView(T view) {
        mView=view;
    }

    /**
     * 分离view
     */
    public void detachView(){
        mView=null;
    }

    @Override
    public void getDatas(String url, Map<String, Object> parameter, boolean setTag, LifecycleProvider lifecycle) {

    }

    @Override
    public void postDatas(String url, Map<String, Object> parameter, boolean setTag, LifecycleProvider lifecycle) {

    }

    @Override
    public void postFiles(String url, Map<String, Object> parameter, List<File> fileList, boolean setTag, LifecycleProvider lifecycle) {

    }

    @Override
    public void downFile(String url, File file, boolean setTag, LifecycleProvider lifecycle) {

    }
}
