package com.pmcc.mybsmodel.requestutil.service;

import com.pmcc.mybsmodel.requestutil.response.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 封装请求通用接口
 */
public interface ApiService {

    /**
     * GET 请求
     *
     * @param url       api接口url
     * @param parameter 请求参数map
     * @param header    请求头map
     * @return
     */
    @GET
    Observable<BaseResponse<String>> get(@Url String url, @QueryMap Map<String, Object> parameter, @HeaderMap Map<String, Object> header);


    /**
     * POST 请求
     *
     * @param url       api接口url
     * @param parameter 请求参数map
     * @param header    请求头map
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<BaseResponse<String>> post(@Url String url, @FieldMap Map<String, Object> parameter, @HeaderMap Map<String, Object> header);

    /**
     * 多文件上传，包含请求参数的
     *
     * @param url
     * @param map，请求参数
     * @param fileList（可以单个上传）
     * @return
     * @Multipart 文件上传注解 multipart/form-data
     */
    @Multipart
    @POST
    Observable<BaseResponse<String>> upload(@Url String url, @PartMap Map<String, RequestBody> map,@HeaderMap Map<String, Object> header, @Part List<MultipartBody.Part> fileList);

    /**
     * 多文件上传,另种方式跟上传方式upload一致
     *
     * @param url
     * @param map
     * @return
     */
    @Multipart
    @POST
    Observable<BaseResponse<String>> uploadBody(@Url String url, @PartMap Map<String, RequestBody> map);
    /**
     * 多文件上传,该方法尚未测试
     *
     * @param url       api接口url
     * @param parameter 请求接口参数
     * @param header    请求头map
     * @param fileList  文件列表,上传单个，表示当个上传
     * @return
     * @Multipart 文件上传注解 multipart/form-data
     */
    @Multipart
    @POST
    Observable<BaseResponse<String>> uploadTest(@Url String url, @PartMap Map<String, Object> parameter, @HeaderMap Map<String, Object> header, @Part List<MultipartBody.Part> fileList);


    /**
     * 断点续传下载
     *
     * @param range  断点下载范围 bytes= start - end
     * @param url    下载地址
     * @param header 请求头map
     * @return
     * @Streaming 防止内容写入内存, 大文件通过此注解避免OOM
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Header("RANGE") String range, @Url String url, @HeaderMap Map<String, Object> header);
}
