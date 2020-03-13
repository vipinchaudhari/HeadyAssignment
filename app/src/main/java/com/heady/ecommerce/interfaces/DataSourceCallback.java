package com.heady.ecommerce.interfaces;

public interface DataSourceCallback<T> {

    void onAPIFetched(T data);

    void getCacheData(T data);
}
