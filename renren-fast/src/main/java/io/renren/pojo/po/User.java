package io.renren.pojo.po;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author poo0054
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 角色ID列表
     */
    private List<SysRole> roleIdList;
}
