package com.heady.ecommerce.repository.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.model.ChildCategory;
import com.heady.ecommerce.model.Product;
import com.heady.ecommerce.model.ProductRank;
import com.heady.ecommerce.model.Ranking;
import com.heady.ecommerce.model.Variant;

@Database(entities = {Category.class, ChildCategory.class, Product.class, ProductRank.class, Ranking.class, Variant.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RootDao rootDao();
    public  abstract CategoryDao categoryDao();
    public abstract ChildCategoryDao childCategoryDao();
    public abstract ProductDao productDao();
}