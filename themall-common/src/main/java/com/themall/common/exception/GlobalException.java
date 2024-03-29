/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.common.exception;

import com.themall.model.entity.R;
import com.themall.model.enums.HttpStatusEnum;
import com.themall.model.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        log.error(e.getMessage(), e);
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return R.error("404", "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatusEnum.SERVICE_ERROR_C0321);
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public R handleException(BadCredentialsException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatusEnum.USER_ERROR_A0220);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handleException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return R.error(HttpStatusEnum.USER_ERROR_A0303);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R validException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        BindingResult result = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(k -> {
            stringBuilder.append(k.getField())
                    .append(":")
                    .append(k.getDefaultMessage())
                    .append("\n")
            ;
        });
        return R.error(HttpStatusEnum.SERVICE_ERROR_C0134, stringBuilder);
    }

}
