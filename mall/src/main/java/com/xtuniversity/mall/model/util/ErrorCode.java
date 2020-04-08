package com.xtuniversity.mall.model.util;

/**
 * @author zlj
 * @introduce
 */
public enum ErrorCode {
    notRegister(-100,"未注册"),
    paramError(-200, "参数错误"),
    passwordError(-300,"密码错误"),
    notLogin(-400,"用户未登录"),
    numberError(-500,"商品数量不足"),
    moneyError(-600,"用户余额不足"),
    tokenError(-700,"token失效请重新登录"),
    telError(-800,"当前手机号码已经注册过"),
    nickError(-900,"昵称重复"),
    AddressError(-1000,"收货地址超过四个"),
    goodNumError(-1100,"购买数量超过最大库存");;

    private int code ;
    private String value ;

    public int code() {
        return code;
    }

    public String value() {
        return value;
    }

    ErrorCode(int code , String value){
        this.code =code;
        this.value =value;
    }
}
