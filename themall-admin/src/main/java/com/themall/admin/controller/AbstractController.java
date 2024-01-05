/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.admin.controller;

import com.themall.common.utils.UserUtils;
import com.themall.model.constants.Page;
import com.themall.model.entity.R;
import com.themall.model.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Controller公共组件
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
public abstract class AbstractController {

    protected SysUser getUser() {
        return UserUtils.getUser();
    }

    protected Long getUserId() {
        SysUser user = getUser();
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        return user.getUserId();
    }


    public <T> R success(Page<T> page) {
        return R.data(page);
    }

    public R success(Object object) {
        return R.data(object);
    }
}
