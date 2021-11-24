package com.example.mamman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mamman.Adapters.BannerAdapter;
import com.example.mamman.Adapters.DetailsOfFoodAdapter;
import com.example.mamman.Adapters.MonAnAdapter;
import com.example.mamman.Fragments.OrdersFragment;
import com.example.mamman.Model.GioHang;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import static java.security.AccessController.getContext;

public class DetailsOfFoodActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton back,yeuthich;
    ImageView img;
    Button sub,add,themvaogiohang;
    TextView ten,soluong,gia;
    ConstraintLayout details;
    private int so=0;
    private int position;
    String foodId="";
    private String shinh,sten,skey,scate;
    private float sgia,sgiamgia,sdanhgia;
    private int sposition;
    private boolean syeuthich;
    DatabaseReference databaseReference;

    private DetailsOfFoodAdapter detailsOfFoodAdapter;
    private List<MonAnModel> monAnModelList;
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
                break;
            case R.id.button4:
                if (so>0){
                    so--;
                }
                soluong.setText(so+"");
                if (so<1){
                    sub.setBackgroundResource(R.drawable.sub_gray);
                }
                break;
            case R.id.themvaogiohang:
                themgiohang();
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
        }
    }


}