package com.theangel.themall.product.dao;

import com.theangel.themall.product.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * 
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {
	
}
