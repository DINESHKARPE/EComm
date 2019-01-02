package com.ecom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecom.R;
import com.ecom.entity.Product;
import java.util.List;

public class ProductsViewAdapter extends RecyclerView.Adapter<ProductsViewAdapter.RecyclerViewHolder> {

    private List<Product> productList;
    private View.OnClickListener onClickListener;

    public ProductsViewAdapter(List<Product> productList, View.OnClickListener onClickListener) {
        this.productList = productList;
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.name.setText(product.getProductName());
        holder.itemView.setTag(product);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        RecyclerViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.categories_name);
        }
    }

    public void addItems(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }
}
