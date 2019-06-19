package com.pmcc.retromvp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.pmcc.mybsmodel.mvp.Contract;
import com.pmcc.mybsmodel.mvp.Presenter;
import com.pmcc.mybsmodel.requestutil.manager.HttpManager;
import com.pmcc.mybsmodel.requestutil.observer.BaseObserver;
import com.pmcc.mybsmodel.requestutil.observer.HttpObserver;
import com.pmcc.mybsmodel.requestutil.response.ResponseTransformer;
import com.pmcc.mybsmodel.requestutil.schedulers.SchedulerProvider;
import com.pmcc.mybsmodel.requestutil.service.ApiService;
import com.pmcc.mybsmodel.requestutil.service.UrlContract;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MainActivity extends RxAppCompatActivity implements Contract.ViewIntfc {

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_normal_request).setOnClickListener(v -> normalRequest());
        findViewById(R.id.main_upload_request).setOnClickListener(v -> uploadRequest());
        findViewById(R.id.main_down_request).setOnClickListener(v -> downRequest());
        findViewById(R.id.main_next).setOnClickListener(v -> startActivity(new Intent(this, MainActivity2.class)));
        initData();
    }

    private void initData() {
        presenter = new Presenter(this);
    }

    /**
     * 普通网络请求
     */
    private void normalRequest() {
        presenter.getDatas(UrlContract.getNewVersion, new HashMap<>(), false, this);
    }

    /**
     * 上传文件
     */
    private void uploadRequest() {
        HashMap<String, Object> paraps = new HashMap<>();
        paraps.put("shopId", "ff80808167148121016717a22dec0094");
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("/storage/emulated/0/DCIM/Screenshots/Screenshot_2019-05-28-10-20-29-026_com.zpjr.cunguan.png"));
        presenter.postFiles(UrlContract.uploadHead, paraps, fileList, true, this);

    }


    /**
     * 下载文件
     */
    private void downRequest() {
        String url = "http://imtt.dd.qq.com/16891/FC92B1B4471DE5AAD0D009DF9BF1AD01.apk?fsname=com.tencent.mobileqq_7.7.5_896.apk&csr=1bbd";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "QQ.apk");
        presenter.downFile(url, file, true, this);
    }


    @Override
    public void getDataSuccess(String url, String data) {

    }

    @Override
    public void getDataFail(String url,String code, String errorMessage) {

    }
}
