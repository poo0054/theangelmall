package com.theangel.themall.themallproduct;

import com.theangel.themall.product.ThemallProductApplication;
import com.theangel.themall.product.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThemallProductApplication.class)
public class ThemallProductApplicationTests {
    @Autowired
    private CategoryService categoryService;

    @Test
    public void test() {
        Long[] cateLogIds = categoryService.getCateLogPath(250L);
        System.out.println(cateLogIds.length);
        System.out.println(cateLogIds[0]);
        System.out.println(cateLogIds[1]);
    }

}
