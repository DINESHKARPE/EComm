package com.ecom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ecom.entity.Variants;

import java.util.List;

@Dao
public interface VariantsDAO {


    @Query("SELECT * FROM variants WHERE productId= :productID")
    LiveData<List<Variants>> loadVariants(int productID);



    @Insert
    void insert(List<Variants> variants);

    @Query("DELETE FROM variants")
    void deleteAll();


}
