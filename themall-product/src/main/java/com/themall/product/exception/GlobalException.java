package com.themall.product.exception;

import com.themall.model.constants.HttpStatusEnum;
import com.themall.model.entity.R;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;

//@ControllerAdvice
//@ResponseBody
//合体为
@RestControllerAdvice(basePackages = "com.theangel.themall.product.controller")
public class GlobalException {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R validException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(k -> {
            map.put(k.getField(), k.getDefaultMessage());
        });
        return R.error(HttpStatusEnum.SERVICE_ERROR_C0134).put("data", map);
    }


    @ExceptionHandler(value = Exception.class)
    public R exception(Exception e) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        return R.error().put("data", map.put(e.getClass().getSimpleName(), e.getMessage()));
    }


}
