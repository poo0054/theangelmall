package com.theangel.common.exception;


/**
 * 5位数
 * 前俩为为业务场景
 * 10:通用：
 * 001:参数效验
 * 002：短信验证码频率高
 * 11：商品
 * 12：订单
 * 13：购物车
 * 14：物流
 * 15:用户
 * <p>
 * 21:库存
 */

public enum BizCodeEnum {
    /**
     *
     */
    UNKNOW_EXCEPTION(10000, "系统未知错误"),
    VAILD_EXCEPTION(10001, "参数格式错误"),
    SMS_CODE_EXCEPTION(10002, "验证码获取频率过高"),
    TO_MANY_REQUEST(10003, "请求流量过大"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常"),
    USER_EXCEPTION(15001, "用户注册异常（异常信息从message中获取）"),
    USER_PASSWORD_EXCEPTION(15002, "用户账号或密码错误"),
    NO_STOCK_EXCEPTION(21000, "商品库存不足");


    private int code;
    private String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
