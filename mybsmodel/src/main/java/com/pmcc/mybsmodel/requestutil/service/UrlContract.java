package com.pmcc.mybsmodel.requestutil.service;


/**
 * ip请求接口合集
 */
public class UrlContract {

    public static final String base = "http://wx.pmcc.com.cn:";//ip
    public static final String ip = base + "8893";//端口号
    public static final String key = ip + "/app/";
    public static final String login = key + "member/login";//登录
    public static final String getNewVersion = key + "appVersion/getNewVersion";//版本更新
    public static final String uploadHead = key + "shopInfo/uploadImage";//上传头像
    public static final String uploadgoods = key + "appGoodsManagement/uploadGoods";//上传商品封面图


}
