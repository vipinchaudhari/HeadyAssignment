package com.heady.ecommerce.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.heady.ecommerce.application.ECommerceApp;
import com.heady.ecommerce.model.Categories;
import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.model.Event;
import com.heady.ecommerce.repository.HeadyService;
import com.heady.ecommerce.repository.NetworkCall;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.repository.database.AppDatabase;
import com.heady.ecommerce.repository.DatabaseCall;
import com.heady.ecommerce.view.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CategoriesViewModel extends BaseViewModel {

    private static final String TAG = CategoriesViewModel.class.getSimpleName();

    MutableLiveData<Resource<List<Category>>> categoriesLiveData = new MutableLiveData<>();
    List<Category> categoryList = new ArrayList<>();
    @Inject
    HeadyService headyService;
    @Inject
    NetworkCall<Categories> networkCall;

    @Inject
    AppDatabase db;

    @Inject
    DatabaseCall<List<Category>> databaseCall;

    CategoryAdapter categoryAdapter;

    {
        ECommerceApp.getHeadyComponent().inject(this);
        categoryAdapter = new CategoryAdapter(this);
    }

    public MutableLiveData<Resource<List<Category>>> getCategories(int categoryId) {
        return databaseCall.query(db.childCategoryDao().getChildCategories(categoryId), null, categoriesLiveData);
    }

    public void onCategorySelected(Category category, Integer position) {
        eventLiveData.sendAction(new Event<>(CATEGORY_SELECTED, category, position));
    }

    public CategoryAdapter getCategoryAdapter() {
        return categoryAdapter;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        categoryAdapter.setCategories(categoryList);
        this.categoryList = categoryList;
    }

}
