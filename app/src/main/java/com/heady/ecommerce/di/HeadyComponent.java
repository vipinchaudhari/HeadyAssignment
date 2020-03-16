package com.heady.ecommerce.di;


import android.content.Context;

import com.heady.ecommerce.repository.HeadyService;
import com.heady.ecommerce.repository.database.AppDatabase;
import com.heady.ecommerce.viewmodel.CategoriesViewModel;
import com.heady.ecommerce.viewmodel.ChildCategoriesViewModel;
import com.heady.ecommerce.viewmodel.HomeViewModel;
import com.heady.ecommerce.viewmodel.ProductByCategoriesViewModel;
import com.heady.ecommerce.viewmodel.ProductViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, DBModule.class, ReactiveXModule.class, ContextModule.class})
public interface HeadyComponent {

    void inject(HeadyService headyService);

    void inject(HomeViewModel homeViewModel);

    AppDatabase provideDatabase();

    Context provideContext();

    void inject(ChildCategoriesViewModel childCategoriesViewModel);

    void inject(ProductByCategoriesViewModel productByCategoriesViewModel);

    void inject(CategoriesViewModel categoriesViewModel);

    void inject(ProductViewModel productViewModel);
}
