package com.themall.auth.vo;

import lombok.Data;

/**
 * 登录的vo
 */
@Data
public class LoginUserVo {
    private String loginName;
    private String passWord;
}
