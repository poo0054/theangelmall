package com.themall.product.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.ContentType;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.themall.model.entity.R;
import com.themall.model.enums.HttpStatusEnum;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.seckill.config
 * @ClassName: SeckillSentinelConfig
 * @Author: theangel
 * @Date: 2021/10/13 21:58
 */
@Configuration(proxyBeanMethods = false)
public class SentinelConfig implements BlockExceptionHandler {

    /**
     * 之前版本  implements UrlBlockHandler
     *
     * @param request
     * @param response
     * @param e
     */
    public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException e) {
        // 降级业务处理
    }

    /**
     * 2.2.0 后
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws Exception
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e)
        throws Exception {
        R error = R.error(HttpStatusEnum.SYSTEM_ERROR_B0210);
        httpServletResponse.setContentType(ContentType.JSON.toString());
        httpServletResponse.setCharacterEncoding(CharsetUtil.UTF_8);
        httpServletResponse.getWriter().write(JSON.toJSONString(error));
    }
}
