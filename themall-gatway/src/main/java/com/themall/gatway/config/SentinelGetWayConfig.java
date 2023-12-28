package com.themall.gatway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import com.themall.model.entity.R;
import com.themall.model.enums.HttpStatusEnum;
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
            R error = R.error(HttpStatusEnum.SYSTEM_ERROR_B0210);
            return ServerResponse.ok().body(Mono.just(JSON.toJSONString(error)), String.class);
        });
    }

}
