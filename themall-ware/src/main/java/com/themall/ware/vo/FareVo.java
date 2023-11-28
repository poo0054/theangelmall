package com.themall.ware.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 运费及收货人
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.ware.vo
 * @ClassName: FareVo
 * @Author: theangel
 * @Date: 2021/9/11 18:47
 */
@Data
public class FareVo {
    private MemberReceiveAddressVo memberReceiveAddressVo;
    private BigDecimal fare;
}
