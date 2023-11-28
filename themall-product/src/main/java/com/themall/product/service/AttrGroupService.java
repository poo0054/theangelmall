package com.themall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.common.utils.PageUtils;
import com.themall.product.entity.AttrGroupEntity;
import com.themall.product.vo.AttrGroupWithAttrsVo;
import com.themall.product.vo.SpuItemAttrGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long cateLogId);

    List<AttrGroupWithAttrsVo> AttrGroupWithAttrsVoByCateLogId(Long catelogId);

    /**
     * 根据spuid查询所有属性分组及属性信息
     *
     * @param
     * @param spuId
     * @return
     */
    List<SpuItemAttrGroupVo> AttrGroupWithAttrBySpuId(Long spuId);

}

