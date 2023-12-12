package com.themall.model.constants;

import lombok.Getter;

/**
 * @author poo0054
 */
@Getter
public enum AuthoritiesEnum {

    /**
     * 所有权限
     */
    ALL("all"),
    /**
     * 用户读取
     */
    USER_READ("user:read"),

    /**
     * 用户写入
     */
    USER_WRITE("user:write"),

    /**
     * 角色 读取
     */
    ROLE_READ("role:read"),

    /**
     * 角色 写入
     */
    ROLE_WRITE("role:write"),

    /**
     * 菜单 读取
     */
    MENU_READ("menu:read"),

    /**
     * 菜单 写入 (不能对外)
     */
    MENU_WRITE("menu:write");


    private final String type;

    AuthoritiesEnum(String type) {
        this.type = type;
    }
}
