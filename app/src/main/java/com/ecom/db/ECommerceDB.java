package com.ecom.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ecom.dao.CategoriesDAO;
import com.ecom.dao.CategoriesRelationDAO;
import com.ecom.dao.ProductDAO;
import com.ecom.dao.VariantsDAO;
import com.ecom.entity.Categories;
import com.ecom.entity.CategoriesRelation;
import com.ecom.entity.Product;
import com.ecom.entity.Variants;

@Database(entities = {Categories.class, CategoriesRelation.class, Product.class, Variants.class}, version = 1,exportSchema = false)
public abstract class ECommerceDB extends RoomDatabase {

    private static ECommerceDB eCommerceDataBase;

    public abstract CategoriesDAO categoriesDAO();

    public abstract CategoriesRelationDAO categoriesRelationDAO();

    public abstract ProductDAO productDAO();

    public abstract VariantsDAO variantsDAO();

    public static ECommerceDB getDatabase(Context context) {
        if (eCommerceDataBase == null) {
            eCommerceDataBase =
                    Room.databaseBuilder(context, ECommerceDB.class, "ECommerce_DB")
                            .allowMainThreadQueries()
                            .build();
        }
        return eCommerceDataBase;
    }

    public static void destroyInstance() {
        eCommerceDataBase = null;
    }
}
