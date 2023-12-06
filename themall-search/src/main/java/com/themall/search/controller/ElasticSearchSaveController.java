package com.themall.search.controller;

import com.themall.model.constants.HttpStatusEnum;
import com.themall.model.entity.R;
import com.themall.model.to.es.SkuEsModel;
import com.themall.search.service.productSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author poo0054
 */
@RestController
@RequestMapping("/search/save")
@Slf4j
public class ElasticSearchSaveController {

    @Autowired
    productSaveService productSaveService;

    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        try {
            boolean b = productSaveService.productStatusUp(skuEsModels);
            if (b) {
                return R.httpStatus();
            } else {
                return R.error(HttpStatusEnum.SYSTEM_ERROR_B0100);
            }
        } catch (IOException e) {
            log.error("ElasticSearchSaveController------->商品商家错误:{}", e.getMessage(), e);
            return R.error(HttpStatusEnum.SYSTEM_ERROR_B0100);
        }

    }
}
