package com.heady.ecommerce.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class Ranking {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "ranking")
    @SerializedName("ranking")
    @Expose
    private String ranking;

    @Ignore
    @SerializedName("products")
    @Expose
    private List<ProductRank> products = null;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<ProductRank> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRank> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "id=" + id +
                ", ranking='" + ranking + '\'' +
                ", products=" + products +
                '}';
    }
}