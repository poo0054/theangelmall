package com.theangel.themall.product.openfeign.fallback;

import com.theangel.common.exception.BizCodeEnum;
import com.theangel.common.utils.R;
import com.theangel.themall.product.openfeign.SeckillFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.product.openfeign.fallback
 * @ClassName: SeckilleFeignServiceFallback
 * @Author: theangel
 * @Date: 2021/10/13 22:56
 */
@Slf4j
@Service
public class SeckilleFeignServiceFallback implements SeckillFeignService {

    /**
     * 出现问题，返回此方法
     *
     * @param skuId
     * @return
     */
    @Override
    public R getSkuSeckillInfo(Long skuId) {
        log.error("getSkuSeckillInfo方法调用熔断");
        return R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMsg());
    }
}
