package com.themall.sso.service;

import com.themall.sso.entity.SysRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author poo0054
 */
@Repository
public interface SysRoleService extends CrudRepository<SysRole, Long> {

    List<SysRole> getByRoleIdIsIn(List<Long> roleIds);
}
