package com.theangel.themall.seckill.config;

import cn.hutool.core.convert.impl.CharsetConverter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.WebFluxCallbackManager;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.theangel.common.exception.BizCodeEnum;
import com.theangel.common.utils.R;
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
@Configuration
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
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMsg());
        httpServletResponse.setContentType(ContentType.JSON.toString());
        httpServletResponse.setCharacterEncoding(CharsetUtil.UTF_8);
        httpServletResponse.getWriter().write(JSON.toJSONString(error));
    }
}
