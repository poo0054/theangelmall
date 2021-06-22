package com.theangel.themall.themallproduct;

import com.theangel.themall.product.entity.CategoryEntity;
import com.theangel.themall.product.service.BrandService;
import com.theangel.themall.product.service.CategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ThemallProductApplicationTests {

    @Autowired
    CategoryService categoryService;

    @Test
    public void test() {
        List<CategoryEntity> list = categoryService.list();
    }

}
