package com.example.mamman.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamman.Fragments.GioHangFragment;
import com.example.mamman.Fragments.OrdersFragment;
import com.example.mamman.HomeActivity;
import com.example.mamman.Interface.MonAnClickInterface;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.GioHang;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.MonAnViewHolder>  {

    private List<MonAnModel> monAnModelList;
    private Context context;
    private static RecyclerViewClickInterface recyclerViewClickInterface;
    private static MonAnClickInterface monAnClickInterface;



    public MonAnAdapter(List<MonAnModel> monAnModelList, Context context, RecyclerViewClickInterface recyclerViewClickInterface, MonAnClickInterface monAnClickInterface) {
        this.monAnModelList = monAnModelList;
        this.context = context;
        this.recyclerViewClickInterface =recyclerViewClickInterface;
        this.monAnClickInterface = monAnClickInterface;
    }

    @NonNull
    @Override
    public MonAnViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_vertical_slider,viewGroup,false);

        return new MonAnViewHolder(view);
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
        holder.btn_tru.setVisibility(View.INVISIBLE);
        holder.tv_soluong.setVisibility(View.INVISIBLE);
        if(HomeActivity.listgiohang !=null){
            for(int i = 0; i<HomeActivity.listgiohang.size();i++) {
                if (HomeActivity.listgiohang.get(i).getIdsp() == HomeActivity.monAnModelList.get(position).getMamonan()) {

                    int sl = HomeActivity.listgiohang.get(i).getSoluongsp();
                    holder.tv_soluong.setVisibility(View.VISIBLE);
                    holder.btn_tru.setVisibility(View.VISIBLE);

                    holder.tv_soluong.setText(sl + "");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }


    public static class MonAnViewHolder extends RecyclerView.ViewHolder {

        private ImageView proImg;
        private TextView tenmonan,giamgia,dongia,danhgia, tv_soluong;
        private Button btn_cong, btn_tru;
        public MonAnViewHolder(@NonNull View itemView) {
            super(itemView);

            proImg=(ImageView) itemView.findViewById(R.id.hinhsp);
            tenmonan = (TextView) itemView.findViewById(R.id.tvtenmonan);
            giamgia = (TextView) itemView.findViewById(R.id.textView6);
            dongia = (TextView) itemView.findViewById(R.id.tvthanhtien);
            danhgia = (TextView) itemView.findViewById(R.id.textView8);
            btn_cong = (Button) itemView.findViewById(R.id.btn_cong);
            btn_tru = (Button) itemView.findViewById(R.id.btn_tru);
            tv_soluong = (TextView) itemView.findViewById(R.id.tv_soluong);




            btn_cong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_tru.setVisibility(View.VISIBLE);
                    tv_soluong.setVisibility(View.VISIBLE);
                    int sl = 0;
                    boolean daco = false;
                    if(HomeActivity.listgiohang != null){
                        for(int i = 0; i<HomeActivity.listgiohang.size();i++){
                            if(HomeActivity.listgiohang.get(i).getIdsp() == HomeActivity.monAnModelList.get(getAdapterPosition()).getMamonan()) {
                                sl = HomeActivity.listgiohang.get(i).getSoluongsp() +1;
                                tv_soluong.setText(sl + "");
                                daco = true;
                            }
                        }
                        if(!daco){
                            sl=1;
                            tv_soluong.setText(sl+"");
                        }
                    }else {
                        sl=1;
                        tv_soluong.setText(sl+"");
                    }

                    monAnClickInterface.onButtonclick(v.getId(),getAdapterPosition());
                }
            });

            btn_tru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int sl =0;

                    if(HomeActivity.listgiohang != null){
                        for(int i = 0; i<HomeActivity.listgiohang.size();i++){
                            if(HomeActivity.listgiohang.get(i).getIdsp() == HomeActivity.monAnModelList.get(getAdapterPosition()).getMamonan()) {
                                sl = HomeActivity.listgiohang.get(i).getSoluongsp();
                                sl=sl-1;
                                if(sl>0){
                                    tv_soluong.setText(sl + "");
                                }else {

                                    btn_tru.setVisibility(View.INVISIBLE);
                                    tv_soluong.setVisibility(View.INVISIBLE);

                                }

                                Log.e("MonAnAdapter","daco");
                            }
                        }
                    }

                    monAnClickInterface.onButtonclick(v.getId(),getAdapterPosition());
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