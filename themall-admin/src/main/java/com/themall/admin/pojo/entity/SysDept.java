package com.themall.admin.pojo.entity;

import com.themall.model.entity.AbstractEntity;
import lombok.Data;

import java.util.Date;

/**
 * (SysDept)表实体类
 *
 * @author makejava
 * @since 2024-01-03 17:54:38
 */
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
    //逻辑删除状态 已删除值(默认为 1) 未删除值(默认为 0)
    private Integer flag;
    //创建者ID
    private String createBy;
    //创建时间
    private Date createTime;
    //修改人
    private String updateBy;
    //修改时间
    private Date updateDate;


}

