package com.themall.model.entity;

import com.themall.model.enums.MenuTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单管理(SysMenu)表实体类
 *
 * @author makejava
 * @since 2023-12-28 16:36:28
 */

@Data
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 8584431895726211493L;

    private Long id;

    //父菜单ID
    private Long parentId;

    //菜单URL
    private String path;

    private String name;
    //视图
    private String component;
    //菜单名称
    private String title;
    //菜单高亮
    private String active;
    //类型  menu iframe link button
    private MenuTypeEnum type;
    //菜单图标
    private String icon;
    //标签
    private String tag;
    //整页路由
    private Integer fullpage;
    //隐藏菜单
    private Integer hidden;
    //隐藏面包屑
    private Integer hiddenbreadcrumb;
    //颜色
    private String color;
    //授权(多个用逗号分隔，如：user:list,user:create)
    private String perms;
    //排序
    private Integer orderNum;


}

