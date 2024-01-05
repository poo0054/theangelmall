package com.themall.admin.pojo.entity;

import com.themall.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (SysDept)表实体类
 *
 * @author makejava
 * @since 2024-01-03 17:54:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDept extends AbstractEntity<SysDept> {

    private Long id;
    //上级
    private Long parentId;
    //部门名称
    private String label;
    //排序
    private Integer sort;
    //状态
    private Integer status;
    //备注
    private String remark;


}

