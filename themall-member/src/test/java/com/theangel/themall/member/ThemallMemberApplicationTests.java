package com.theangel.themall.member;

import com.alibaba.nacos.common.utils.Md5Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

class ThemallMemberApplicationTests {
    public static void main(String[] args) {
        String md5 = Md5Utils.getMD5("123456".getBytes());
        System.out.println(md5);
        System.out.println(DigestUtils.md5Hex("123456"));
    }

    @Test
    void contextLoads() {

    }

}
