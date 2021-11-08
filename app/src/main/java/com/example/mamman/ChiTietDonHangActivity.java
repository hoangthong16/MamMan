package com.example.mamman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mamman.Adapters.ChiTietDonHangAdapter;
import com.example.mamman.Model.ChiTietDonHang;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangActivity extends AppCompatActivity {
    RecyclerView recyclerViewchitietdonhang;
    static TextView tongtien,madonhang;
    private String madh;
    ImageButton btback;
    private ChiTietDonHangAdapter chiTietDonHangAdapter;

    DatabaseReference databaseReferencechitiet;

    public static List<ChiTietDonHang> chiTietDonHangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        recyclerViewchitietdonhang = (RecyclerView) findViewById(R.id.recyclerViewchitietdonhang);
        tongtien=(TextView)findViewById(R.id.tongtien);
        btback=(ImageButton)findViewById(R.id.btback);
        madonhang=(TextView)findViewById(R.id.madonhang);
        databaseReferencechitiet= FirebaseDatabase.getInstance().getReference("ChiTietDonHang");

        madh= getIntent().getStringExtra("madonhang");
        madonhang.setText(madh);

        LinearLayoutManager layoutDonHang = new LinearLayoutManager(this);
        layoutDonHang.setOrientation(RecyclerView.VERTICAL);
        recyclerViewchitietdonhang.setLayoutManager(layoutDonHang);

        chiTietDonHangList = new ArrayList<>();

        chiTietDonHangAdapter = new ChiTietDonHangAdapter(chiTietDonHangList,ChiTietDonHangActivity.this);
        recyclerViewchitietdonhang.setAdapter(chiTietDonHangAdapter);

        databaseReferencechitiet.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChiTietDonHang chiTiet= snapshot.getValue(ChiTietDonHang.class);
                String key=snapshot.getKey();
                if(chiTiet.getMadonhang().equals(madh)){
                    chiTietDonHangList.add(new ChiTietDonHang(key,chiTiet.getMadonhang(),chiTiet.getIdsp(),chiTiet.getTenmonan(),chiTiet.getDongia(),chiTiet.getSoluong(),chiTiet.getLink_hinh()));
                    chiTietDonHangAdapter.notifyDataSetChanged();
                    TongTien();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //madonhang,hinhmonan,tenmonan,soluong,dongia(thanhtien,tongtien)
    }
    public void TongTien(){
        float tonggiatien=0;
        for (int i=0;i<ChiTietDonHangActivity.chiTietDonHangList.size();i++){
            tonggiatien+=ChiTietDonHangActivity.chiTietDonHangList.get(i).getDongia() * ChiTietDonHangActivity.chiTietDonHangList.get(i).getSoluong();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tonggiatien)+ " Đ");
    }
}