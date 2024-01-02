package io.renren.controller;


import com.themall.model.entity.R;
import com.themall.model.validator.group.UpdateGroup;
import io.renren.pojo.form.NodeDropMenuVo;
import io.renren.pojo.vo.MenuVo;
import io.renren.service.SysMenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
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
        return R.ok().put("menu", menuList).put("permissions", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
    }

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param sysMenu 查询实体
     * @return 所有数据
     */
    @GetMapping
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public R getList() {
        return R.data(sysMenuService.getList());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public R selectOne(@PathVariable Serializable id) {
        return R.data(this.sysMenuService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param menuVo 实体对象
     * @return 新增结果
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public R insert(@RequestBody @Validated MenuVo menuVo) {
        return R.data(this.sysMenuService.saveMenuVo(menuVo));
    }

    /**
     * 修改数据
     *
     * @param sysMenu 实体对象
     * @return 修改结果
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public R update(@RequestBody @Validated(UpdateGroup.class) MenuVo sysMenu) {
        return R.status(this.sysMenuService.updateMenuVo(sysMenu));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public R delete(@RequestBody List<String> ids) {
        return R.data(this.sysMenuService.delete(ids));
    }

    @PutMapping("nodeDrop")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public R nodeDrop(@RequestBody @Validated(UpdateGroup.class) NodeDropMenuVo dropMenuVo) {
        return R.status(this.sysMenuService.nodeDrop(dropMenuVo));
    }
}

