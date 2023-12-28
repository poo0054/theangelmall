package com.themall.product.pojo.vo;

import com.themall.product.pojo.entity.AttrEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttrGroupWithAttrsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;
    /**
     * 属性分组下所有属性信息
     */
    private List<AttrEntity> attrs;
}
