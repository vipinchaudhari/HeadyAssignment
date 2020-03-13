package com.heady.ecommerce.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Product {

    @PrimaryKey
    @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    @SerializedName("id")
    @Expose
    public Integer id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    public String name;

    @ColumnInfo(name = "date_added")
    @SerializedName("date_added")
    @Expose
    public String dateAdded;

    @Ignore
    @SerializedName("variants")
    @Expose
    public List<Variant> variants = null;

    @Ignore
    @SerializedName("tax")
    @Expose
    public Tax tax;

    @ColumnInfo(name = "tax_name")
    public String taxName;

    @ColumnInfo(name = "tax_value")
    public Double taxValue;

    @ColumnInfo(name = "category_id")
    int categoryId;

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

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Double getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(Double taxValue) {
        this.taxValue = taxValue;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                ", variants=" + variants +
                ", tax=" + tax +
                ", taxName=" + taxName +
                ", taxValue=" + taxValue +
                ", categoryId=" + categoryId +
                '}';
    }
}