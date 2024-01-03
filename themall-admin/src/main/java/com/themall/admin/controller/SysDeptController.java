package com.themall.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.themall.admin.pojo.entity.SysDept;
import com.themall.admin.service.SysDeptService;
import com.themall.model.constants.Page;
import com.themall.model.entity.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (SysDept)表控制层
 *
 * @author makejava
 * @since 2024-01-03 17:54:38
 */
@RestController
@RequestMapping("sys/dept")
public class SysDeptController extends AbstractController {

    /**
     * 服务对象
     */
    @Resource
    private SysDeptService sysDeptService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param sysDept 查询实体
     * @return 所有数据
     */
    @GetMapping
    @PreAuthorize("hasAuthority('sys:dept:list')")
    public R selectAll(Page<SysDept> page, SysDept sysDept) {
        return success(this.sysDeptService.page(page, new QueryWrapper<>(sysDept)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:dept:list')")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.sysDeptService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysDept 实体对象
     * @return 新增结果
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:dept:save')")
    public R insert(@RequestBody SysDept sysDept) {
        return success(this.sysDeptService.save(sysDept));
    }

    /**
     * 修改数据
     *
     * @param sysDept 实体对象
     * @return 修改结果
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:dept:update')")
    public R update(@RequestBody SysDept sysDept) {
        return success(this.sysDeptService.updateById(sysDept));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    public R delete(@RequestBody List<Long> idList) {
        return success(this.sysDeptService.removeByIds(idList));
    }
}

