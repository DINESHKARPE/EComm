package com.ecom.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ecom.MainActivity;
import com.ecom.R;
import com.ecom.adapter.CategoriesViewAdapter;
import com.ecom.adapter.ProductsViewAdapter;
import com.ecom.db.ECommerceDB;
import com.ecom.entity.Categories;
import com.ecom.entity.Product;
import com.ecom.event.OnFragmentInteractionListener;
import com.ecom.viewmodel.CategoryViewModel;
import com.ecom.viewmodel.ECommModelFactory;
import com.ecom.viewmodel.ProductsViewModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    private ECommerceDB eCommerceDB;

    private CategoryViewModel categoryViewModel;
    private ProductsViewModel productsViewModel;
    private Fragment currentFragment;
    private CategoriesViewAdapter recyclerViewAdapter;
    private ProductsViewAdapter productsViewAdapter;
    private RecyclerView recyclerView;
    private int lastCate;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance(int subcategory, boolean productsAvilable) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putInt("subcat",subcategory);
        args.putBoolean("productsAvilable",productsAvilable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eCommerceDB = (ECommerceDB) ECommerceDB.getDatabase(getActivity());
        categoryViewModel = ViewModelProviders.of(this,new ECommModelFactory(getActivity().getApplication(), getArguments().getInt("subcat"))).get(CategoryViewModel.class);

        productsViewModel = ViewModelProviders.of(this,new ECommModelFactory(getActivity().getApplication(), getArguments().getInt("subcat"))).get(ProductsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  =inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = rootView.findViewById(R.id.categories);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if(getArguments().getInt("subcat") > 0){

            ((MainActivity) getActivity()).setToolbar(true);
        }else {
            ((MainActivity) getActivity()).setToolbar(false);
        }

        recyclerViewAdapter = new CategoriesViewAdapter(new ArrayList<Categories>(), this);

        productsViewAdapter = new ProductsViewAdapter(new ArrayList<Product>(), this);
        if(getArguments().getBoolean("productsAvilable")){

            productsViewModel.getCategoryProducts().observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(@Nullable List<Product> productList) {
                    productsViewAdapter.addItems(productList);
                }
            });

            recyclerView.setAdapter(productsViewAdapter);
        }else{

            categoryViewModel.getCategorires(getArguments().getInt("subcat")).observe(this, new Observer<List<Categories>>() {

                @Override
                public void onChanged(@Nullable List<Categories> categories) {
                    if(categories.size() > 0){
                        recyclerViewAdapter.addItems(categories);
                    }
                }
            });

            recyclerView.setAdapter(recyclerViewAdapter);
        }
        lastCate = getArguments().getInt("subcat");


        ((MainActivity)getActivity()).getToolbar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject viewObject = new JsonObject();
                viewObject.addProperty("VIEW","");
                viewObject.addProperty("SUBCATEGORY",0);
                viewObject.addProperty("ISPRODUCTS",false);
                mListener.onFragmentInteraction(viewObject);
            }
        });
        return rootView;
    }

    private void loadSelectedCategoryProducts(Integer catId) {


        productsViewModel.getCategoryProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> productList) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

        if(view.getTag().getClass().getName().equals("com.ecom.entity.Categories")){

            Categories categories = (Categories) view.getTag();
            JsonObject viewObject = new JsonObject();
            viewObject.addProperty("VIEW","PRODUCT_SUBCATEGORY");
            viewObject.addProperty("SUBCATEGORY",categories.getCatId());
            viewObject.addProperty("ISPRODUCTS",categories.isProducts());
            mListener.onFragmentInteraction(viewObject);
        }
    }

}
