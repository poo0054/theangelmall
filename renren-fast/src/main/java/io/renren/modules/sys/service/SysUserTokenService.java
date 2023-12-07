/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.model.entity.R;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import org.springframework.security.core.Authentication;

/**
 * 用户Token
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

    /**
     * 生成token
     *
     * @param userId       用户ID
     * @param authenticate
     */
    R createToken(String userId, Authentication authenticate);

    /**
     * 退出，修改token值
     *
     * @param userId 用户ID
     */
    void logout(String userId);

}
