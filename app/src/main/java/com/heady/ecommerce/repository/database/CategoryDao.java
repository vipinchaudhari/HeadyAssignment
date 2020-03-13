package com.heady.ecommerce.repository.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.heady.ecommerce.model.Category;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CategoryDao {

    @Query("Select * from category where id not in (select child_category_id from childcategory)")
    Single<List<Category>> getAllCategories();

    @Query("Select * from category where id=:categoryId")
    Single<List<Category>> getCategory(int categoryId);
}
