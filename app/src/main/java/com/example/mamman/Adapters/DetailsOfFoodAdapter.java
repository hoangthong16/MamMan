package com.example.mamman.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsOfFoodAdapter extends RecyclerView.Adapter<DetailsOfFoodAdapter.DetailsOfFoodViewHolder> {

    private List<MonAnModel> monAnModelList;
    private Context context;


    public DetailsOfFoodAdapter(List<MonAnModel> monAnModelList, Context context) {
        this.monAnModelList = monAnModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailsOfFoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_vertical_slider, viewGroup, false);

        return new DetailsOfFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsOfFoodViewHolder holder, int position) {
        MonAnModel monAnModel = monAnModelList.get(position);
        Picasso.get().load(monAnModelList.get(position).link_hinh).into(holder.img);

        holder.ten.setText(monAnModel.getTenmonan());
        holder.gia.setText(String.valueOf(monAnModel.getDongia()));
    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }


    public static class DetailsOfFoodViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView ten, soluong, gia;

        public DetailsOfFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            img= (ImageView)itemView.findViewById(R.id.imageView);
            ten=(TextView)itemView.findViewById(R.id.textView4);
            soluong=(TextView)itemView.findViewById(R.id.textView5);
            gia=(TextView)itemView.findViewById(R.id.textView13);


        }
    }
}

