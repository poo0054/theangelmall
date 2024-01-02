package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.themall.model.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-28 16:36:26
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenu> {
    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();

    List<Long> queryAllMenuId(Long userId);

    boolean orderNumPlusOne(@Param("parentId") Long parentId, @Param("orderNum") Integer orderNum);
}

