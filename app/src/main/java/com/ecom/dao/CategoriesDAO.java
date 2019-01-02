package com.ecom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ecom.entity.Categories;

import java.util.List;

@Dao
public interface CategoriesDAO {

    @Query("SELECT DISTINCT(categories.id),categories.name,categories.products FROM categoriesrelations relation, categories categories   WHERE categories.id = relation.mastercat and relation.mastercat NOT IN (SELECT childcat FROM categoriesrelations)")
    LiveData<List<Categories>> fetchMasterCategories();

    @Insert
    void insert(List<Categories> categories);

    @Ignore
    @Query("SELECT categories.id ,categories.name,categories.products FROM categories categories,categoriesrelations categoriesrelations WHERE categories.id = categoriesrelations.childcat and categoriesrelations.masterCat = :catid")
    LiveData<List<Categories>> fetchSubCategories(int catid);



    @Query("DELETE FROM categories")
    void deleteAll();

}
