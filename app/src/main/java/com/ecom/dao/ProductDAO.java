package com.ecom.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ecom.entity.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert
    void insert(List<Product> product);

    @Query("SELECT   id, productName, catId FROM product WHERE product.catId = :catid")
    LiveData<List<Product>> loadProductList(Integer catid);


    @Query("DELETE FROM product")
    void deleteAll();


}
