package com.theangel.themall.member.dao;

import com.theangel.themall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 21:47:49
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
