package io.renren.filter;

import com.themall.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.web.authentication.www.BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC;

/**
 * @author poo0054
 */


@Slf4j
@Component
public class JWTBasicAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public JWTBasicAuthenticationFilter(@Lazy AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String header = request.getHeader(AUTHORIZATION);
            if (null != header) {
                header = header.trim();
                if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
                    String token = header.substring(6);
                    Jws<Claims> claimsJws = JwtUtils.getClaimsJws(token);
                    if (null != claimsJws) {
                        String subject = claimsJws.getPayload().getSubject();
                        redisTemplate.expire(JwtUtils.REDIS_PREFIX + subject, JwtUtils.EXPIRE, TimeUnit.SECONDS);
                        Authentication authenticate = (Authentication) redisTemplate.opsForValue().get(JwtUtils.REDIS_PREFIX + subject);
                        SecurityContextHolder.getContext().setAuthentication(authenticate);
                        JwsHeader jwsHeader = claimsJws.getHeader();
                        request.setAttribute(JWTBasicAuthenticationFilter.class.getSimpleName() + "subject", subject);
                        request.setAttribute(JWTBasicAuthenticationFilter.class.getSimpleName() + JwtUtils.HEADER_NAME, jwsHeader.get(JwtUtils.HEADER_NAME));
                    }
                }
            }
            chain.doFilter(request, response);
        } finally {
            request.removeAttribute(JWTBasicAuthenticationFilter.class.getSimpleName());
            request.removeAttribute(JWTBasicAuthenticationFilter.class.getSimpleName() + JwtUtils.HEADER_NAME);
            SecurityContextHolder.clearContext();
        }
    }
}
