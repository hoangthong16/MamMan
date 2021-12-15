package com.example.mamman.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamman.GioHangActivity;
import com.example.mamman.HomeActivity;
import com.example.mamman.Interface.MonAnClickInterface;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.GioHang;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder>  {

    private List<GioHang> gioHangList;
    private Context context;
    private static MonAnClickInterface monAnClickInterface;
    private static RecyclerViewClickInterface recyclerViewClickInterface;


    public GioHangAdapter(List<GioHang> gioHangList, Context context,RecyclerViewClickInterface recyclerViewClickInterface, MonAnClickInterface monAnClickInterface) {
        this.gioHangList = gioHangList;
        this.context = context;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
        this.monAnClickInterface=monAnClickInterface;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_giohang,viewGroup,false);

        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        Picasso.get().load(gioHangList.get(position).hinhsp).into(holder.Img);
        //Glide.with(context).load(simpleVerticalModel.getPro_img()).into(holder.proImg);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        holder.ten.setText(gioHang.getTensp());
        holder.gia.setText(decimalFormat.format(gioHang.getGiasp())+" Đ");
        holder.soluong.setText(String.valueOf(gioHang.getSoluongsp()));

        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int sl = HomeActivity.listgiohang.get(position).getSoluongsp();
                sl=sl-1;
                if (sl>0){
                    HomeActivity.listgiohang.get(position).setSoluongsp(sl);
                    holder.soluong.setText(HomeActivity.listgiohang.get(position).getSoluongsp()+"");
                    holder.gia.setText(decimalFormat.format(HomeActivity.listgiohang.get(position).getGiasp() * HomeActivity.listgiohang.get(position).getSoluongsp())+ " Đ");
                    GioHangActivity.TongTien();
                }else {
                    monAnClickInterface.onButtonclick(v.getId(),position);
                }



            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int sl = HomeActivity.listgiohang.get(position).getSoluongsp();
                sl=sl+1;
                HomeActivity.listgiohang.get(position).setSoluongsp(sl);

                holder.soluong.setText(HomeActivity.listgiohang.get(position).getSoluongsp()+"");
                holder.gia.setText(decimalFormat.format(HomeActivity.listgiohang.get(position).getGiasp() * HomeActivity.listgiohang.get(position).getSoluongsp())+ " Đ");
                GioHangActivity.TongTien();

                monAnClickInterface.onButtonclick(v.getId(),position);




                /*
                int slmoinhat= Integer.parseInt(holder.soluong.getText().toString()) + 1;
                int slht = HomeActivity.listgiohang.get(position).getSoluongsp();
                float giaht= HomeActivity.listgiohang.get(position).getGiasp();
                HomeActivity.listgiohang.get(position).setSoluongsp(slmoinhat);
                float giamoinhat= (giaht *slmoinhat)/slht;
                HomeActivity.listgiohang.get(position).setGiasp(giamoinhat);
                holder.gia.setText(decimalFormat.format(giamoinhat)+ " Đ");
                GioHangActivity.TongTien();
                holder.soluong.setText(String.valueOf(slmoinhat));

                 */
            }
        });

    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public static class GioHangViewHolder extends RecyclerView.ViewHolder {

        private ImageView Img;
        private TextView ten,gia,soluong;
        private Button sub,add;
        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);

            Img=(ImageView) itemView.findViewById(R.id.anhsp);
            ten = (TextView) itemView.findViewById(R.id.tensp);
            gia = (TextView) itemView.findViewById(R.id.giasp);
            soluong = (TextView) itemView.findViewById(R.id.soluong);
            sub=(Button) itemView.findViewById(R.id.sub);
            add= (Button) itemView.findViewById(R.id.add);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });




        }

    }

}
