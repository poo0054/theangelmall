package com.theangel.themall.auth.openfeign;

import com.theangel.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient("third-party")
public interface ThemallPartyFeign {

    @GetMapping("/third-party/sms/send")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
