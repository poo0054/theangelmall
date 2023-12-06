package com.themall.model.to;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SpuBoundTo implements Serializable {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
