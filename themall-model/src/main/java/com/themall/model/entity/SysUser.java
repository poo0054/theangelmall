/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.themall.model.validator.group.AddGroup;
import com.themall.model.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    @NotNull(groups = {UpdateGroup.class})
    private Long userId;

    /**
     * 第三方登陆id
     */
    private String oauthId;

    /**
     * 用户名
     */
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class})
    private String userName;

    /**
     * 第三方用户名
     */
    private String oauthName;

    /**
     * 密码
     */
    @NotBlank(groups = AddGroup.class)
    private String password;


    /**
     * 邮箱
     */
    @Email(groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态  0：正常  1：禁用
     */
    private Integer status;

    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    private List<Long> roleIdList;

}
