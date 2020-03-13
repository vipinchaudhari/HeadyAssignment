package com.heady.ecommerce.repository.database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.model.CategoryAndChildCategories;
import com.heady.ecommerce.model.ChildCategory;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ChildCategoryDao {

    @Query("Select * from childcategory")
    Single<List<ChildCategory>> getAllChildCategories();

    @Query("Select * from category where id in (Select child_category_id from childcategory where parent_category_id=:categoryId)")
    //@Query("Select * from category INNER JOIN childcategory ON category.id=child_category_id where category.id=:categoryId")
    Single<List<Category>> getChildCategories(int categoryId);

    @Transaction
    @Query("Select * from category where id not in (Select child_category_id from childcategory)")
    Single<List<CategoryAndChildCategories>> getCategoriesWithChildCategories();
}
