package com.ecom.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class ECommModelFactory<VM extends ViewModel> implements ViewModelProvider.Factory {

    private Application mApplication;
    private int mParam;


    public ECommModelFactory(Application application, int param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return  (T) new CategoryViewModel(mApplication);
        }
        if (modelClass.isAssignableFrom(ProductsViewModel.class)) {
            return  (T) new ProductsViewModel(mApplication,mParam);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}
