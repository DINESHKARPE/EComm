package com.ecom.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "categoriesrelations")
public class CategoriesRelation {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;


    @NonNull
    @ColumnInfo(name = "mastercat")
    private Integer masterCat;

    @NonNull
    @ColumnInfo(name = "childcat")
    private int childCat;

    public CategoriesRelation(@NonNull Integer masterCat, @NonNull Integer childCat) {
        this.masterCat = masterCat;
        this.childCat = childCat;
    }

    @NonNull
    public Integer getMasterCat() {
        return masterCat;
    }

    @NonNull
    public Integer getChildCat() {
        return childCat;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }
}
