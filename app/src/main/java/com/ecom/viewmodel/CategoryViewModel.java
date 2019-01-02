package com.ecom.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;


import com.ecom.entity.Categories;
import com.ecom.repo.CategoriesRepository;

import java.util.List;


public class CategoryViewModel extends AndroidViewModel {

    private CategoriesRepository categoriesRepository;
    private LiveData<List<Categories>> categories;


    public CategoryViewModel(Application application) {
        super(application);
        categoriesRepository = new CategoriesRepository(application);
        categories = categoriesRepository.getAllCategories();
    }


    public LiveData<List<Categories>> getCategorires(int i) {
        if(i>0){
           return categoriesRepository.fetchSubCategories(i);
        }else {

            return categories;
        }
    }

}