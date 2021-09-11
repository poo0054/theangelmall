package com.theangel.themall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theangel.common.utils.PageUtils;
import com.theangel.themall.ware.entity.WareInfoEntity;
import com.theangel.themall.ware.vo.FareVo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 仓库信息
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-10 20:02:13
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据用户收费地址计算运费
     * @param addrId
     * @return
     */
    FareVo getFare(Long addrId);
}

