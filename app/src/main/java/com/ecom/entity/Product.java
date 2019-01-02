package com.ecom.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "product")
public class Product {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer productId;


    @NonNull
    @ColumnInfo(name = "productname")
    private String productName;

    @NonNull
    @ColumnInfo(name = "catId")
    private Integer catId;




    public Product(@NonNull Integer productId, @NonNull String productName, @NonNull Integer catId) {
        this.productId = productId;
        this.productName = productName;
        this.catId = catId;
    }

    @NonNull
    public Integer getProductId() {
        return productId;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    @NonNull
    public Integer getCatId() {
        return catId;
    }
}
