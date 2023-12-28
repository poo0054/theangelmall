package com.themall.product.controller;

import com.themall.common.utils.PageUtils;
import com.themall.model.entity.R;
import com.themall.product.pojo.entity.AttrAttrgroupRelationEntity;
import com.themall.product.pojo.entity.AttrEntity;
import com.themall.product.pojo.entity.AttrGroupEntity;
import com.themall.product.pojo.vo.AttrGroupRelationVo;
import com.themall.product.pojo.vo.AttrGroupWithAttrsVo;
import com.themall.product.service.AttrAttrgroupRelationService;
import com.themall.product.service.AttrGroupService;
import com.themall.product.service.AttrService;
import com.themall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 属性分组
 *
 * @author theangel
 * @email poo0054@outlook.com
 * @date 2021-06-09 20:47:30
 */
@Slf4j
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    private AttrGroupService attrGroupService;
    private CategoryService categoryService;
    private AttrService attrService;
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    public void setAttrGroupService(AttrGroupService attrGroupService) {
        this.attrGroupService = attrGroupService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setAttrService(AttrService attrService) {
        this.attrService = attrService;
    }

    @Autowired
    public void setAttrAttrgroupRelationService(AttrAttrgroupRelationService attrAttrgroupRelationService) {
        this.attrAttrgroupRelationService = attrAttrgroupRelationService;
    }

    /**
     * /product/attrgroup/{catelogId}/withattr
     * 获取分类下所有分组&关联属性
     *
     * @param catelogId
     * @return
     */
    @GetMapping("{catelogId}/withattr")
    public R AttrGroupWithAttrsVo(@PathVariable("catelogId") Long catelogId) {

        List<AttrGroupWithAttrsVo> attrGroupWithAttrsVos = attrGroupService.AttrGroupWithAttrsVoByCateLogId(catelogId);
        return new R().put("data", attrGroupWithAttrsVos);
    }


    /**
     * /product/attrgroup/attr/relation
     * POST 添加属性与分组关联关系
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrAttrgroupRelationEntity> attrGroupRelationVo) {
        attrAttrgroupRelationService.saveBatch(attrGroupRelationVo);
        return new R();
    }

    /**
     * 获取属性分组的关联的所有属性
     * /product/attrgroup/{attrgroupId}/noattr/relation
     *
     * @param attrgroupId
     * @return
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> attrEntities = attrService.attrRelation(attrgroupId);
        return new R().put("data", attrEntities);
    }

    /**
     * 获取属性分组里面还没有关联的本分类里面的其他基本属性，方便添加新的关联
     * GET /product/attrgroup/{attrgroupId}/noattr/relation
     *
     * @param attrgroupId
     * @param params
     * @return
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R noAttrRelation(@PathVariable("attrgroupId") Long attrgroupId, @RequestParam Map<String, Object> params) {
        PageUtils pageUtils = attrService.noAttrRelation(params, attrgroupId);
        return new R().put("page", pageUtils);
    }

    /**
     * 删除属性与分组的关联关系
     * /product/attrgroup/attr/relation/delete
     *
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody List<AttrGroupRelationVo> attrGroupRelationVo) {
        attrService.deleteRelation(attrGroupRelationVo);
        return new R();
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list/{cateLogId}", method = RequestMethod.GET)
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("cateLogId") Long cateLogId) {
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, cateLogId);
        return R.httpStatus().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        attrGroup.setCatelogPath(categoryService.getCateLogPath(attrGroup.getCatelogId()));
        return R.httpStatus().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.httpStatus();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.httpStatus();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.httpStatus();
    }

}
