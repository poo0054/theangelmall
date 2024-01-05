/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.themall.model.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRole extends AbstractEntity<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId
    @NotNull(groups = UpdateGroup.class)
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 备注
     */
    private String remark;


    @TableField(exist = false)
    private List<Long> menuIdList;


}
