package com.theangel.themall.ware.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.theangel.common.utils.R;
import com.theangel.themall.ware.openfeign.MemberFeignService;
import com.theangel.themall.ware.vo.FareVo;
import com.theangel.themall.ware.vo.MemberReceiveAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.ware.dao.WareInfoDao;
import com.theangel.themall.ware.entity.WareInfoEntity;
import com.theangel.themall.ware.service.WareInfoService;
import org.springframework.util.StringUtils;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {
    @Autowired
    MemberFeignService memberFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(item -> {
                item.eq("id", key).or().like("name", key).or().like("address", key);
            });
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public FareVo getFare(Long addrId) {
        R address = memberFeignService.getAddrInfo(addrId);
        if (address.getCode() == 0) {
            MemberReceiveAddressVo data = address.getData("memberReceiveAddress", new TypeReference<MemberReceiveAddressVo>() {
            });
            //TODO 調用第三方物流接口  查詢物流的價格返回
            //模拟使用手机号位数2位数作为物流价格
            FareVo fareVo = new FareVo();
            String phone = data.getPhone();
            String charSequence = phone.substring(phone.length() - 2, phone.length());
            fareVo.setFare(new BigDecimal(charSequence));
            fareVo.setMemberReceiveAddressVo(data);
            return fareVo;
        }
        return null;
    }


}