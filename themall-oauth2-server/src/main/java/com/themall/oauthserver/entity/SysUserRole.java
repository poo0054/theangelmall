/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.oauthserver.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 用户与角色对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;


}
