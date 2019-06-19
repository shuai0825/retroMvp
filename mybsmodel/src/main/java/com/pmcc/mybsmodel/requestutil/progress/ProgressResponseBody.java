package com.pmcc.mybsmodel.requestutil.progress;

import com.pmcc.mybsmodel.requestutil.observer.BaseObserver;

import java.io.IOException;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 监听文件下载进度
 */
public class ProgressResponseBody extends ResponseBody {
    private ResponseBody mResponseBody;
    private BufferedSource mBufferedSource;
    private BaseObserver progressObserver;

    public ProgressResponseBody(ResponseBody body, BaseObserver progressObserver) {
        this.mResponseBody = body;
        this.progressObserver = progressObserver;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }


    private Source source(Source source) {
        ForwardingSource so = new ForwardingSource(source) {
            long currentSize = 0L;
            long totalSize = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long nowSize = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                currentSize += nowSize != -1 ? nowSize : 0;
                if (totalSize == 0) {
                    //获得contentLength的值，后续不再调用
                    totalSize = contentLength();
                }
                //当前下载的百分比进度
                int progress = (int) (currentSize * 100 / totalSize);
                if (progressObserver != null && progress >= 0) {
                    progressObserver.onProgressChanges(
                            currentSize, totalSize,
                            progress);
                }
                return currentSize;
            }
        };
        return so;
    }

}
