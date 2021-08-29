package com.theangel.themall.cart.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物项，一个个的商品
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart.vo
 * @ClassName: CartItem
 * @Author: theangel
 * @Date: 2021/8/29 18:25
 */
@Data
public class CartItem {
    private Long skuId;
    //是否选中
    private Boolean check;
    //商品标题
    private String title;
    //商品默认图片
    private String image;
    private List<String> skuAttr;
    private BigDecimal price;
    private Integer count;
    //商品总价
    private BigDecimal totalPrice;

    /**
     * 获取总价
     *
     * @return
     */
    public BigDecimal getTotalPrice() {
        //总价是单价乘数量
        BigDecimal multiply = this.price.multiply(new BigDecimal(this.count));
        this.totalPrice = multiply;
        return totalPrice;
    }
}
