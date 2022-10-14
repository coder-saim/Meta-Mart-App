package com.example.votenow.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.votenow.model.Category;
import com.example.votenow.R;
import com.example.votenow.databinding.ItemCategoriesBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategorViewHolder>{


    Context context;
    ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> categories){
        this.context  = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategorViewHolder(LayoutInflater.from(context).inflate(R.layout.item_categories,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategorViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.label.setText(category.getName());
        Glide.with(context).load(category.getIcon()).into(holder.binding.image); //loading image from web

        holder.binding.image.setBackgroundColor(Color.parseColor(category.getColor()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategorViewHolder extends RecyclerView.ViewHolder{

        ItemCategoriesBinding binding;

        public CategorViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCategoriesBinding.bind(itemView);
        }
    }
}
