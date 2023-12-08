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
import java.util.Date;

/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Id
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private Date createTime;


}
