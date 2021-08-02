package com.theangel.common.exception;

import lombok.Data;

public enum BizCodeEnum {
    UNKNOW_EXCEPTION(10000, "系统未知错误"),
    VAILD_EXCEPTION(10001, "参数格式错误"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常");

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
