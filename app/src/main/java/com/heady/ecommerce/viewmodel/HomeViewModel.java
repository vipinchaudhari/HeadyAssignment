package com.heady.ecommerce.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.heady.ecommerce.application.ECommerceApp;
import com.heady.ecommerce.model.Categories;
import com.heady.ecommerce.interfaces.DataSourceCallback;
import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.model.Event;
import com.heady.ecommerce.repository.HeadyService;
import com.heady.ecommerce.repository.NetworkCall;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.repository.database.AppDatabase;
import com.heady.ecommerce.repository.database.DatabaseCall;
import com.heady.ecommerce.utils.RXJavaHelper;
import com.heady.ecommerce.view.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class HomeViewModel extends BaseViewModel implements DataSourceCallback {

    private static final String TAG = HomeViewModel.class.getSimpleName();

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

    public MutableLiveData<Resource<List<Category>>> getCategories() {
        return databaseCall.query(db.categoryDao().getAllCategories(), this, categoriesLiveData);
    }

    public void loadProductsData() {
        networkCall.makeCall(headyService.getProductsData(), this);
    }

    public void onCategorySelected(Category category, Integer position) {
        eventLiveData.sendAction(new Event<>(CATEGORY_SELECTED, category, position));
    }

    public CategoryAdapter  getCategoryAdapter() {
        return categoryAdapter;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public void onAPIFetched(final Object data) {
        RXJavaHelper.getInstance().complete(new Action() {
            @Override
            public void run() throws Exception {
                db.rootDao().insertWholeData((Categories) data);
            }
        }, new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                getCategories();
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        Log.d(TAG, "onAPIFetched() " + data);
    }

    @Override
    public void getCacheData(Object data) {
        Log.d(TAG, "getCacheData() " + data);
        if (data == null || ((List<Category>) data).size() == 0) {
            loadProductsData();
        } else {
            categoryList.clear();
            categoryList.addAll((List<Category>) data);
        }
    }

}
