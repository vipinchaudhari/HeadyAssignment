package com.heady.ecommerce.repository;


import com.heady.ecommerce.di.DaggerHeadyComponent;
import com.heady.ecommerce.model.Categories;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class HeadyService {

    @Inject
    HeadyApi api;

    public HeadyService() {
        DaggerHeadyComponent.create().inject(this);
    }

    public Single<Categories> getProductsData(){
        return api.getProductsData();
    }
}
