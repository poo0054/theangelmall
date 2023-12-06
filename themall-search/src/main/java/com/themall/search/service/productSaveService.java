package com.themall.search.service;

import com.themall.model.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author poo0054
 */
public interface productSaveService {

    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
