package com.heady.ecommerce.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.heady.ecommerce.di.DaggerHeadyComponent;
import com.heady.ecommerce.model.Categories;
import com.heady.ecommerce.repository.HeadyService;
import com.heady.ecommerce.repository.NetworkCall;
import com.heady.ecommerce.repository.Resource;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {
    @Inject
    HeadyService headyService;
    @Inject
    NetworkCall<Categories> networkCall;

    {
        DaggerHeadyComponent.create().inject(this);
    }

    public LiveData<Resource<Categories>> loadProductsData() {
        return networkCall.makeCall(headyService.getProductsData());
    }
}
