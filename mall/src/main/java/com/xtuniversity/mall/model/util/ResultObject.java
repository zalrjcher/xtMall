package com.xtuniversity.mall.model.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zlj
 * @introduce
 */
public class ResultObject<T extends Object> {
    //返回码
    private int code ;
    /**
     * 返回的信息
     */
    private String message;
    /**
     * 返回的数据
     */
    private T data ;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private ResultObject() {
    }

    public static <T extends Object> ResultObject<T> SUCCESS(T data ){
        ResultObject<T> resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setData(data);
        resultObject.setMessage("");
        return resultObject ;
    }
    public static <T extends Object> ResultObject<T> ERROR(int code , String message) {
        ResultObject<T> resultObject = new ResultObject<>();
        resultObject.setCode(code);
        resultObject.setMessage(message);
        return  resultObject ;
    }
    public String toJsonString(){
        String responseJson = JSONObject.toJSONString(this);
        return responseJson;
    }
}
