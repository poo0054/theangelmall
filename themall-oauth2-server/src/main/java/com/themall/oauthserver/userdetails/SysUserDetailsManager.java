package com.themall.oauthserver.userdetails;

import com.themall.model.constants.Constant;
import com.themall.oauthserver.entity.SysRole;
import com.themall.oauthserver.entity.SysUser;
import com.themall.oauthserver.entity.SysUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author poo0054
 */
@Component
public class SysUserDetailsManager implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserRepository.getByUsername(username);
        if (null == sysUser) {
            return null;
        }
        User.UserBuilder builder = User.builder();
        builder.username(sysUser.getUsername());
        builder.password(sysUser.getPassword());
        //先给空权限
        builder.authorities(AuthorityUtils.NO_AUTHORITIES);
        Set<String> auth = new HashSet<>();
        if (Objects.equals(Constant.SUPER_ADMIN, sysUser.getUserId())) {
            Iterable<SysRole> roleServiceAll = this.sysRoleRepository.findAll();
            for (SysRole sysRole : roleServiceAll) {
                auth.add(sysRole.getRoleName());
            }
        } else {
            //找出具体角色
            List<SysUserRole> byUserId = sysUserRoleRepository.getByUserId(sysUser.getUserId());
            if (null != byUserId) {
                List<SysRole> byRoleIdIsIn = sysRoleRepository.getByRoleIdIsIn(
                        byUserId.stream()
                                .map(SysUserRole::getRoleId)
                                .collect(Collectors.toList()));
                if (null != byRoleIdIsIn) {
                    for (SysRole sysRole : byRoleIdIsIn) {
                        auth.add(sysRole.getRoleName());
                    }
                }
            }
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String s : auth) {
            authorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList(s));
        }
        builder.authorities(authorities);
        return builder.build();
    }
}
