package com.themall.sso.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户与角色对应关系(SysUserRole)实体类
 *
 * @author makejava
 * @since 2023-11-29 15:26:07
 */
@Data
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 785923424990046309L;

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

