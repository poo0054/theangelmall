package com.theangel.themall.search.service;

import com.theangel.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface productSaveService {

    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
