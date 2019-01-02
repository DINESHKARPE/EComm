package com.ecom.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "categories")
public class Categories {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Integer catId;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;


    @NonNull
    @ColumnInfo(name = "products")
    private boolean products;

    public Categories(@NonNull Integer catId,@NonNull String name,@NonNull boolean products) {
        this.catId = catId;
        this.name = name;
        this.products = products;
    }


    @NonNull
    public Integer getCatId() {
        return catId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public boolean isProducts() {
        return products;
    }
}
