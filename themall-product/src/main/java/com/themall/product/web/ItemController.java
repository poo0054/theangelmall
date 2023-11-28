package com.themall.product.web;

import com.themall.product.service.SkuInfoService;
import com.themall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ExecutionException;

@Controller
public class ItemController {

    @Autowired
    SkuInfoService skuInfoService;

    @GetMapping("/item/{skuid}.html")
    public String item(@PathVariable("skuid") Long skyId, Model model) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo = skuInfoService.itemBySkuId(skyId);
        model.addAttribute("skuItem", skuItemVo);
        return "item";
    }
}
