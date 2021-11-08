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

import java.text.DecimalFormat;
import java.util.List;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.MonAnViewHolder>  {

    private List<MonAnModel> monAnModelList;
    private Context context;
    private static RecyclerViewClickInterface recyclerViewClickInterface;

    public MonAnAdapter(List<MonAnModel> monAnModelList, Context context, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.monAnModelList = monAnModelList;
        this.context = context;
        this.recyclerViewClickInterface =recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public MonAnViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_vertical_slider,viewGroup,false);

        return  new MonAnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonAnViewHolder holder, int position) {
        MonAnModel monAnModel = monAnModelList.get(position);
        Picasso.get().load(monAnModelList.get(position).link_hinh).into(holder.proImg);
        //Glide.with(context).load(simpleVerticalModel.getPro_img()).into(holder.proImg);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        DecimalFormat giagiatheophantram= new DecimalFormat("###");
        DecimalFormat danhgiaformat= new DecimalFormat("#.#");
        holder.tenmonan.setText(monAnModel.getTenmonan());
        holder.giamgia.setText(giagiatheophantram.format(monAnModel.getGiamgia()*100)+" %");
        holder.dongia.setText(decimalFormat.format(monAnModel.getDongia()) +" Đ");
        holder.danhgia.setText(danhgiaformat.format(monAnModel.getDanhgia()));
    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }


    public static class MonAnViewHolder extends RecyclerView.ViewHolder {

        private ImageView proImg;
        private TextView tenmonan,giamgia,dongia,danhgia;
        public MonAnViewHolder(@NonNull View itemView) {
            super(itemView);

            proImg=(ImageView) itemView.findViewById(R.id.hinhsp);
            tenmonan = (TextView) itemView.findViewById(R.id.tvtenmonan);
            giamgia = (TextView) itemView.findViewById(R.id.textView6);
            dongia = (TextView) itemView.findViewById(R.id.tvthanhtien);
            danhgia = (TextView) itemView.findViewById(R.id.textView8);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });



        }
    }
}