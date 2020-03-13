package com.heady.ecommerce.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryAndChildCategories {
    @Embedded
    public Category category;
    @Relation(
            parentColumn = "id",
            entityColumn = "parent_category_id"
    )
    public List<ChildCategory> childCategories;
}
