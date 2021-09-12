package com.theangel.themall.order.to;

import com.theangel.themall.order.vo.MemberAddressVo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 收货人信息及运费
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.to
 * @ClassName: FareVo
 * @Author: theangel
 * @Date: 2021/9/12 13:59
 */
@Data
public class FareVo {
    private MemberAddressVo memberReceiveAddressVo;
    private BigDecimal fare;
}
