package com.example.mamman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mamman.Adapters.BannerAdapter;
import com.example.mamman.Adapters.DanhGiaAdapter;
import com.example.mamman.Adapters.DetailsOfFoodAdapter;
import com.example.mamman.Adapters.MonAnAdapter;
import com.example.mamman.Fragments.OrdersFragment;
import com.example.mamman.Model.DanhGia;
import com.example.mamman.Model.GioHang;
import com.example.mamman.Model.MonAnBanChayModel;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.Model.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.security.AccessController.getContext;

public class DetailsOfFoodActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton back,yeuthich;
    ImageView img;
    Button sub,add,themvaogiohang;
    TextView ten,soluong,gia,tv_vietdanhgia;
    ConstraintLayout details;
    private int so=0;
    private int position;
    String foodId="";
    private String shinh,sten,skey,scate;
    private float sgia,sgiamgia,sdanhgia;
    private int sposition;
    private boolean syeuthich;
    DatabaseReference databaseReference,databaseReferenceDanhGia;
    RecyclerView recyclerViewDanhGia;

    //item bottom_sheet_vietdanhgia
    BottomSheetDialog bottomSheetDialog;
    Button btn_huy, btn_gui;
    EditText noidung;
    RatingBar ratingBarDanhGia;


    private DetailsOfFoodAdapter detailsOfFoodAdapter;
    private List<MonAnModel> monAnModelList;
    private List<DanhGia> danhGiaList;
    private DanhGiaAdapter danhGiaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_food);
        back = (ImageButton)findViewById(R.id.imageButton);
        yeuthich = (ImageButton)findViewById(R.id.favourite);
        img= (ImageView)findViewById(R.id.imageView);
        sub=(Button)findViewById(R.id.button4);
        add=(Button)findViewById(R.id.button3);
        ten=(TextView)findViewById(R.id.textView4);
        soluong=(TextView)findViewById(R.id.textView5);
        gia=(TextView)findViewById(R.id.textView13);
        themvaogiohang=(Button)findViewById(R.id.themvaogiohang);
        recyclerViewDanhGia= (RecyclerView)findViewById(R.id.recyclerViewDanhGia);
        tv_vietdanhgia = (TextView)findViewById(R.id.tv_vietdanhgia);




        databaseReferenceDanhGia= FirebaseDatabase.getInstance().getReference("DanhGia");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewDanhGia.setLayoutManager(layoutManager);

        danhGiaList = new ArrayList<>();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewDanhGia.getContext(),layoutManager.getOrientation());
        recyclerViewDanhGia.addItemDecoration(dividerItemDecoration);

        danhGiaAdapter = new DanhGiaAdapter(danhGiaList,this);
        recyclerViewDanhGia.setAdapter(danhGiaAdapter);


        position = getIntent().getIntExtra("position",-1);
        /*
        shinh =getIntent().getStringExtra("hinhanh");
        sten =getIntent().getStringExtra("ten");
        sgia =getIntent().getFloatExtra("gia",0);
        skey= getIntent().getStringExtra("key");
        scate= getIntent().getStringExtra("cate");
        syeuthich= getIntent().getBooleanExtra("yeuthich",false);
        sgiamgia=getIntent().getFloatExtra("giamgia",0);
        sdanhgia=getIntent().getFloatExtra("danhgia",0);
        Toast.makeText(DetailsOfFoodActivity.this,skey,Toast.LENGTH_LONG).show();

         */

        shinh =HomeActivity.monAnModelList.get(position).getLink_hinh();
        sten =HomeActivity.monAnModelList.get(position).getTenmonan();
        sgia =HomeActivity.monAnModelList.get(position).getDongia();
        skey= HomeActivity.monAnModelList.get(position).getMamonan();
        scate= HomeActivity.monAnModelList.get(position).getCategory();
        syeuthich= HomeActivity.monAnModelList.get(position).getYeuthich();
        sgiamgia= HomeActivity.monAnModelList.get(position).getGiamgia();
        sdanhgia= HomeActivity.monAnModelList.get(position).getDanhgia();


        for(int i =0; i<HomeActivity.listgiohang.size();i++){
            if(HomeActivity.listgiohang.get(i).getIdsp() == skey){
                so = HomeActivity.listgiohang.get(i).getSoluongsp();
            }
        }


        ten.setText(sten);
        Picasso.get().load(shinh).into(img);
        soluong.setText(so+"");

        gia.setText(sgia+"");
        sub.setBackgroundResource(R.drawable.sub_gray);
        if (syeuthich){
            yeuthich.setBackgroundResource(R.drawable.favorite_true);
        }else {
            yeuthich.setBackgroundResource(R.drawable.favorite);
        }

        /*
        ten.setText(HomeActivity.monAnModelList.get(position).getTenmonan());
        Picasso.get().load(HomeActivity.monAnModelList.get(position).getLink_hinh()).into(img);
        soluong.setText(so);
        gia.setText(HomeActivity.monAnModelList.get(position).getDongia()+"");
        sub.setBackgroundResource(R.drawable.sub_gray);
        if (HomeActivity.monAnModelList.get(position).getYeuthich()){
            yeuthich.setBackgroundResource(R.drawable.favorite_true);
        }else {
            yeuthich.setBackgroundResource(R.drawable.favorite);
        }

         */


        back.setOnClickListener(this);
        sub.setOnClickListener(this);
        add.setOnClickListener(this);
        themvaogiohang.setOnClickListener(this);
        yeuthich.setOnClickListener(this);
        tv_vietdanhgia.setOnClickListener(this);

        loadDanhGia();





    }
    /*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("khoa",skey);
        outState.putString("tenmonan",sten);
        outState.putInt("soluong",so);
        outState.putInt("thutu",sposition);
        outState.putString("linkanh",shinh);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        so=savedInstanceState.getInt("soluong",-1);
        sgia=savedInstanceState.getFloat("tongtien",-1);

    }


     */

    private void loadDanhGia(){
        Query query = databaseReferenceDanhGia.orderByChild("id_MonAn").equalTo(skey);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChildren()){
                    danhGiaList.clear();
                    for (DataSnapshot dss: snapshot.getChildren()){
                        final DanhGia danhgia = dss.getValue(DanhGia.class);
                        danhGiaList.add(new DanhGia(dss.getKey(),danhgia.getId_User(),danhgia.getId_MonAn(),danhgia.getTen(),danhgia.getNoiDung(),danhgia.getStar(),danhgia.getTime()));
                        danhGiaAdapter.notifyDataSetChanged();

                    }
                }

                /*
                if(snapshot.hasChildren()){
                    monAnModelList.clear();
                    for (DataSnapshot dss: snapshot.getChildren()){
                        final MonAnModel monAnModel= dss.getValue(MonAnModel.class);
                        monAnModelList.add(monAnModel);
                        if(dss.getKey() != null){
                            key.add(dss.getKey());
                        }
                    }

                    MonAnAdapter monAnAdapter=new MonAnAdapter(monAnModelList,getApplicationContext(),SearchActivity.this, SearchActivity.this);
                    recyclerViewSearch.setAdapter(monAnAdapter);
                    monAnAdapter.notifyDataSetChanged();

                }
                 */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*
        databaseReferenceDanhGia.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String key = snapshot.getKey();
                DanhGia danhgia = snapshot.getValue(DanhGia.class);

                danhGiaList.add(new DanhGia(key,danhgia.getId_User(),danhgia.getId_MonAn(),danhgia.getTen(),danhgia.getNoiDung(),danhgia.getStar(),danhgia.getTime()));
                danhGiaAdapter.notifyDataSetChanged();


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

         */
    }

    private void vietDanhGia(){
        View viewDiaLog = getLayoutInflater().inflate(R.layout.bottom_sheet_vietdanhgia,null);

        //bottom_sheet_vietdanhgia
        btn_huy = (Button)viewDiaLog.findViewById(R.id.btn_huy);
        btn_gui = (Button) viewDiaLog.findViewById(R.id.btn_gui);
        ratingBarDanhGia = (RatingBar)viewDiaLog.findViewById(R.id.ratingBarDanhGia);
        noidung = (EditText)viewDiaLog.findViewById(R.id.noidung);

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDiaLog);
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(false);

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HomeActivity.listkhachhang.size()>0){
                    DanhGia danhGia = new DanhGia(null,HomeActivity.listkhachhang.get(0).getUid(),skey,HomeActivity.listkhachhang.get(0).getName(),noidung.getText().toString(),ratingBarDanhGia.getRating(), TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    databaseReferenceDanhGia.child("DanhGia" +TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())).setValue(danhGia);
                }else {
                    DanhGia danhGia = new DanhGia(null,"",skey,"Ẩn danh",noidung.getText().toString(),ratingBarDanhGia.getRating(), TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    databaseReferenceDanhGia.push().setValue(danhGia);
                }

                bottomSheetDialog.dismiss();

            }
        });

    }

    private void themgiohang(){
        if(HomeActivity.listgiohang.size()>0){
            boolean exists=false;
            for (int i = 0; i<HomeActivity.listgiohang.size();i++){
                if(HomeActivity.listgiohang.get(i).getIdsp() ==skey){
                    HomeActivity.listgiohang.get(i).setSoluongsp(so);
                    HomeActivity.listgiohang.get(i).setGiasp(so*sgia);
                    exists=true;
                }
            }
            if (exists ==false){
                int soluong= so;
                float giamoi=soluong*sgia;
                HomeActivity.listgiohang.add(new GioHang(skey,sten,giamoi,shinh,soluong));
            }
        }else {
            int soluong= so;
            float giamoi=soluong*sgia;
            HomeActivity.listgiohang.add(new GioHang(skey,sten,giamoi,shinh,soluong));
        }

        Intent intent= new Intent(getApplicationContext(),GioHangActivity.class);
        startActivity(intent);
        finish();
    }

    private void update(){
        if (HomeActivity.listgiohang.size()>0){
            boolean exists=false;
            for (int i = 0; i<HomeActivity.listgiohang.size();i++) {
                if (HomeActivity.listgiohang.get(i).getIdsp() == skey) {
                    if(so>0){
                        HomeActivity.listgiohang.get(i).setSoluongsp(so);
                    }else{
                        HomeActivity.listgiohang.remove(i);
                    }

                    exists = true;
                }
            }
            if (exists ==false){
                HomeActivity.listgiohang.add(new GioHang(skey,sten,sgia,shinh,so));
            }
        }else {
            HomeActivity.listgiohang.add(new GioHang(skey,sten,sgia,shinh,so));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton:

                finish();
                break;

            case R.id.button3:
                so++;
                soluong.setText(so+"");
                if(so>0){
                    sub.setBackgroundResource(R.drawable.sub);
                }
                update();
                break;
            case R.id.button4:
                if (so>0){
                    so--;
                }
                soluong.setText(so+"");
                if (so<1){
                    sub.setBackgroundResource(R.drawable.sub_gray);
                    sub.setEnabled(false);
                }
                update();
                break;
            case R.id.themvaogiohang:
                //themgiohang();
                Intent intent= new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.favourite:
                syeuthich=!syeuthich;
                Toast.makeText(DetailsOfFoodActivity.this,syeuthich+"",Toast.LENGTH_SHORT).show();
                if (syeuthich){
                    yeuthich.setBackgroundResource(R.drawable.favorite_true);
                }else {
                    yeuthich.setBackgroundResource(R.drawable.favorite);
                }
                for (int i=0;i<HomeActivity.monAnModelList.size();i++){
                    if(HomeActivity.monAnModelList.get(i).getMamonan().equals(skey)){
                        HomeActivity.monAnModelList.get(i).setYeuthich(syeuthich);
                        databaseReference= FirebaseDatabase.getInstance().getReference("MonAn");
                        MonAnModel monAnModel = new MonAnModel(null,shinh,sten,scate,sgiamgia,sdanhgia,sgia,syeuthich);
                        databaseReference.child(skey).setValue(monAnModel);
                    }
                }
                break;
            case R.id.tv_vietdanhgia:
                vietDanhGia();
                break;

        }
    }


}