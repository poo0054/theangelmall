package com.theangel.themall.cart.vo;

import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.cart.vo
 * @ClassName: Cart
 * @Author: theangel
 * @Date: 2021/8/29 18:25
 */
@Data
public class Cart {
    private List<CartItem> cartItem;
    //商品数量
    private Integer countNum;
    //商品类型数量
    private Integer countType;
    //总价
    private BigDecimal totalAmount;
    /**
     * 减免价格
     */
    private BigDecimal reduce;

    /**
     * 总价
     *
     * @return
     */
    public BigDecimal getTotalAmount() {
        BigDecimal bigDecimal = new BigDecimal(0);
        //计算购物项总价
        if (!ObjectUtils.isEmpty(cartItem)) {
            for (CartItem item : cartItem) {
                bigDecimal = bigDecimal.add(item.getTotalPrice());
            }
        }
        //减去优惠总价
        if (!ObjectUtils.isEmpty(reduce)) {
            bigDecimal.subtract(reduce);
        }

        this.totalAmount = bigDecimal;
        return bigDecimal;
    }

    /**
     * 商品数量
     *
     * @return
     */
    public Integer getCountNum() {
        countNum = 0;
        if (!ObjectUtils.isEmpty(cartItem)) {
            for (CartItem item : cartItem) {
                countNum += item.getCount();
            }
        }
        return countNum;
    }

    /**
     * 商品类型数量
     *
     * @return
     */
    public Integer getCountType() {
        countType = 0;
        if (!ObjectUtils.isEmpty(cartItem)) {
            countType = cartItem.size();
        }
        return countType;
    }
}
