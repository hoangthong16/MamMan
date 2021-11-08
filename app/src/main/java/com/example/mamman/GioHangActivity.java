package com.example.mamman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mamman.Adapters.BannerAdapter;
import com.example.mamman.Adapters.GioHangAdapter;
import com.example.mamman.Adapters.MonAnAdapter;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.ChiTietDonHang;
import com.example.mamman.Model.DonHangModel;
import com.example.mamman.Model.GioHang;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    RecyclerView recyclerViewGioHang;
    static TextView tongtien;
    TextView thongbao;
    Button thanhtoan,tieptucmua;
    ImageButton btback;

    GioHangAdapter gioHangAdapter;
    ProgressBar progressBar;

    DatabaseReference databaseReferenceDonHang,databaseReferenceChiTietDonHang;
    public String DonHangID;
    boolean xacnhan=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        AnhXa();
        progressBar.setVisibility(View.INVISIBLE);

        int leng=HomeActivity.listgiohang.size();
        Toast.makeText(GioHangActivity.this,String.valueOf(leng),Toast.LENGTH_LONG).show();
        CheckData();


        gioHangAdapter.notifyDataSetChanged();
        TongTien();
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(HomeActivity.listgiohang.size()>0){
                    if(HomeActivity.listkhachhang.size() >0){
                        if(HomeActivity.listkhachhang.get(0).getPhonenumber()==null || HomeActivity.listkhachhang.get(0).getAddress()== null){
                            Intent intent = new Intent(GioHangActivity.this,ThongTinTaiKhoanActivity.class);
                            startActivity(intent);
                        }else {
                            databaseReferenceDonHang = FirebaseDatabase.getInstance().getReference("DonHang");
                            databaseReferenceChiTietDonHang = FirebaseDatabase.getInstance().getReference("ChiTietDonHang");
                            DonHangModel donHangModel = new DonHangModel(null, HomeActivity.listkhachhang.get(0).getUid(), HomeActivity.listkhachhang.get(0).name, HomeActivity.listkhachhang.get(0).phonenumber, HomeActivity.listkhachhang.get(0).getAddress(), "dat hang");
                            databaseReferenceDonHang.push().setValue(donHangModel, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    DonHangID= ref.getKey();
                                    for (int i = 0; i < HomeActivity.listgiohang.size(); i++) {
                                        ChiTietDonHang chiTietDonHang = new ChiTietDonHang(null, DonHangID, HomeActivity.listgiohang.get(i).getIdsp(), HomeActivity.listgiohang.get(i).getTensp(), HomeActivity.listgiohang.get(i).giasp, HomeActivity.listgiohang.get(i).getSoluongsp(),HomeActivity.listgiohang.get(i).getHinhsp());
                                        databaseReferenceChiTietDonHang.push().setValue(chiTietDonHang);
                                        Toast.makeText(getApplicationContext(), "Đợi xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        xacnhan=true;
                                        xacnhandonhang(DonHangID);
                                    }
                                }
                            });

                        }
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Giỏ hàng của bạn đang trống",Toast.LENGTH_SHORT).show();
                }

            }
        });

        tieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHangActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    public static void TongTien(){
        float tonggiatien=0;
        for (int i=0;i<HomeActivity.listgiohang.size();i++){
            tonggiatien+=HomeActivity.listgiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tonggiatien)+ " Đ");
    }

    private void AnhXa() {
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        recyclerViewGioHang = (RecyclerView) findViewById(R.id.recyclerViewGioHang);
        thongbao=(TextView) findViewById(R.id.thongbao);
        tongtien=(TextView) findViewById(R.id.tongtien);
        thanhtoan=(Button) findViewById(R.id.thanhtoan);
        tieptucmua=(Button) findViewById(R.id.tieptucmua);
        btback=(ImageButton) findViewById(R.id.btback);
        LinearLayoutManager layoutManagerBanner = new LinearLayoutManager(this);
        layoutManagerBanner.setOrientation(RecyclerView.VERTICAL);
        recyclerViewGioHang.setLayoutManager(layoutManagerBanner);
        gioHangAdapter= new GioHangAdapter(HomeActivity.listgiohang,GioHangActivity.this,this);
        recyclerViewGioHang.setAdapter(gioHangAdapter);
    }

    private void CheckData(){
        if (HomeActivity.listgiohang.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            thongbao.setVisibility(View.VISIBLE);
            recyclerViewGioHang.setVisibility(View.INVISIBLE);
        }else {
            gioHangAdapter.notifyDataSetChanged();
            thongbao.setVisibility(View.INVISIBLE);
            recyclerViewGioHang.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onItemClick(int position) {
        //Toast.makeText(GioHangActivity.this,position+"",Toast.LENGTH_LONG).show();
        /*
        int soluongmoi= HomeActivity.listgiohang.get(position).getSoluongsp() +1;
        HomeActivity.listgiohang.get(position).setSoluongsp(soluongmoi);
        gioHangAdapter.notifyDataSetChanged();

         */

    }

    @Override
    public void onItemClickcat(int position) {

    }

    @Override
    public void onLongItemClick(int position) {
        Toast.makeText(GioHangActivity.this,position+" Long Click",Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder= new AlertDialog.Builder(GioHangActivity.this);
        builder.setTitle("Xác nhận xoá sản phẩm");
        builder.setMessage("Bạn có chắc chắn muốn xoá sản phẩm này ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (HomeActivity.listgiohang.size()<=0){
                    thongbao.setVisibility(View.VISIBLE);
                }else {
                    removeAt(position);
                    TongTien();
                    if (HomeActivity.listgiohang.size()<=0){
                        thongbao.setVisibility(View.VISIBLE);
                    }else {
                        thongbao.setVisibility(View.INVISIBLE);
                        gioHangAdapter.notifyDataSetChanged();
                        TongTien();
                    }
                }
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gioHangAdapter.notifyDataSetChanged();
                TongTien();
            }
        });
        builder.show();
    }
    public void removeAt(int position){
        HomeActivity.listgiohang.remove(position);
        gioHangAdapter.notifyItemRemoved(position);
        gioHangAdapter.notifyItemRangeChanged(position, HomeActivity.listgiohang.size());
    }

    public void xacnhandonhang(String iddonhang){
        databaseReferenceDonHang.child(iddonhang).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String trangthai=(String) snapshot.getValue();
                if (trangthai.equals("thanhcong")){
                    progressBar.setVisibility(View.INVISIBLE);
                    Thongbaothanhcong();
                    if (xacnhan){
                        HomeActivity.listgiohang.clear();
                    }


                }
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

    }

    public void Thongbaothanhcong() {
        /*
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent= new Intent(GioHangActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        }).setTitle(title).setMessage(message).show();

        builder.setPositiveButton("Huỷ đơn hàng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DonHangModel donHangModel = new DonHangModel(null, HomeActivity.listkhachhang.get(0).getUid(), HomeActivity.listkhachhang.get(0).name, HomeActivity.listkhachhang.get(0).phonenumber, HomeActivity.listkhachhang.get(0).getAddress(), "huy");
                databaseReferenceDonHang.child(DonHangID).setValue(donHangModel);
            }
        }).setTitle(title).setMessage(message).show();

         */
        AlertDialog.Builder builder= new AlertDialog.Builder(GioHangActivity.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Đặt hàng thành công,Quay lại Home");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent= new Intent(GioHangActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Huỷ đơn hàng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DonHangModel donHangModel = new DonHangModel(null, HomeActivity.listkhachhang.get(0).getUid(), HomeActivity.listkhachhang.get(0).name, HomeActivity.listkhachhang.get(0).phonenumber, HomeActivity.listkhachhang.get(0).getAddress(), "huy");
                databaseReferenceDonHang.child(DonHangID).setValue(donHangModel);
            }
        });
        builder.show();

    }

}