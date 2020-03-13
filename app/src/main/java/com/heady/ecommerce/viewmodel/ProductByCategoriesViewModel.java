package com.heady.ecommerce.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.heady.ecommerce.application.ECommerceApp;
import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.model.Product;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.repository.database.AppDatabase;
import com.heady.ecommerce.repository.database.DatabaseCall;
import com.heady.ecommerce.view.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ProductByCategoriesViewModel extends BaseViewModel {
    private MutableLiveData<Resource<List<Product>>> productsLiveData = new MutableLiveData<>();
    List<Product> productList = new ArrayList<>();
    @Inject
    DatabaseCall<List<Product>> databaseCall;
    @Inject
    AppDatabase db;

    ProductAdapter productAdapter;

    {
        ECommerceApp.getHeadyComponent().inject(this);
        productAdapter = new ProductAdapter(this);
    }

    public MutableLiveData<Resource<List<Product>>> getProductsByCategory(int categoryId) {
        return databaseCall.query(db.productDao().getProducts(categoryId), null, productsLiveData);
    }

    public ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    public void setProducts(List<Product> products) {
        if (products != null && products.size()>0) {
            productList = products;
            productAdapter.setProducts(products);
        }
    }

    public List<Product> getProducts() {
        return productList;
    }
}
