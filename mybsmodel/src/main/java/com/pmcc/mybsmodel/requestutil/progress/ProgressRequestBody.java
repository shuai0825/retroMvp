package com.pmcc.mybsmodel.requestutil.progress;

import com.pmcc.mybsmodel.requestutil.observer.BaseObserver;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 监听文件上传进度
 */
public class ProgressRequestBody extends RequestBody {
    //实际的待包装请求体
    private RequestBody mRequestBody;
    //进度回调接口
    private BaseObserver progressObserver;
    //包装完成的BufferedSink
    private BufferedSink mSink;

    public ProgressRequestBody(RequestBody body, BaseObserver progressObserver) {
        this.mRequestBody = body;
        this.progressObserver = progressObserver;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        try {
            return mRequestBody.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void writeTo(BufferedSink s) throws IOException {
        if (mSink == null) {
            mSink = Okio.buffer(CountingSink(s));
        }
        //写入
        mRequestBody.writeTo(mSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        mSink.flush();
    }


    private Sink CountingSink(Sink sink) {
        ForwardingSink s = new ForwardingSink(sink) {
            //当前写入字节数
            long currentSize = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long totalSize = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (totalSize == 0) {
                    //获得contentLength的值，后续不再调用
                    totalSize = contentLength();
                }
                //增加当前写入的字节数
                currentSize += byteCount;
                //当前上传的百分比进度
                int progress = (int) (currentSize * 100 / totalSize);
                if (progressObserver != null) {
                    progressObserver.onProgressChanges(currentSize,
                            totalSize, progress);
                }
            }
        };
        return s;
    }

}
