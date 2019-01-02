package com.ecom.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import com.ecom.dao.ProductDAO;
import com.ecom.db.ECommerceDB;
import com.ecom.entity.Product;

import java.util.List;

public class ProductsRepository {

    private ProductDAO productDAO;
    private LiveData<List<Product>> productList;

    public ProductsRepository(Application application,int catId) {
        ECommerceDB db = ECommerceDB.getDatabase(application);
        productDAO = db.productDAO();
        productList = productDAO.loadProductList(catId);
    }

    public LiveData<List<Product>> fetchProductFromCategory() {
        return productList;
    }


}
