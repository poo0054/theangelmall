package com.themall.thirdparty.contorller;

import com.themall.common.utils.R;
import com.themall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("third-party")
public class SmsController {

    @Autowired
    SmsComponent smsComponent;

    /**
     * 发送短信，提供给第三方服务调用
     *
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("/sms/send")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        smsComponent.sendSmsLundroid(phone, code);
        return R.httpStatus();
    }

}
