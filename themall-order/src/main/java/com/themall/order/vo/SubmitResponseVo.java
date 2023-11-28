package com.themall.order.vo;

import com.themall.order.entity.OrderEntity;
import lombok.Data;

/**
 * 订单生成状态
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.vo
 * @ClassName: SubmitResultVo
 * @Author: theangel
 * @Date: 2021/9/12 0:06
 */
@Data
public class SubmitResponseVo {
    /**
     * 状态码  0是成功
     * 其余都是失败
     */
    private Integer code;
    private OrderEntity orderEntity;
}
