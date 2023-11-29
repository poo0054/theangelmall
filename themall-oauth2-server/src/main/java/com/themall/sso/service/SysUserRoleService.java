package com.themall.sso.service;

import com.themall.sso.entity.SysUserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author poo0054
 */
@Repository
public interface SysUserRoleService extends CrudRepository<SysUserRole, Long> {

    List<SysUserRole> getByUserIdIsIn(List<Long> userIds);

    List<SysUserRole> getByUserId(Long userId);
}
