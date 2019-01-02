package com.ecom.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ecom.entity.CategoriesRelation;

import java.util.List;

@Dao
public interface CategoriesRelationDAO {

    @Insert
    void insertCategoriesRelation(CategoriesRelation categoriesRelation);


    @Query("DELETE FROM categoriesrelations")
    void deleteAll();

    @Insert
    void insertCategoriesRelation(List<CategoriesRelation> categoriesRelationList);
}
