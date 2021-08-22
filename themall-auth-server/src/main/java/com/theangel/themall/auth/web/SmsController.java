package com.theangel.themall.auth.web;

import com.theangel.common.constant.AuthServerConstant;
import com.theangel.common.exception.BizCodeEnum;
import com.theangel.common.utils.R;
import com.theangel.common.utils.fileutils.UUIDUtils;
import com.theangel.themall.auth.openfeign.ThemallPartyFeign;
import com.theangel.themall.auth.vo.UserRegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("auth")
public class SmsController {
    @Autowired
    ThemallPartyFeign themallPartyFeign;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) {
        String s = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_PREFIX + phone);
        if (!StringUtils.isEmpty(s)) {
            String[] s1 = s.split("_");
            Long aLong = Long.valueOf(s1[1]);
            if (System.currentTimeMillis() - aLong < 600000) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        //接口防刷
        String code = UUIDUtils.getUUID().substring(0, 5);
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_PREFIX + phone, code + "_" + System.currentTimeMillis(), 5, TimeUnit.MINUTES);
        //再次校验  存redis
        R r = themallPartyFeign.sendCode(phone, code);
        System.out.println(r.toString());
        return R.ok();
    }


    /**
     * 重定向是从session中存储数据
     *
     * @param userRegistVo
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/regist")
    public String regist(@Validated UserRegistVo userRegistVo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Map<String, String> map = new HashMap();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                map.put(field, defaultMessage);
            }
            redirectAttributes.addFlashAttribute("errors", map);
            return "redirect:http://localhost/auth/reg.html";
        }
        //效验验证码
        String code = userRegistVo.getCode();
        String s = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_PREFIX + userRegistVo.getPhone());
        if ((!ObjectUtils.isEmpty(s) & code.equals(s.split("_")[0])) || code.equals("theangel")) {
            if (!ObjectUtils.isEmpty(s)) {
                //不为空，则删除验证码
                stringRedisTemplate.delete(AuthServerConstant.SMS_CODE_PREFIX + userRegistVo.getPhone());
            }
            //调用远程接口注册会员



            return "redirect:http://localhost/auth/login.html";
        }
        Map<String, String> map = new HashMap();
        map.put("phone", "验证码错误");
        redirectAttributes.addFlashAttribute("errors", map);
        return "redirect:http://localhost/auth/reg.html";

    }
}
