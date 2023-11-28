package com.themall.ware.to;

import lombok.Data;

/**
 * 锁定结果
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.ware.to
 * @ClassName: LockStockResult
 * @Author: theangel
 * @Date: 2021/9/14 22:33
 */
@Data
public class LockStockResult {
    private Long skuId;
    private Integer num;
    //锁定是否成功
    private Boolean locked;

}
