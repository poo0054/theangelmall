package com.themall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.common.utils.PageUtils;
import com.themall.common.utils.Query;
import com.themall.member.dao.MemberDao;
import com.themall.member.entity.MemberEntity;
import com.themall.member.entity.MemberLevelEntity;
import com.themall.member.exception.MemberExection;
import com.themall.member.service.MemberLevelService;
import com.themall.member.service.MemberService;
import com.themall.member.vo.LoginUserVo;
import com.themall.member.vo.MemberRegistVo;
import com.themall.model.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

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
    public MemberEntity login(LoginUserVo loginUserVo) throws RRException {
        String loginName = loginUserVo.getLoginName();
        MemberEntity username = this.getOne(new QueryWrapper<MemberEntity>().eq("username", loginName).or().eq("mobile", loginName));
        if (ObjectUtils.isEmpty(username)) {
            throw new RRException("用户账号或密码错误");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        boolean b = bCryptPasswordEncoder.matches(loginUserVo.getPassWord(), username.getPassword());

        if (b) {
            return username;
        } else {
            throw new RRException("用户账号或密码错误");
        }
    }


}