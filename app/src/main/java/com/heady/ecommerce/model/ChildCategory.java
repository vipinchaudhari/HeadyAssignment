package com.heady.ecommerce.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class ChildCategory {

    public ChildCategory(int parentCategoryId, int childCategoryId) {
        this.parentCategoryId = parentCategoryId;
        this.childCategoryId = childCategoryId;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "parent_category_id")
    @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "parent_category_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    public int parentCategoryId;

    @ColumnInfo(name = "child_category_id")
    public int childCategoryId;

}
