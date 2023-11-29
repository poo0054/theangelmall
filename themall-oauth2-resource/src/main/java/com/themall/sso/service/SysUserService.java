package com.themall.sso.service;

import com.themall.sso.entity.SysUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author poo0054
 */
@Repository
public interface SysUserService extends CrudRepository<SysUser, Long> {

    /**
     * userName查询
     *
     * @param userName 用户名
     * @return 数据
     */
    SysUser getByUsername(String userName);
}
