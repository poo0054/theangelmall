/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.pojo.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;


}
