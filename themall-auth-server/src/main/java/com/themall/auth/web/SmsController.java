package com.themall.auth.web;

import com.alibaba.fastjson2.TypeReference;
import com.themall.auth.openfeign.ThemallPartyFeign;
import com.themall.auth.openfeign.memberFerignService;
import com.themall.auth.vo.UserRegisterterVo;
import com.themall.common.utils.fileutils.UUIDUtils;
import com.themall.model.constants.AuthServerConstant;
import com.themall.model.constants.HttpStatusEnum;
import com.themall.model.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class SmsController {
    @Autowired
    ThemallPartyFeign themallPartyFeign;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    memberFerignService themallmemberFerign;

    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) {
        String s = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_PREFIX + phone);
        //存入
        if (!StringUtils.isEmpty(s)) {
            String[] s1 = s.split("_");
            Long aLong = Long.valueOf(s1[1]);
            // _当前时间  ，每次取出来，先判断是否超过5分钟，小于5分钟就不能再次发送  如果有数据，需要大于5分钟才能再次获取
            if (System.currentTimeMillis() - aLong >= (5 * 60 * 1000)) {
                //接口防刷
                String code = UUIDUtils.getUUID().substring(0, 5);
                // 存redis
                R r = themallPartyFeign.sendCode(phone, code);
                if (r.isSuccess()) {
                    stringRedisTemplate.opsForValue()
                            .set(AuthServerConstant.SMS_CODE_PREFIX + phone, code + "_" + System.currentTimeMillis(), 5,
                                    TimeUnit.MINUTES);
                    return R.httpStatus();
                }
            }
        } else {
            //接口防刷
            String code = UUIDUtils.getUUID().substring(0, 5);
            // 存redis
            R r = themallPartyFeign.sendCode(phone, code);
            if (r.isSuccess()) {
                stringRedisTemplate.opsForValue()
                        .set(AuthServerConstant.SMS_CODE_PREFIX + phone, code + "_" + System.currentTimeMillis(), 5,
                                TimeUnit.MINUTES);
                return R.httpStatus();
            }
        }
        return R.error(HttpStatusEnum.USER_ERROR_A0501);
    }

    /**
     * 重定向是从session中存储数据
     *
     * @param userRegisterterVo 用户信息
     * @param bindingResult     遗产校验信息
     * @param attributes        重定向参数
     * @return 进行重定向到注册页面，携带失败信息
     */
    @PostMapping("/regist")
    public String regist(@Validated UserRegisterterVo userRegisterterVo, BindingResult bindingResult,
                         RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            Map<String, String> map = new HashMap();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                map.put(field, defaultMessage);
            }
            attributes.addFlashAttribute("errors", map);
            return "redirect:https://auth.poo0054.top/auth/reg.html";
        }
        //效验验证码
        String code = userRegisterterVo.getCode();
        String s =
                stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_PREFIX + userRegisterterVo.getPhone());
        Map<String, String> map = new HashMap<>();
        if (!ObjectUtils.isEmpty(s)) {
            if (code.equals(s.split("_")[0])) {
                if (!ObjectUtils.isEmpty(s)) {
                    //不为空，则删除验证码
                    stringRedisTemplate.delete(AuthServerConstant.SMS_CODE_PREFIX + userRegisterterVo.getPhone());
                }
                //调用远程接口注册会员
                R regist = themallmemberFerign.regist(userRegisterterVo);
                if (regist.isSuccess()) {
                    return "redirect:https://auth.poo0054.top/auth/login.html";
                } else {
                    String data = regist.getData("msg", new TypeReference<String>() {
                    });
                    map.put("msg", data);
                }
            }
        } else if (code.equals("poo0054")) {
            //调用远程接口注册会员
            R regist = themallmemberFerign.regist(userRegisterterVo);
            if (regist.isSuccess()) {
                return "redirect:https://auth.poo0054.top/auth/login.html";
            } else {
                String data = regist.getData(new TypeReference<String>() {
                });
                map.put("msg", data);
            }
        }
        map.put("phone", "验证码错误");
        attributes.addFlashAttribute("errors", map);
        return "redirect:https://auth.poo0054.top/auth/reg.html";
    }
}
