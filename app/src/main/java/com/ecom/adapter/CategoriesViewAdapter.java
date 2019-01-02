package com.ecom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecom.R;
import com.ecom.entity.Categories;

import java.util.List;

public class CategoriesViewAdapter extends RecyclerView.Adapter<CategoriesViewAdapter.RecyclerViewHolder> {

    private List<Categories> categoriesList;
    private View.OnClickListener onClickListener;

    public CategoriesViewAdapter(List<Categories> borrowModelList, View.OnClickListener onClickListener) {
        this.categoriesList = borrowModelList;
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        Categories categories = categoriesList.get(position);
        holder.name.setText(categories.getName());
        holder.itemView.setTag(categories);
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        RecyclerViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.categories_name);
        }
    }

    public void addItems(List<Categories> categories) {
        this.categoriesList = categories;
        notifyDataSetChanged();
    }
}
