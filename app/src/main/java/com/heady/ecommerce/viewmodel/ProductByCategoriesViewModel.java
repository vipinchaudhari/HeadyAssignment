package com.heady.ecommerce.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.heady.ecommerce.application.ECommerceApp;
import com.heady.ecommerce.model.Event;
import com.heady.ecommerce.model.Product;
import com.heady.ecommerce.repository.DatabaseCall;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.repository.database.AppDatabase;
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

    int categoryId;

    {
        ECommerceApp.getHeadyComponent().inject(this);
        productAdapter = new ProductAdapter(this);
    }

    public MutableLiveData<Resource<List<Product>>> getProductsByCategory(int categoryId) {
        this.categoryId = categoryId;
        return databaseCall.query(db.productDao().getProducts(categoryId), null, productsLiveData);
    }

    public ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    public void setProducts(List<Product> products) {
        if (products != null && products.size() > 0) {
            productList = products;
            productAdapter.setProducts(products);
        }
    }

    public List<Product> getProducts() {
        return productList;
    }

    public void onProductSelected(Product product, Integer position) {
        eventLiveData.sendAction(new Event<>(PRODUCT_SELECTED, product, position));
    }

    public void setPurchasedFilter(String filter) {
        databaseCall.query(db.productDao().getProductsWithPurchasedFilter(categoryId), null, productsLiveData);
    }

    public void setSharedFilter(String filter) {
        databaseCall.query(db.productDao().getProductsWithSharedFilter(categoryId), null, productsLiveData);
    }

    public void setViewedFilter(String filter) {
        databaseCall.query(db.productDao().getProductsWithViewedFilter(categoryId), null, productsLiveData);
    }
}
