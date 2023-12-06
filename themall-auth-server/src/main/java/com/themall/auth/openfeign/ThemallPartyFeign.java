package com.themall.auth.openfeign;

import com.themall.model.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("third-party")
public interface ThemallPartyFeign {

    @GetMapping("/third-party/sms/send")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
