package com.theangel.themall.member.service.impl;

import com.alibaba.nacos.common.utils.Md5Utils;
import com.theangel.themall.member.dao.MemberLevelDao;
import com.theangel.themall.member.entity.MemberLevelEntity;
import com.theangel.themall.member.exception.MemberExection;
import com.theangel.themall.member.service.MemberLevelService;
import com.theangel.themall.member.vo.LoginUserVo;
import com.theangel.themall.member.vo.MemberRegistVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.member.dao.MemberDao;
import com.theangel.themall.member.entity.MemberEntity;
import com.theangel.themall.member.service.MemberService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import sun.security.util.Password;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {


    @Autowired
    MemberLevelService memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void regist(MemberRegistVo memberRegistVo) {
        MemberEntity memberEntity = new MemberEntity();
        MemberLevelEntity default_status = memberLevelDao.getOne(new QueryWrapper<MemberLevelEntity>().eq("default_status", 1));
        //设置默认等级
        memberEntity.setLevelId(default_status.getId());
        List<MemberEntity> username = this.list(new QueryWrapper<MemberEntity>().eq("username", memberRegistVo.getUserName()));
        if (!ObjectUtils.isEmpty(username)) {
            throw new MemberExection("用户名重复");
        }
        List<MemberEntity> list = this.list(new QueryWrapper<MemberEntity>().eq("mobile", memberRegistVo.getPhone()));
        if (!ObjectUtils.isEmpty(list)) {
            throw new MemberExection("手机号重复");
        }
        memberEntity.setUsername(memberRegistVo.getUserName());
        memberEntity.setMobile(memberRegistVo.getPhone());
        //spring封装的密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(memberRegistVo.getPassWord());
        //使用加密前的，和加密后的，自动进行匹配，返回一个boolean值
//        boolean matches = bCryptPasswordEncoder.matches(memberRegistVo.getPassWord(), encode);
        //密码加密
        memberEntity.setPassword(encode);

        //TODO  会员其余信息

        this.getBaseMapper().insert(memberEntity);
    }

    @Override
    public boolean login(LoginUserVo loginUserVo) {
        String loginName = loginUserVo.getLoginName();
        MemberEntity username = this.getOne(new QueryWrapper<MemberEntity>().eq("username", loginName).or().eq("mobile", loginName));
        if (ObjectUtils.isEmpty(username)) {
            return false;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        boolean matches = bCryptPasswordEncoder.matches(loginUserVo.getPassWord(), username.getPassword());

        return matches;
    }

}