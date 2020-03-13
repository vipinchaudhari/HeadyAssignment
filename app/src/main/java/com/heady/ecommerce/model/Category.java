package com.heady.ecommerce.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Category {

    @PrimaryKey()
    @SerializedName("id")
    @Expose
    public Integer id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    public String name;

    @Ignore
    @SerializedName("products")
    @Expose
    public List<Product> products = null;

    @Ignore
    @SerializedName("child_categories")
    @Expose
    public List<Integer> childCategories = null;

    @Ignore
    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Integer> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<Integer> childCategories) {
        this.childCategories = childCategories;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", childCategories=" + childCategories +
                '}';
    }
}