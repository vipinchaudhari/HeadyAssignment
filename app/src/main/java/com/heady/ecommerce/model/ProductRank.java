package com.heady.ecommerce.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class ProductRank {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @ColumnInfo(name = "view_count")
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;

    @ColumnInfo(name = "order_count")
    @SerializedName("order_count")
    @Expose
    private Integer orderCount;

    @ColumnInfo(name = "shares")
    @SerializedName("shares")
    @Expose
    private Integer shares;

    @ColumnInfo(name = "ranking_id")
    @ForeignKey(entity = Ranking.class, parentColumns = "id", childColumns = "ranking_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
    int rankingId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public int getRankingId() {
        return rankingId;
    }

    public void setRankingId(int rankingId) {
        this.rankingId = rankingId;
    }

    @Override
    public String toString() {
        return "ProductRank{" +
                "id=" + id +
                ", viewCount=" + viewCount +
                ", orderCount=" + orderCount +
                ", shares=" + shares +
                ", rankingId=" + rankingId +
                '}';
    }
}