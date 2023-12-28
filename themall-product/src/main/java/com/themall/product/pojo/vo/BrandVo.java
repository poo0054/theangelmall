package com.themall.product.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandVo implements Serializable {
    private Long BrandId;
    private String brandName;
}
