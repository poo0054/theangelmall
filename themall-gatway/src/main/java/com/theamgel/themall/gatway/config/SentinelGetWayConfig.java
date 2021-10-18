package com.theamgel.themall.gatway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import com.theangel.common.exception.BizCodeEnum;
import com.theangel.common.utils.R;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill.config
 * @ClassName: SeckillSentinelConfig
 * @Author: theangel
 * @Date: 2021/10/13 21:58
 */
@Configuration
public class SentinelGetWayConfig {


    public SentinelGetWayConfig() {
        //网关限流了请求
        GatewayCallbackManager.setBlockHandler((exchange, t) -> {
            R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMsg());
            Mono<ServerResponse> body = ServerResponse.ok().body(Mono.just(JSON.toJSONString(error)), String.class);
            return body;
        });
    }

}
