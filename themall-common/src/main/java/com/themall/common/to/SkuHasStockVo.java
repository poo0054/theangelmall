package com.themall.common.to;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SkuHasStockVo implements Serializable {
    private Long skuId;
    private Boolean hasStock;
}
