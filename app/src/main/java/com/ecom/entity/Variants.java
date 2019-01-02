package com.ecom.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "variants")
public class Variants {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer variantsId;

    @NonNull
    @ColumnInfo(name = "color")
    private String variantsColor;

    @ColumnInfo(name = "size")
    private String variantsSize;

    @NonNull
    @ColumnInfo(name = "price")
    private Integer variantsPrice;


    @NonNull
    @ColumnInfo(name = "productid")
    private Integer productId;

    public Variants(@NonNull Integer variantsId, @NonNull String variantsColor,  String variantsSize, @NonNull Integer variantsPrice, @NonNull Integer productId) {
        this.variantsId = variantsId;
        this.variantsColor = variantsColor;
        this.variantsSize = variantsSize;
        this.variantsPrice = variantsPrice;
        this.productId = productId;
    }

    @NonNull
    public Integer getVariantsId() {
        return variantsId;
    }

    @NonNull
    public String getVariantsColor() {
        return variantsColor;
    }

    public String getVariantsSize() {
        return variantsSize;
    }

    @NonNull
    public Integer getVariantsPrice() {
        return variantsPrice;
    }

    @NonNull
    public Integer getProductId() {
        return productId;
    }
}
