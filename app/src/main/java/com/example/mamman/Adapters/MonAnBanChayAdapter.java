package com.example.mamman.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.GreatOffersModel;
import com.example.mamman.Model.MonAnBanChayModel;
import com.example.mamman.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class MonAnBanChayAdapter extends RecyclerView.Adapter<MonAnBanChayAdapter.MonAnBanChayViewHolder> {

    List<MonAnBanChayModel> monAnBanChayModelList;
    Context context;
    private static RecyclerViewClickInterface recyclerViewClickInterface;

    public MonAnBanChayAdapter(List<MonAnBanChayModel> monAnBanChayModelList, Context context,RecyclerViewClickInterface recyclerViewClickInterface) {
        this.monAnBanChayModelList = monAnBanChayModelList;
        this.context = context;
        this.recyclerViewClickInterface=recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public MonAnBanChayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_horizontal_great_offers, viewGroup, false);

        return new MonAnBanChayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonAnBanChayViewHolder holder, int position) {
        MonAnBanChayModel monAnBanChayModel = monAnBanChayModelList.get(position);
        Picasso.get().load(monAnBanChayModelList.get(position).link_hinh).into(holder.Img);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        DecimalFormat giagiatheophantram= new DecimalFormat("###");
        DecimalFormat danhgiaformat= new DecimalFormat("#.#");
        holder.tenmonan.setText(monAnBanChayModel.getTenmonan());
        holder.dongia.setText(decimalFormat.format(monAnBanChayModel.getDongia()) +" Đ");
        holder.giamgia.setText(giagiatheophantram.format(monAnBanChayModel.getGiamgia() *100 )+" %");
        holder.danhgia.setText(danhgiaformat.format(monAnBanChayModel.getDanhgia()));
    }

    @Override
    public int getItemCount() {
        return monAnBanChayModelList.size();
    }


    public class MonAnBanChayViewHolder extends RecyclerView.ViewHolder {
        private ImageView Img;
        private TextView tenmonan, giamgia, dongia, danhgia;

        public MonAnBanChayViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = (ImageView) itemView.findViewById(R.id.imageView7);
            tenmonan = (TextView) itemView.findViewById(R.id.textView9);
            dongia = (TextView) itemView.findViewById(R.id.textView11);
            danhgia = (TextView) itemView.findViewById(R.id.textView12);
            giamgia = (TextView) itemView.findViewById(R.id.textView10);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
