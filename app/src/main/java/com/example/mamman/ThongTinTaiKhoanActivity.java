package com.example.mamman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mamman.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.TooManyListenersException;

public class ThongTinTaiKhoanActivity extends AppCompatActivity {

    private ImageView imageButton;
    private TextView capnhat,dangxuat;
    private EditText sdt,ten,email,address,ngaysinh,matkhau;
    DatabaseReference databaseReference;
    private String userID;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);

        imageButton=(ImageView)findViewById(R.id.imageButton);
        capnhat = (TextView)findViewById(R.id.textView14);
        dangxuat=(TextView)findViewById(R.id.dangxuat);
        sdt = (EditText)findViewById(R.id.sdt);
        ten = (EditText)findViewById(R.id.ten);
        email = (EditText)findViewById(R.id.email);
        address = (EditText)findViewById(R.id.diachi);
        ngaysinh = (EditText)findViewById(R.id.ngaysinh);
        matkhau = (EditText)findViewById(R.id.matkhau);
        /*
        if (savedInstanceState != null) {
            ten.setText(savedInstanceState.getInt("ten"));
            email.setText(savedInstanceState.getInt("email"));
            matkhau.setText(savedInstanceState.getInt("matkhau"));
            sdt.setText(savedInstanceState.getInt("sdt"));
            address.setText(savedInstanceState.getInt("diachi"));
            ngaysinh.setText(savedInstanceState.getInt("ngaysinh"));
        }

         */

        user=FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        userID=HomeActivity.userID;
        if(userID!=null){
            databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile=snapshot.getValue(User.class);
                    if (userProfile!=null){
                        String sname=userProfile.name;
                        String semail=userProfile.email;
                        String spassword=userProfile.password;
                        String sphonenumber=userProfile.phonenumber;
                        String saddress=userProfile.address;
                        String sdate=userProfile.date;

                        Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();
                        ten.setText(sname);
                        email.setText(semail);
                        matkhau.setText(spassword);
                        sdt.setText(sphonenumber);
                        address.setText(saddress);
                        ngaysinh.setText(sdate);
                    }else {
                        Toast.makeText(ThongTinTaiKhoanActivity.this,"chua co du lieu",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ThongTinTaiKhoanActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            });
        }


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinTaiKhoanActivity.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("mobile","thongtin");
                //startActivity(intent);
                finish();

            }
        });

        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUser();

            }
        });

        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ThongTinTaiKhoanActivity.this,MainActivity.class);
                HomeActivity.listkhachhang.clear();
                HomeActivity.listgiohang.clear();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private void UpdateUser(){
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        User user = new User(null,ten.getText().toString(),email.getText().toString(),matkhau.getText().toString(),sdt.getText().toString(),address.getText().toString(),ngaysinh.getText().toString());
        databaseReference.child(userID).setValue(user);

        if(HomeActivity.listkhachhang.size()>0){
            HomeActivity.listkhachhang.get(0).setName(ten.getText().toString());
            HomeActivity.listkhachhang.get(0).setEmail(email.getText().toString());
            HomeActivity.listkhachhang.get(0).setPassword(matkhau.getText().toString());
            HomeActivity.listkhachhang.get(0).setPhonenumber(sdt.getText().toString());
            HomeActivity.listkhachhang.get(0).setAddress(address.getText().toString());
            HomeActivity.listkhachhang.get(0).setDate(ngaysinh.getText().toString());
            Toast.makeText(getApplicationContext(),"Bạn đã cập nhật thành công",Toast.LENGTH_SHORT).show();


        }
    }
    /*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ten",ten.getText().toString());
        outState.putString("email",email.getText().toString());
        outState.putString("matkhau",matkhau.getText().toString());
        outState.putString("sdt",sdt.getText().toString());
        outState.putString("diachi",address.getText().toString());
        outState.putString("ngaysinh",ngaysinh.getText().toString());
    }

     */
}