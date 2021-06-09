package com.theangel.themall.member.dao;

import com.theangel.themall.member.entity.MemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 21:47:50
 */
@Mapper
public interface MemberLoginLogDao extends BaseMapper<MemberLoginLogEntity> {
	
}
