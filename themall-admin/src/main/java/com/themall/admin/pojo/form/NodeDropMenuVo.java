package com.themall.admin.pojo.form;

import com.themall.admin.enums.DropType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author poo0054
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NodeDropMenuVo implements Serializable {
    private static final long serialVersionUID = -1344769927086411429L;

    /**
     * 当前节点
     */
    @NotNull
    private Long nodeId;

    /**
     * 结束拖拽时最后进入的节点
     */
    @NotNull
    private Long afterNodeId;

    /**
     * 被拖拽节点的放置位置
     */
    @NotNull
    private DropType dropType;
}
