package com.heady.ecommerce.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.heady.ecommerce.application.ECommerceApp;
import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.repository.database.AppDatabase;
import com.heady.ecommerce.repository.database.DatabaseCall;

import java.util.List;

import javax.inject.Inject;

public class CategoriesViewModel extends BaseViewModel {
    private MutableLiveData<Resource<List<Category>>> categoriesListLiveData = new MutableLiveData<>();
    @Inject
    AppDatabase db;

    {
        ECommerceApp.getHeadyComponent().inject(this);
    }

    @Inject
    DatabaseCall<List<Category>> databaseCall;

    public MutableLiveData<Resource<List<Category>>> getChildCategories(int categoryId) {
        return databaseCall.query(db.childCategoryDao().getChildCategories(categoryId), null, categoriesListLiveData);
    }
}
