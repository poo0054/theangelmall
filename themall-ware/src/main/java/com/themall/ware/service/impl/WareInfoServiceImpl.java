package com.themall.ware.service.impl;

import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.common.utils.PageUtils;
import com.themall.common.utils.Query;
import com.themall.model.entity.R;
import com.themall.ware.dao.WareInfoDao;
import com.themall.ware.entity.WareInfoEntity;
import com.themall.ware.openfeign.MemberFeignService;
import com.themall.ware.service.WareInfoService;
import com.themall.ware.vo.FareVo;
import com.themall.ware.vo.MemberReceiveAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author poo0054
 */
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
        if (address.isSuccess()) {
            MemberReceiveAddressVo data = address.getData("memberReceiveAddress", new TypeReference<MemberReceiveAddressVo>() {
            });
            //TODO 調用第三方物流接口  查詢物流的價格返回
            //模拟使用手机号位数2位数作为物流价格
            FareVo fareVo = new FareVo();
            String phone = data.getPhone();
            String charSequence = phone.substring(phone.length() - 2);
            fareVo.setFare(new BigDecimal(charSequence));
            fareVo.setMemberReceiveAddressVo(data);
            return fareVo;
        }
        return null;
    }


}