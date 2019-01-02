package com.ecom.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.ecom.entity.Product;
import com.ecom.repo.ProductsRepository;

import java.util.List;


public class ProductsViewModel extends AndroidViewModel {

    private ProductsRepository productsRepository;
    private LiveData<List<Product>> categoryproducts;
    public ProductsViewModel(Application application, int catId) {
        super(application);
        productsRepository = new ProductsRepository(application, catId);
        categoryproducts = productsRepository.fetchProductFromCategory();
    }

    public LiveData<List<Product>> getCategoryProducts() {
        return categoryproducts;
    }

}