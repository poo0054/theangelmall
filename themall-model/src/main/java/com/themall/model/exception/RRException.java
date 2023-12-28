/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.model.exception;

import com.themall.model.constants.HttpStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author Mark sunlightcs@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RRException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;

    private int code = 500;

    public RRException(HttpStatusEnum httpStatusEnum) {
        super(httpStatusEnum.getCode());
        this.msg = httpStatusEnum.getMsg();
    }

    public RRException(HttpStatusEnum httpStatusEnum, Throwable e) {
        super(httpStatusEnum.getCode(), e);
        this.msg = httpStatusEnum.getMsg();
    }

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RRException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RRException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }


}
