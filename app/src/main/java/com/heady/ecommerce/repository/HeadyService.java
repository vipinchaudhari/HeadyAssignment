package com.heady.ecommerce.repository;

import com.heady.ecommerce.application.ECommerceApp;
import com.heady.ecommerce.model.Categories;

import javax.inject.Inject;

import io.reactivex.Single;

public class HeadyService {

    @Inject
    HeadyApi api;

    public HeadyService() {
        ECommerceApp.getHeadyComponent().inject(this);
    }

    public Single<Categories> getProductsData(){
        return api.getProductsData();
    }
}
