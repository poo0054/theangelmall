/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.pojo.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 密码表单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class PasswordForm {
    /**
     * 原密码
     */
    @NotBlank
    private String password;

    /**
     * 新密码
     */
    @NotBlank
    private String newPassword;

}
