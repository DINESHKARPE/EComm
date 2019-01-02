package com.ecom.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.ecom.dao.CategoriesDAO;
import com.ecom.db.ECommerceDB;
import com.ecom.entity.Categories;

import java.util.List;

public class CategoriesRepository {

    private CategoriesDAO categoriesDAO;
    private LiveData<List<Categories>> categoriesList;

    public CategoriesRepository(Application application) {
        ECommerceDB db = ECommerceDB.getDatabase(application);
        categoriesDAO = db.categoriesDAO();
        categoriesList = categoriesDAO.fetchMasterCategories();
    }

    public LiveData<List<Categories>> getAllCategories() {
        return categoriesList;
    }

    public LiveData<List<Categories>> fetchSubCategories(int i){
        return  categoriesDAO.fetchSubCategories(i);
    }

}
