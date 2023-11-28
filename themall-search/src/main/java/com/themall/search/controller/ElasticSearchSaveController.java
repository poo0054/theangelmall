package com.themall.search.controller;

import com.themall.common.exception.BizCodeEnum;
import com.themall.common.to.es.SkuEsModel;
import com.themall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search/save")
@Slf4j
public class ElasticSearchSaveController {
    @Autowired
    com.themall.search.service.productSaveService productSaveService;

    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        try {
            boolean b = productSaveService.productStatusUp(skuEsModels);
            if (b) {
                return R.ok();
            } else {
                return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ElasticSearchSaveController------->商品商家错误{}", e);
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }

    }
}
