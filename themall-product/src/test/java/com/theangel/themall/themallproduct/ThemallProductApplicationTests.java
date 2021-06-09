package com.theangel.themall.themallproduct;

import com.theangel.themall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThemallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {
        System.out.println(brandService.list());
    }

}
