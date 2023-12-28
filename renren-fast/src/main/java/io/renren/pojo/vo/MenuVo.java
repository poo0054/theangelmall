package io.renren.pojo.vo;

import com.themall.model.enums.MenuTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author poo0054
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo implements Serializable {
    private static final long serialVersionUID = -1344769927086411429L;

    private Long id;

    //父菜单ID
    private Long parentId;

    //菜单URL
    private String path;

    private String name;
    //视图
    private String component;

    //菜单高亮
    private String active;

    //授权(多个用逗号分隔，如：user:list,user:create)
    private String perms;

    //排序
    private Integer orderNum;

    private List<MenuVo> children;
    /**
     * 元数据
     */
    private Meta meta;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta implements Serializable {
        private static final long serialVersionUID = 3287758168176072884L;
        //菜单图标
        private String icon;
        //标签
        private String tag;
        //整页路由
        private Integer fullpage;
        //隐藏菜单
        private Integer hidden;
        //隐藏面包屑
        private Integer hiddenbreadcrumb;
        //颜色
        private String color;
        //菜单名称
        private String title;
        //类型  menu iframe link button
        private MenuTypeEnum type;
    }
}
