package com.themall.ware.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MergeVo implements Serializable {
    /**
     * purchaseId: 1, //整单id
     * items:[1,2,3,4] //合并项集合
     */
    private Long purchaseId;
    private List<Long> items;

}
