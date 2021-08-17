package com.theangel.themall.themallproduct;

import com.theangel.common.utils.fileutils.UUIDUtils;
import com.theangel.themall.product.ThemallProductApplication;
import com.theangel.themall.product.dao.AttrGroupDao;
import com.theangel.themall.product.service.CategoryService;
import com.theangel.themall.product.vo.SkuItemVo;
import com.theangel.themall.product.vo.SpuItemAttrGroupVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThemallProductApplication.class)
public class ThemallProductApplicationTests {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient singRedissonClient;
    @Autowired
    private AttrGroupDao attrGroupDao;

    @Test
    public void redissonTest() {
//        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.supplyAsync();
        List<SpuItemAttrGroupVo> spuItemAttrGroupVos = attrGroupDao.attrGroupWithAttrBySpuId(16L);
        System.out.println(spuItemAttrGroupVos);
    }

    @Test
    public void redisTest() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("holle", "word" + UUIDUtils.getUUID());
        String holle = ops.get("holle");
        System.out.println("之前存储的redis值为：" + holle);
    }


    @Test
    public void test() {
        Long[] cateLogIds = categoryService.getCateLogPath(250L);
        System.out.println(cateLogIds.length);
        System.out.println(cateLogIds[0]);
        System.out.println(cateLogIds[1]);
    }


}
