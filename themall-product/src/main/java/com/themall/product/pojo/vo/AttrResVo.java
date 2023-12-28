package com.themall.product.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author poo0054
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AttrResVo extends AttrVo {
    /*  "catelogName":"手机/数码/手机", //所属分类名字
              "groupName":"主体", //所属分组名字*/
    private String catelogName;
    private String groupName;

    private Long[] catelogPath;
}
