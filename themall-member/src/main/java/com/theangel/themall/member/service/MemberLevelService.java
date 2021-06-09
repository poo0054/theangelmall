package com.theangel.themall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 21:47:50
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

