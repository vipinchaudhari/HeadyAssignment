package com.heady.ecommerce.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Variant {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @ColumnInfo(name = "color")
    @SerializedName("color")
    @Expose
    private String color;
    @ColumnInfo(name = "size")
    @SerializedName("size")
    @Expose
    private Integer size;
    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    private Integer price;


    @ColumnInfo(name = "product_id")
    @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "product_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    int productId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", productId=" + productId +
                '}';
    }
}