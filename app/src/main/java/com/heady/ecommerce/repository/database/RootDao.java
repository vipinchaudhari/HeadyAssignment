package com.heady.ecommerce.repository.database;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.heady.ecommerce.model.Categories;
import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.model.ChildCategory;
import com.heady.ecommerce.model.Product;
import com.heady.ecommerce.model.ProductRank;
import com.heady.ecommerce.model.Ranking;
import com.heady.ecommerce.model.Variant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class RootDao {
    private static final String TAG = RootDao.class.getSimpleName();

    public void insertWholeData(Categories categories) {
        if (categories != null && categories.getCategories() != null && categories.getCategories().size() != 0) {
            List<Category> allCategories = categories.getCategories();

            int categoriesSize = allCategories.size();
            //insert categories
            _insertAllCategory(allCategories);
            for (int i = 0; i < categoriesSize; i++) {

                //insert child categories
                insertAllChildCategories(allCategories.get(i).getChildCategories(), allCategories.get(i).getId());
                //insert products
                insertAllProducts(allCategories.get(i).getProducts(), allCategories.get(i).getId());
            }
            //insert Ranking
            _insertAllRankings(categories.getRankings());

            //insert product rank
            insertAllProductRanks(categories.getRankings());
        }
    }

    void insertAllChildCategories(List<Integer> childCategories, int parentCategoryId) {
        int childCategoriesSize;
        if (childCategories != null) {
            childCategoriesSize = childCategories.size();
            List<ChildCategory> childCategoryList = new ArrayList<>();
            for (int i = 0; i < childCategoriesSize; i++) {
                Log.d(TAG, "child_category: " + childCategories.get(i));
                childCategoryList.add(new ChildCategory(parentCategoryId, (int) childCategories.get(i)));
            }
            _insertAllChildCategories(childCategoryList);

        }
    }

    void insertAllProducts(List<Product> products, int categoryId) {
        if (products != null) {
            Product product;
            List<Variant> allVariants = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                product = products.get(i);
                product.setCategoryId(categoryId);
                product.setTaxName(product.getTax().getName());
                product.setTaxValue(product.getTax().getValue());
                //prepare records for variant table
                if (product.getVariants() != null) {
                    allVariants.addAll(product.getVariants());
                    if (allVariants != null) {
                        for (int j = 0; j < allVariants.size(); j++) {
                            allVariants.get(j).setProductId(product.getId());
                        }
                    }
                }
            }
            //insert records into product
            _insertAllProducts(products);
            //insert records into variant
            _insertAllVariants(allVariants);
        }
    }

    void insertAllProductRanks(List<Ranking> rankings) {
        if (rankings != null) {
            List<ProductRank> productRanks = new ArrayList<>();
            int rankingId;
            for (int i = 0; i < rankings.size(); i++) {
                productRanks = rankings.get(i).getProducts();
                rankingId = rankings.get(i).getId();
                if (productRanks != null) {
                    for (int j = 0; j < productRanks.size(); j++) {
                        productRanks.get(j).setRankingId(rankingId);
                    }
                }

            }
            if (productRanks.size() > 0) {
                _insertAllProductRanks(productRanks);
            }
        }
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _insertAllCategory(List<Category> categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _insertAllChildCategories(List<ChildCategory> childCategories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _insertAllProducts(List<Product> products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _insertAllRankings(List<Ranking> rankings);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _insertAllProductRanks(List<ProductRank> productRanks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _insertAllVariants(List<Variant> variants);

    @Query("Select * from category")
    public abstract Single<List<Category>> getAllCategories();


}
