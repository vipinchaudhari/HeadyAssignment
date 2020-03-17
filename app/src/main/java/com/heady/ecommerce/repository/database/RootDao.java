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

/**
 * this Dao insert the data from api to database
 */
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
                    List<Variant> variants = product.getVariants();
                    for (int j = 0; j < variants.size(); j++) {
                        variants.get(j).setProductId(product.getId());
                    }
                    allVariants.addAll(variants);
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
            List<ProductRank> allProductRanks = new ArrayList<>();
            int rankingId;
            for (int i = 0; i < rankings.size(); i++) {
                productRanks = rankings.get(i).getProducts();
                rankingId = rankings.get(i).getId();
                if (productRanks != null) {
                    for (int j = 0; j < productRanks.size(); j++) {
                        productRanks.get(j).setRankingId(rankingId);
                    }
                }
                allProductRanks.addAll(productRanks);
            }
            if (productRanks.size() > 0) {
                upsertProductRanks(allProductRanks);
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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> _insertAllProductRanks(List<ProductRank> productRanks);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void _updateAllProductRanks(List<ProductRank> productRanks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void _insertAllVariants(List<Variant> variants);

    @Query("Select * from category")
    public abstract Single<List<Category>> getAllCategories();

    private void upsertProductRanks(List<ProductRank> productRanks) {
        List<Long> insertResult = _insertAllProductRanks(productRanks);
        List<ProductRank> updateList = new ArrayList<>();

        for (int i = 0; i < insertResult.size(); i++) {
            if (insertResult.get(i) == -1) {
                updateList.add(productRanks.get(i));
            }
        }

        if (!updateList.isEmpty()) {
            _updateAllProductRanks(updateList);
        }
    }
}
