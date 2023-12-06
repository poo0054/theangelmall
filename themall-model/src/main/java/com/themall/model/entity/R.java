/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.model.entity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.themall.model.constants.HttpStatusEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String CODE_NAME = "code";
    public static final String MSG_NAME = "msg";
    public static final String DATA_NAME = "data";


    public R() {
        HttpStatusEnum success = HttpStatusEnum.SUCCESS;
        put(CODE_NAME, success.getCode());
        put(MSG_NAME, success.getMsg());
    }

    public static R error() {
        return httpStatus(HttpStatusEnum.SYSTEM_ERROR_B0001);
    }

    public static R error(HttpStatusEnum httpStatusEnum) {
        return error(httpStatusEnum.getCode(), httpStatusEnum.getMsg());
    }

    public static R error(String code, String msg) {
        R r = new R();
        r.put(CODE_NAME, code);
        r.put(MSG_NAME, msg);
        return r;
    }

    public static R httpStatus(String msg) {
        R r = new R();
        r.put(MSG_NAME, msg);
        return r;
    }

    public static R ok(String code, String msg) {
        R r = new R();
        r.put(MSG_NAME, msg);
        r.put(CODE_NAME, code);
        return r;
    }

    public static R httpStatus(HttpStatusEnum httpStatusEnum) {
        R r = new R();
        r.put(MSG_NAME, httpStatusEnum.getCode());
        r.put(CODE_NAME, httpStatusEnum.getMsg());
        return r;
    }

    public static R httpStatus(HttpStatusEnum httpStatusEnum, Object data) {
        R r = new R();
        r.put(MSG_NAME, httpStatusEnum.getCode());
        r.put(CODE_NAME, httpStatusEnum.getMsg());
        r.put(DATA_NAME, data);
        return r;
    }

    public static R httpStatus(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R httpStatus() {
        return new R();
    }

    /**
     * 是否成功
     *
     * @return 结果
     */
    public boolean isSuccess() {
        return Objects.equals(this.getCode(), HttpStatusEnum.SUCCESS.getCode());
    }

    public String getCode() {
        Object code = this.get(CODE_NAME);
        return (String) code;
    }

    public String getMsg() {
        Object msg = this.get(MSG_NAME);
        return (String) msg;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Object getData() {
        return this.get(DATA_NAME);
    }

    public R setData(Object data) {
        put(DATA_NAME, data);
        return this;
    }

    public <T> T getData(TypeReference<T> tTypeReference) {
        String data = JSON.toJSONString(this.get(DATA_NAME));
        return JSON.parseObject(data, tTypeReference);
    }

    public <T> T getData(String name, TypeReference<T> tTypeReference) {
        Object data = this.get(name);
        return JSON.parseObject(JSON.toJSONString(data), tTypeReference);
    }


}
