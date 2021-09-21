package com.theangel.common.to.mq;

import lombok.Data;
import lombok.ToString;


/**
 * 库存锁定to
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.common.to.mq
 * @ClassName: StockLockedTo
 * @Author: theangel
 * @Date: 2021/9/21 15:42
 */
@Data
@ToString
public class StockLockedTo {
    //库存锁定工作单id
    private Long id;
    //工作单详情id
    private StockDetailTo detailTo;
}
