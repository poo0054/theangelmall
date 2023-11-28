package com.themall.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.member.entity.MemberEntity;
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
