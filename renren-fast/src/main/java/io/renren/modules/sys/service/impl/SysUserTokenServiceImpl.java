/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.common.utils.JwtUtils;
import com.themall.model.entity.R;
import com.themall.model.entity.SysUserEntity;
import io.renren.modules.sys.dao.SysUserTokenDao;
import io.renren.modules.sys.entity.SysUserTokenEntity;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    /**
     * 12小时后过期
     */
    private final static int EXPIRE = 3600 * 12;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public R createToken(String userName, Authentication authenticate) {
        SysUserEntity byUserName = sysUserService.getByUserName(userName);
        byUserName.setPassword(null);
        String subject = UUID.randomUUID().toString();
        String token = JwtUtils.generateToken(subject, byUserName);
        redisTemplate.opsForValue().set(JwtUtils.REDIS_PREFIX + subject, authenticate, EXPIRE, TimeUnit.SECONDS);
        return R.ok().put("token", token).put("expire", EXPIRE);
    }

    @Override
    public void logout(String userId) {
        redisTemplate.delete(JwtUtils.REDIS_PREFIX + userId);
    }
}
