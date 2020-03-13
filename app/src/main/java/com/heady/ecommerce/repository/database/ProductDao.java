package com.heady.ecommerce.repository.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.heady.ecommerce.model.Product;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ProductDao {

    @Query("Select * from Product")
    Single<List<Product>> getAllProducts();

    @Query("Select * from Product where category_id=:categoryId")
    Single<List<Product>> getProducts(int categoryId);
}
