package com.heady.ecommerce.repository.database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.heady.ecommerce.model.Product;
import com.heady.ecommerce.model.ProductVariants;
import com.heady.ecommerce.model.Variant;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ProductDao {

    @Query("Select * from Product")
    Single<List<Product>> getAllProducts();

    @Query("Select * from Product where category_id=:categoryId")
    Single<List<Product>> getProducts(int categoryId);

    @Transaction
    @Query("Select * from Product where id=:productId")
    Single<ProductVariants> getProductWithVariant(int productId);

    @Transaction
    @Query("select * from Variant where product_id=:productId")
    List<Variant> getVariants(int productId);

    @Transaction
    @Query("Select * from Product where id=:productId")
    List<ProductVariants> getProductWithVariant1(int productId);

    @Query("Select * from Product LEFT JOIN ProductRank ON product.id = productrank.id where product.category_id=:categoryId order by productrank.shares DESC")
    Single<List<Product>> getProductsWithSharedFilter(int categoryId);

    @Query("Select * from Product LEFT JOIN ProductRank ON product.id = productrank.id where product.category_id=:categoryId order by productrank.view_count DESC")
    Single<List<Product>> getProductsWithViewedFilter(int categoryId);

    @Query("Select * from Product LEFT JOIN ProductRank ON product.id = productrank.id where product.category_id=:categoryId order by productrank.order_count DESC")
    Single<List<Product>> getProductsWithPurchasedFilter(int categoryId);
}
