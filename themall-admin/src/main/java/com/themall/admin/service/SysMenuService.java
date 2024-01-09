package com.themall.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.themall.admin.pojo.form.NodeDropMenuVo;
import com.themall.admin.pojo.vo.MenuVo;
import com.themall.model.entity.SysMenu;

import java.util.List;

/**
 * 菜单管理(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2023-12-28 16:36:30
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户菜单列表
     */
    List<MenuVo> getUserMenuList(Long userId);

    List<Long> queryAllMenuId(Long createUserId);

    /**
     * 获取所有菜单
     *
     * @return 菜单返回
     */
    List<MenuVo> getList();

    String saveMenuVo(MenuVo menuVo);

    boolean updateMenuVo(MenuVo sysMenu);

    boolean delete(List<Long> idList);

    boolean nodeDrop(NodeDropMenuVo dropMenuVo);
}

