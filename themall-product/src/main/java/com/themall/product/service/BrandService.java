package com.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.utils.PageUtils;
import com.themall.product.pojo.entity.BrandEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);

    List<BrandEntity> getBrandsById(List<Long> brandIds);
}

