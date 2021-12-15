package com.example.mamman.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mamman.Model.DanhGia;
import com.example.mamman.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.DanhGiaAdapterViewHolder> {
    private List<DanhGia> danhGiaList;
    private Context context;

    public DanhGiaAdapter(List<DanhGia> danhGiaList, Context context) {
        this.danhGiaList = danhGiaList;
        this.context = context;
    }

    @NonNull
    @Override
    public DanhGiaAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_danhgia,viewGroup,false);
        return new DanhGiaAdapterViewHolder(view);
    }

    private String getDate(long time){
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time*1000);
        Date d = cal.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return simpleDateFormat.format(d);

    }

    @Override
    public void onBindViewHolder(@NonNull DanhGiaAdapterViewHolder holder, int position) {
        DanhGia danhGia = danhGiaList.get(position);
        holder.tv_tennguoidanhgia.setText(danhGia.getTen());
        holder.ratingBar.setRating(danhGia.getStar());
        holder.tv_noidung.setText(danhGia.getNoiDung());
        holder.tv_datetime.setText(getDate(danhGia.getTime()));
    }

    @Override
    public int getItemCount() {
        return danhGiaList.size();
    }


    public static class DanhGiaAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_tennguoidanhgia,tv_noidung,tv_datetime;
        private RatingBar ratingBar;

        public DanhGiaAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_tennguoidanhgia = (TextView) itemView.findViewById(R.id.tv_tennguoidanhgia);
            tv_noidung = (TextView) itemView.findViewById(R.id.tv_noidung);
            tv_datetime = (TextView) itemView.findViewById(R.id.tv_datetime);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

        }
    }
}
