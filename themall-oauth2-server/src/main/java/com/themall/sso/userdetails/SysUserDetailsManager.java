package com.themall.sso.userdetails;

import com.themall.sso.entity.SysRole;
import com.themall.sso.entity.SysUser;
import com.themall.sso.entity.SysUserRole;
import com.themall.sso.service.SysRoleService;
import com.themall.sso.service.SysUserRoleService;
import com.themall.sso.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author poo0054
 */
public class SysUserDetailsManager implements UserDetailsService {

    @Autowired
    SysUserService userService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getByUsername(username);
        if (null == sysUser) {
            return null;
        }
        User.UserBuilder builder = User.builder();
        builder.username(sysUser.getUsername());
        builder.password(sysUser.getPassword());

        List<SysUserRole> byUserId = sysUserRoleService.getByUserId(sysUser.getUserId());
        if (null != byUserId) {
            List<SysRole> byRoleIdIsIn = sysRoleService.getByRoleIdIsIn(
                byUserId.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
            if (null != byRoleIdIsIn) {
                String[] array = byRoleIdIsIn.stream().map(SysRole::getRoleName).toArray(String[]::new);
                builder.authorities(array);
            }
        }
        return builder.build();
    }
}
