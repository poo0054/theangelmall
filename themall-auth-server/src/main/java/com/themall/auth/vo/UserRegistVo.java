package com.themall.auth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 注册的vo
 */
@Data
public class UserRegistVo {
    @NotEmpty(message = "用户名必须提交")
    @Length(max = 18, min = 6, message = "账号必须为6-18位字符")
    private String userName;
    @NotEmpty(message = "密码不能为空")
    @Length(max = 18, min = 6, message = "密码必须为6-18位字符")
    private String passWord;
    @NotEmpty(message = "手机不能为空")
    @Pattern(regexp = "^[1][0-9]{10}$", message = "手机格式不正确")
    private String phone;
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
