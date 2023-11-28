package com.themall.search.service;

import com.themall.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface productSaveService {

    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
