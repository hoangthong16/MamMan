package com.example.mamman.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamman.HomeActivity;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.CategoryModel;
import com.example.mamman.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CategoryViewHolder> {

    private List<CategoryModel> categoryModelList;
    private Context context;
    private static RecyclerViewClickInterface recyclerViewClickInterface;



        public CatAdapter(List<CategoryModel> categoryModelList, Context context,RecyclerViewClickInterface recyclerViewClickInterface) {
            this.categoryModelList = categoryModelList;
            this.context = context;
            this.recyclerViewClickInterface= recyclerViewClickInterface;
        }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category,viewGroup,false);

        return  new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel categoryModel = categoryModelList.get(position);
        holder.cat_title.setText(categoryModel.getCat_title());
        //Glide.with(context).load(categoryModel.getCat_ing()).placeholder(R.drawable.category_test).into(holder.cat_img);
        Picasso.get().load(categoryModelList.get(position).link_hinh).into(holder.cat_img);

    }


    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView cat_img;
        private TextView cat_title;
        View v;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            cat_img=(ImageView) itemView.findViewById(R.id.imageView5);
            cat_title=(TextView) itemView.findViewById(R.id.textView2);
            v=itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClickcat(getAdapterPosition());
                }
            });
        }
    }
}
