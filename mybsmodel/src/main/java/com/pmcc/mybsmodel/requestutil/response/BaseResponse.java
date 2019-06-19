package com.pmcc.mybsmodel.requestutil.response;

import lombok.Data;

/**
 * 后台统一返回值，根据自己的项目修改
 * @param <T>
 */
@Data
public class BaseResponse<T> {
    private int resultCode;
    private String resultDesc;
    private T object;
}
