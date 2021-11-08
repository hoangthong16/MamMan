package com.example.mamman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.mamman.Fragments.DonHangFragment;
import com.example.mamman.Fragments.OrdersFragment;
import com.example.mamman.Fragments.YeuThichFragment;
import com.example.mamman.Model.BannerModel;
import com.example.mamman.Model.DonHangModel;
import com.example.mamman.Model.GioHang;
import com.example.mamman.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FrameLayout frameLayout;
    public static List<BannerModel> bannerModelList;
    public static List<GioHang> listgiohang;
    public static List<User> listkhachhang;
    public static List<DonHangModel> donHangModelList;
    DatabaseReference databaseReference;
    FirebaseUser user;
    public static String userID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //changing the color status text color
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ///

        user= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("User");

        String codangnhap=getIntent().getStringExtra("mobile");



        if(bannerModelList != null){

        }else {
            bannerModelList = new ArrayList<>();
            bannerModelList.add(new BannerModel(R.drawable.banner1));
            bannerModelList.add(new BannerModel(R.drawable.banner1));
            bannerModelList.add(new BannerModel(R.drawable.banner1));
            bannerModelList.add(new BannerModel(R.drawable.banner1));
            bannerModelList.add(new BannerModel(R.drawable.banner1));
        }

        if(listkhachhang !=null){

        }else {
            listkhachhang = new ArrayList<>();
        }



        if(listgiohang != null){

        }else {
            listgiohang = new ArrayList<>();
        }

        if (donHangModelList != null) {

        } else {
            donHangModelList = new ArrayList<>();
        }


        if (codangnhap !=null && listkhachhang.size()<1){
            userID=codangnhap;
            Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();
            //listkhachhang.add(new User(userID,null,null,null,null,null,null));
            databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile=snapshot.getValue(User.class);
                    if (userProfile!=null){
                        listkhachhang.add(new User(userID,userProfile.name,userProfile.email,userProfile.password,userProfile.phonenumber,userProfile.address,userProfile.date));
                        /*
                        if(HomeActivity.listkhachhang.size()>0) {
                            HomeActivity.listkhachhang.get(0).setName(userProfile.name);
                            HomeActivity.listkhachhang.get(0).setEmail(userProfile.email);
                            HomeActivity.listkhachhang.get(0).setPassword(userProfile.password);
                            HomeActivity.listkhachhang.get(0).setPhonenumber(userProfile.phonenumber);
                            HomeActivity.listkhachhang.get(0).setAddress(userProfile.address);
                            HomeActivity.listkhachhang.get(0).setDate(userProfile.date);
                        }

                         */
                    }else {
                        listkhachhang.add(new User(userID,null,null,null,null,null,null));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(HomeActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            });
        }







        bottomNavigation =(BottomNavigationView)findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigation);
        Bundle args= new Bundle();
        args.putString("codangnhap",codangnhap );
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        OrdersFragment fragment = new OrdersFragment();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
        //updatet();
        //replacing by default fragment on home activity


        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
        //     new OrdersFragment()).commit();
        //String verificationId = getIntent().getStringExtra("mobile");
        //Toast.makeText(this,verificationId,Toast.LENGTH_LONG).show();
    }
    /*
    public void updatet(){
        OrdersFragment ordersFragment = (OrdersFragment)getSupportFragmentManager().findFragmentByTag("fragment");
        if(ordersFragment != null){
            ordersFragment.update(getIntent().getStringExtra("mobile"));
            Toast.makeText(this,getIntent().getStringExtra("mobile"),Toast.LENGTH_LONG).show();
        }
    }

     */


    private BottomNavigationView.OnNavigationItemSelectedListener navigation=
    new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId())
            {
                case R.id.orders:
                    selectedFragment=new OrdersFragment();
                    break;

                case R.id.fragment_yeu_thich:
                    selectedFragment=new YeuThichFragment();
                    break;

                case R.id.fragment_don_hang:
                    selectedFragment=new DonHangFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                    selectedFragment).commit();
            return true;
        }
    };





}