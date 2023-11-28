package com.themall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.themall.common.utils.PageUtils;
import com.themall.common.utils.Query;
import com.themall.product.dao.AttrGroupDao;
import com.themall.product.entity.AttrEntity;
import com.themall.product.entity.AttrGroupEntity;
import com.themall.product.service.AttrGroupService;
import com.themall.product.service.AttrService;
import com.themall.product.vo.AttrGroupWithAttrsVo;
import com.themall.product.vo.SpuItemAttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrService attrService;
    @Autowired
    private AttrGroupService attrGroupService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long cateLogId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(key)) {
            wrapper.and((obj) -> {
                obj.like("attr_group_id", key).or().like("attr_group_name", key).or().like("descript", key);
            });
        }

        if (cateLogId == 0) {
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        } else {
            wrapper.eq("catelog_id", cateLogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }
    }

    /**
     * /product/attrgroup/{catelogId}/withattr
     * 获取分类下所有分组&关联属性
     *
     * @param catelogId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrsVo> AttrGroupWithAttrsVoByCateLogId(Long catelogId) {
        //查询当前分类下所有属性分组
        List<AttrGroupEntity> cateLogList = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        //查询属性分组的所有属性
        List<AttrGroupWithAttrsVo> collect = cateLogList.stream().map(attrGroupEntity -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(attrGroupEntity, attrGroupWithAttrsVo);
            List<AttrEntity> attrEntities = attrService.attrRelation(attrGroupEntity.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(attrEntities);
            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 根据spuid查询所有属性分组及属性信息
     *
     * @param
     * @param spuId
     * @return
     */
    @Override
    public List<SpuItemAttrGroupVo> AttrGroupWithAttrBySpuId(Long spuId) {
        //spu 所有属性分组 分组下所有属性信息
        List<SpuItemAttrGroupVo> spuItemAttrGroupVos = this.baseMapper.attrGroupWithAttrBySpuId(spuId);
        return spuItemAttrGroupVos;
    }
}