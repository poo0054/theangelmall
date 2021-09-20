package com.theangel.themall.order.vo;

import lombok.Data;
import org.springframework.util.ObjectUtils;

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
public class OrderItemVo {
    private Long skuId;
    //商品标题
    private String title;
    //商品默认图片
    private String image;
    private List<String> skuAttr;
    //单价
    private BigDecimal price;
    //数量
    private Integer count;
    //商品总价
    private BigDecimal totalPrice;

/*    //TODO  是否有货待查询
    private boolean hasStock;*/

    //TODO  商品重量
    private BigDecimal width;

    /**
     * 获取总价
     *
     * @return
     */
    public BigDecimal getTotalPrice() {
        //单价不为空
        if (!ObjectUtils.isEmpty(this.price)) {
            //数量不为空
            if (ObjectUtils.isEmpty(this.count)) {
                //总价是单价乘数量
                BigDecimal multiply = this.price.multiply(new BigDecimal(this.count));
                this.totalPrice = multiply;
                return totalPrice;
            }
            //数量为空，返回单价
            this.totalPrice = this.price;
            return totalPrice;
        }
        //都为空 返回0
        return new BigDecimal(0);
    }


}
