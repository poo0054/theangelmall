package com.theangel.themall.order.listener;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.theangel.themall.order.config.AlipayTemplate;
import com.theangel.themall.order.service.OrderService;
import com.theangel.themall.order.vo.PayAsyncVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 支付宝回调
 *
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.order.listener
 * @ClassName: OrderPayListener
 * @Author: theangel
 * @Date: 2021/10/2 0:56
 */
@RestController
public class OrderPayListener {
    @Autowired
    OrderService orderService;
    @Autowired
    AlipayTemplate alipayTemplate;

    /**
     * 必须给支付宝返回success
     *
     * @return
     */
    @PostMapping("/pay/notify")
    public String handleAliPay(@RequestBody Map map, HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        System.out.println("签名通过themall-order支付宝通知信息为：" + JSON.toJSONString(map));
        boolean signVerified = isVerified(map);
        if (signVerified) {
            System.out.println("签名通过themall-order支付宝通知信息为：" + JSON.toJSONString(map));
            return orderService.handleAliPay(JSON.parseObject(JSON.toJSONString(map), PayAsyncVo.class));
        } else {
            throw new RuntimeException("签名失败");
        }


    }

    private boolean isVerified(Map map) {
        //获取支付宝POST过来反馈信息
//        Map<String, String> treeMap = JSON.parseObject(JSON.toJSONString(payAsyncVo), TreeMap.class);
//        Map<String, String> params = new HashMap<>();
//        treeMap.forEach((k, v) -> {
//            try {
//                byte[] bytes = v.getBytes("ISO-8859-1");
//                v = new String(bytes, "utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            params.put(k, v);
//        });
        /*Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);e
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }*/

        //调用SDK验证签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(map, alipayTemplate.getAlipay_public_key(), alipayTemplate.getCharset(), alipayTemplate.getSign_type());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return signVerified;
    }
}
