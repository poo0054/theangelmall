package com.themall.model.exception;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.ware.exception
 * @ClassName: NoStockException
 * @Author: theangel
 * @Date: 2021/9/14 22:57
 */
public class NoStockException extends RuntimeException {

    private Integer skuId;

    public NoStockException(Long skuId) {
        super("商品id：" + skuId + "没有足够的库存了");
    }

    public NoStockException() {
        super("库存不足");
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
}
