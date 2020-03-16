package com.heady.ecommerce.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProductVariants {

    @Embedded
    public Product product;
    @Relation(entity = Variant.class, parentColumn = "id", entityColumn = "product_id")
    public List<Variant> variants;

    @Override
    public String toString() {
        return "ProductVariants{" +
                "product=" + product +
                ", variants=" + variants +
                '}';
    }
}
