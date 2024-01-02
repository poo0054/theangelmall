/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.model.exception;

import com.themall.model.enums.HttpStatusEnum;
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

    private String message;

    private String code = HttpStatusEnum.SYSTEM_ERROR_B0001.getCode();

    public RRException(HttpStatusEnum httpStatusEnum) {
        super(httpStatusEnum.getMessage());
        this.code = httpStatusEnum.getCode();
        this.message = httpStatusEnum.getMessage();
    }

    public RRException(HttpStatusEnum httpStatusEnum, Throwable e) {
        super(httpStatusEnum.getMessage(), e);
        this.code = httpStatusEnum.getCode();
        this.message = httpStatusEnum.getMessage();
    }

    public RRException(HttpStatusEnum httpStatusEnum, String message) {
        super(message);
        this.code = httpStatusEnum.getCode();
        this.message = message;
    }

    public RRException(String message) {
        super(message);
        this.message = message;
    }


}
