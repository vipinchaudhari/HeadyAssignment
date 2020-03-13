package com.heady.ecommerce.di;


import com.heady.ecommerce.repository.HeadyService;
import com.heady.ecommerce.viewmodel.HomeViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface HeadyComponent {

    void inject(HeadyService headyService);

    void inject(HomeViewModel homeViewModel);
}
