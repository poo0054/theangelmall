package com.themall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.themall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private String app_id = "2021000118622315";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCsdN3zvPa9i8XCnx4SwxeBxXVL+PHMRhZQIsxLscT1xBvszw+TNBjyVpm+yL+1paJFKYf0dpVnpdqnTZVWbyutZok0e+YNdDCZGgonoRq4lm5tfdyiP2pO7zLFaYi+PxrrLqkU1mSWYFEE+2SBRS1oj5GH+uwFrNEldkScSsQi0ULHUrXjhX3C6WJYlgBVK+gkoAfrbxpmm0xf51HGP/MBDsigkgn5gM1sQ7/tlUKoX1RcRjsaxDmn5tc7WfAZ2l92Oa0O+e44BQaTSiFMCH1k1A27nHqgczDGYNlJKdr+dCx0cO4D5goTqrnmD9RQHZhR38IdYn3SJsz4y3w+aG0pAgMBAAECggEBAJxpoqTeTiu8CbFq/6ukpj1dCfXN5ACR0Mr9xK0inrEJHQq7AjVQmw+w2+Dd0oK9p19zO4vNwdQv6RC0nS23xrWOugKQHSc2qp0xsOQgikvApIdKEEJXoePVrwa9oPhr485Kk7ACg1igItuR5qYYSAb9r9DVFBxRkZ+YlMHsTdgE1QRfjSuNlD5XKbw/PbqqA5g19PBrWatwIDogmIdXxBClcgvclRowcwd5XChAQS693oxzhpJyWa7NtVq57RRl2JJf2g+o61RNB+Zuq80pmf3YmsKnqjuYQY/AnQJivnQcg8Bm20jn7WG/doyrAGS/ySFTCaYIRL7RjwL7teXvHwECgYEA5hsJjRz33AmL1ffvpnJDSEma9uKqZ6uJDXXSUeImKE4Py0zvIp8skDYQIIhJpn2BVQmGPjR4B6mUzRiBMo/25hOLnbUKtkpcfn8IviKls2dqFcOO6CysBNI6cNq3LKMpU9h4FwOy0GrcoQ+CtGYfzeMNOWBx+wDOmgigLketisUCgYEAv90N4+MJHnfrvlKNtRSPL94UYHW2qoBRYsWxsGdfaRYva+vdMoEZ9BB0YdoujvDrCHGc1LMPKGj2Bu4Ql7bfxB4Ca8Lwr+mOgABzMxSe6p7/DWuatnph9JWqULaqodq6cjj4yh2cWsNq+A29yaPXJDe2BvEYkfKIK2hNPSSIjxUCgYAI7qPARo+YiwZSl/1FiMnz1GefjczvmUkIrLotU4oHpoAWdDYUMfmY34BgwumY/OY4VtZPM9o08fBExd6+B7id1cRlqtFvohNVFblRKCcmf60uixjRCmrjBQYfu13A7dR83LpMqmgWjw9hcSixuAUkCNxKjePeynk6oXsWUNH7wQKBgAJYUFAluTnPG2mDFspMywc5vqQDLE1fLh937RkL2mWtaLz38Acq/sc14EZF5WXH2geLY/BRk/DZf5BMHpXMHWYHO95XTpvHzf8Qglfwe5TAhruCQizSyBm2LpO399PsmXTF73yA6iVN2CPZ/yTUxM9jgu0Iy1UVUsa9Cpyhsq2VAoGAZQlRBTC64isKHUYIEubKSt4AUMfxmo52GHbE3laSLg/zs9HHwy40HybXdyiIM65f0QxYFZgbyzyk0EdJ+59cqfDjjwUw7UewUUFGcz0XwtJhnML+fDuJayXe2tPxawOTIc4yVjLwzBRiEFtj2tO/DBOhZ1r1SX/5BjtVZ/tLizw=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqCq3gGGpORbZ7JKvA32Rr39TTurv2ehD7CpsIsPY7U1kW0J6YBh9vhecDkVxMpW3wAP9lnBHLNQU/hSiO1kqKZQqmPA82xhgwnGY0IBGS985FSQcJBWdLJy5lPlCphfQPBVhy4MPzDpBQPRa9g1V4RI1Dn6EOWw80ufrDr1D7XRvZWsKLL2S3pfLVI5DteizZJULd9YvttO78R95DX6bTwMkT5Rui/A3Fy0ZFAitcOZQBaDLr/rImZ294hhbnQ+rs4y5+5bW9pi+xJTfN09uPExvJITHRPNCmgsVquW3Wu00beMFi3xIriodCJka5CnUnJ+7bkRrOKiK114GcxhX2QIDAQAB";

    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    private String sign_type = "RSA2";

    // 字符编码格式
    private String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    /**
     * 订单超时时间
     */
    private String timeout_express = "15m";

    /**
     * 付款
     *
     * @param vo
     * @return
     * @throws AlipayApiException
     */
    public String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应：" + result);
        return result;
    }
}
