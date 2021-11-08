package com.example.mamman.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamman.ChiTietDonHangActivity;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.ChiTietDonHang;
import com.example.mamman.Model.DonHangModel;
import com.example.mamman.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ChiTietDonHangViewHolder>  {

    private List<ChiTietDonHang> chiTietDonHangList;
    private Context context;

    public ChiTietDonHangAdapter(List<ChiTietDonHang> chiTietDonHangList, Context context) {
        this.chiTietDonHangList = chiTietDonHangList;
        this.context = context;
    }


    @NonNull
    @Override
    public ChiTietDonHangAdapter.ChiTietDonHangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dongchitietdonhang,viewGroup,false);

        return  new ChiTietDonHangAdapter.ChiTietDonHangViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChiTietDonHangAdapter.ChiTietDonHangViewHolder holder, int position) {
        ChiTietDonHang chiTietDonHang= chiTietDonHangList.get(position);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        Picasso.get().load(chiTietDonHangList.get(position).getLink_hinh()).into(holder.hinhsp);
        holder.tvtenmonan.setText(chiTietDonHang.getTenmonan());
        holder.tvsoluong.setText(decimalFormat.format(chiTietDonHang.getSoluong()));
        holder.tvdongia.setText(decimalFormat.format(chiTietDonHang.getDongia()) +" Đ");

        float thanhtien= chiTietDonHang.getDongia()* chiTietDonHang.getSoluong();
        holder.tvthanhtien.setText(decimalFormat.format(thanhtien) +" Đ");

    }

    @Override
    public int getItemCount() {
        return chiTietDonHangList.size();
    }


    public static class ChiTietDonHangViewHolder extends RecyclerView.ViewHolder {

        private TextView tvtenmonan,tvsoluong,tvdongia,tvthanhtien;
        private ImageView hinhsp;
        public ChiTietDonHangViewHolder(@NonNull View itemView) {
            super(itemView);

            tvtenmonan= (TextView) itemView.findViewById(R.id.tvtenmonan);
            tvsoluong= (TextView) itemView.findViewById(R.id.tvsoluong);
            tvdongia= (TextView) itemView.findViewById(R.id.tvdongia);
            tvthanhtien= (TextView) itemView.findViewById(R.id.tvthanhtien);
            hinhsp= (ImageView) itemView.findViewById(R.id.hinhsp);




        }
    }

}

