package com.example.mamman.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamman.HomeActivity;
import com.example.mamman.Interface.MonAnClickInterface;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class YeuThichAdapter extends RecyclerView.Adapter<YeuThichAdapter.MonAnViewHolder>{
    private List<MonAnModel> monAnModelList;
    private Context context;
    private static RecyclerViewClickInterface recyclerViewClickInterface;
    private static MonAnClickInterface monAnClickInterface;

    public YeuThichAdapter(List<MonAnModel> monAnModelList, Context context, RecyclerViewClickInterface recyclerViewClickInterface, MonAnClickInterface monAnClickInterface) {
        this.monAnModelList = monAnModelList;
        this.context = context;
        this.recyclerViewClickInterface =recyclerViewClickInterface;
        this.monAnClickInterface = monAnClickInterface;
    }

    @NonNull
    @Override
    public YeuThichAdapter.MonAnViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_vertical_slider,viewGroup,false);

        return  new YeuThichAdapter.MonAnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YeuThichAdapter.MonAnViewHolder holder, int position) {
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
        private TextView tenmonan, giamgia, dongia, danhgia, tv_soluong;
        private Button btn_cong, btn_tru;

        public MonAnViewHolder(@NonNull View itemView) {
            super(itemView);

            proImg = (ImageView) itemView.findViewById(R.id.hinhsp);
            tenmonan = (TextView) itemView.findViewById(R.id.tvtenmonan);
            giamgia = (TextView) itemView.findViewById(R.id.textView6);
            dongia = (TextView) itemView.findViewById(R.id.tvthanhtien);
            danhgia = (TextView) itemView.findViewById(R.id.textView8);
            btn_cong = (Button) itemView.findViewById(R.id.btn_cong);
            btn_tru = (Button) itemView.findViewById(R.id.btn_tru);
            tv_soluong = (TextView) itemView.findViewById(R.id.tv_soluong);

            btn_cong.setVisibility(View.INVISIBLE);


            btn_cong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_tru.setVisibility(View.VISIBLE);
                    tv_soluong.setVisibility(View.VISIBLE);
                    monAnClickInterface.onButtonclick(v.getId(), getAdapterPosition());
                }
            });

            btn_tru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monAnClickInterface.onButtonclick(v.getId(), getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });


        }
    }
}
