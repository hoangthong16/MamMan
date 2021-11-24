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
import com.example.mamman.Model.DonHangModel;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder>  {

    private List<DonHangModel> donHangModelList;
    private Context context;
    private static RecyclerViewClickInterface recyclerViewClickInterface;

    public DonHangAdapter(List<DonHangModel> donHangModelList, Context context, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.donHangModelList = donHangModelList;
        this.context = context;
        this.recyclerViewClickInterface =recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public DonHangAdapter.DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dongdonhang,viewGroup,false);

        return  new DonHangAdapter.DonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.DonHangViewHolder holder, int position) {
        DonHangModel donHangModel= donHangModelList.get(position);
        holder.madonang.setText(donHangModel.getMadonhang());
        if(donHangModel.getTrangthai().equals("thanhcong")){
            holder.trangthai.setText("Hoàn thành");
        }else {
            holder.trangthai.setText("Huỷ");
        }
        //holder.trangthai.setText(donHangModel.getTrangthai());
    }

    @Override
    public int getItemCount() {
        return donHangModelList.size();
    }


    public static class DonHangViewHolder extends RecyclerView.ViewHolder {

        private TextView trangthai,madonang;
        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);

            trangthai= (TextView) itemView.findViewById(R.id.trangthai);
            madonang= (TextView) itemView.findViewById(R.id.madonhang);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });



        }
    }

}
