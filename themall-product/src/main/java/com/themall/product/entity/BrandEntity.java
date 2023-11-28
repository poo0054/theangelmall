package com.themall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.themall.common.valid.ListVlaue;
import com.themall.common.valid.addGro;
import com.themall.common.valid.updateGro;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 品牌
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId
    @NotNull(message = "品牌id不能为空", groups = {updateGro.class})
    private Long brandId;
    /**
     * 品牌名
     */
    @NotEmpty(message = "品牌名不能为空", groups = {addGro.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @NotEmpty(message = "品牌logo地址不能为空", groups = {addGro.class})
    @URL(message = "品牌logo地址必须是一个合法的url", groups = {addGro.class, updateGro.class})
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @ListVlaue(value = {0, 1}, groups = {addGro.class, updateGro.class})
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是一个字母", groups = {addGro.class, updateGro.class})
    private String firstLetter;
    /**
     * 排序
     */
    @Min(value = 0, message = "排序必须是一个大于0的数", groups = {addGro.class, updateGro.class})
    private Integer sort;

}
