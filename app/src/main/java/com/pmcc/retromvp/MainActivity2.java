package com.pmcc.retromvp;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pmcc.mybsmodel.base.BaseActivity;
import com.pmcc.mybsmodel.mvp.Contract;
import com.pmcc.mybsmodel.mvp.Presenter;
import com.pmcc.mybsmodel.requestutil.manager.RequestManager;
import com.pmcc.mybsmodel.requestutil.service.UrlContract;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends BaseActivity<Presenter> implements Contract.ViewIntfc {

    @butterknife.BindView(R.id.main_normal_request)
    TextView mainNormalRequest;
    @butterknife.BindView(R.id.main_upload_request)
    TextView mainUploadRequest;
    @butterknife.BindView(R.id.main_down_request)
    TextView mainDownRequest;
    @butterknife.BindView(R.id.main_next)
    TextView mainNext;




    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    public boolean setNoScreenFit() {
        return false;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() throws Exception {
        Log.d("%s",getLocalClassName());
    }

    @butterknife.OnClick({R.id.main_normal_request, R.id.main_upload_request, R.id.main_down_request, R.id.main_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_normal_request:
                normalRequest();
                break;
            case R.id.main_upload_request:
                uploadRequest();
                break;
            case R.id.main_down_request:
                downRequest();
                break;
            case R.id.main_next:
                RequestManager.getInstance().cancelAll();
                break;
        }
    }

    /**
     * 普通网络请求
     */
    private void normalRequest() {
        mPresenter.getDatas(UrlContract.getNewVersion, new HashMap<>(), false, this);
    }

    /**
     * 上传文件
     */
    private void uploadRequest() {
        HashMap<String, Object> paraps = new HashMap<>();
        paraps.put("shopId", "ff80808167148121016717a22dec0094");
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("/storage/emulated/0/DCIM/Screenshots/Screenshot_2019-05-28-10-20-29-026_com.zpjr.cunguan.png"));
        mPresenter.postFiles(UrlContract.uploadHead, paraps, fileList, true, this);

    }


    /**
     * 下载文件
     */
    private void downRequest() {
        String url = "http://imtt.dd.qq.com/16891/FC92B1B4471DE5AAD0D009DF9BF1AD01.apk?fsname=com.tencent.mobileqq_7.7.5_896.apk&csr=1bbd";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "QQ.apk");
        mPresenter.downFile(url, file, true, this);
    }


    @Override
    public void getDataSuccess(String url, String data) {
        Log.d("%s", url+data);
    }

    @Override
    public void getDataFail(String url, String code, String errorMessage) {
        Log.d("%s", url+code+errorMessage);
    }


}
