package com.themall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.utils.PageUtils;
import com.themall.member.entity.MemberEntity;
import com.themall.member.vo.LoginUserVo;
import com.themall.member.vo.MemberRegistVo;

import java.util.Map;

/**
 * 会员
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 21:47:49
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 注册
     * @param memberRegistVo
     */
    void regist(MemberRegistVo memberRegistVo);

    MemberEntity login(LoginUserVo loginUserVo);
}

