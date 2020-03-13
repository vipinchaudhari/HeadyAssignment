package com.heady.ecommerce.di;

import com.heady.ecommerce.view.adapter.CategoryAdapter;
import com.heady.ecommerce.viewmodel.HomeViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonModule {

    @Provides
    public CategoryAdapter provideCategoryAdapter(HomeViewModel homeViewModel){
        return new CategoryAdapter(homeViewModel);
    }
}
