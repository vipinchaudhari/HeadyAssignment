package com.heady.ecommerce.repository;

import com.heady.ecommerce.model.Categories;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface HeadyApi {

    @GET("/json")
    Single<Categories> getProductsData();
}
