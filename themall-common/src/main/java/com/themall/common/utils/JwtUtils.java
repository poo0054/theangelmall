/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.themall.common.utils;

import com.themall.model.entity.SysUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * jwt工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Getter
@Slf4j
public class JwtUtils {

    public static final String REDIS_PREFIX = "login:name:";
    /**
     * 过期时间 7天
     */
    public static final long EXPIRE = 604800L;

    public static final String HEADER_NAME = "user";

    private static final String SECRET = "theangelmall1c55e80f3c8648d8a69de3811fe924b4wwwpoo0054top";

    /**
     * 生成jwt token
     */
    public static String generateToken(String subject, SysUser userName) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + EXPIRE * 1000);
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET));
        return Jwts.builder()
                .header().add(HEADER_NAME, userName)
                .and()
                .subject(subject)
                .expiration(expireDate)
                .issuedAt(nowDate)
                .signWith(key)
                .compact();
    }

    public static Claims getClaimByToken(String token) {
        try {
            Jws<Claims> claimsJws = getClaimsJws(token);
            return claimsJws.getPayload();
        } catch (Exception e) {
            log.warn("validate is token error ", e);
            return null;
        }
    }


    public static JwsHeader getJwsHeaderByToken(String token) {
        try {
            return getClaimsJws(token)
                    .getHeader();
        } catch (Exception e) {
            log.warn("validate is token error ", e);
            return null;
        }
    }

    public static Jws<Claims> getClaimsJws(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET));
            return Jwts.parser().verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("token校验失败", e);
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }


}
