package io.renren.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.themall.model.entity.R;
import com.themall.model.entity.SysMenu;
import io.renren.pojo.vo.MenuVo;
import io.renren.service.SysMenuService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理(SysMenu)表控制层
 *
 * @author makejava
 * @since 2023-12-28 16:36:24
 */
@RestController
@RequestMapping("sys/menu")
public class SysMenuController extends AbstractController {

    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 分页查询所有数据
     *
     * @return 当前登陆人的所有数据
     */
    @GetMapping("getMenu")
    public R getMenu() {
        List<MenuVo> menuList = sysMenuService.getUserMenuList(getUserId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return R.ok().put("menuList", menuList).put("permissions", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
    }

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param sysMenu 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<SysMenu> page, SysMenu sysMenu) {
        return R.data(this.sysMenuService.page(page, new QueryWrapper<>(sysMenu)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return R.data(this.sysMenuService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sysMenu 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody SysMenu sysMenu) {
        return R.data(this.sysMenuService.save(sysMenu));
    }

    /**
     * 修改数据
     *
     * @param sysMenu 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody SysMenu sysMenu) {
        return R.data(this.sysMenuService.updateById(sysMenu));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return R.data(this.sysMenuService.removeByIds(idList));
    }
}

