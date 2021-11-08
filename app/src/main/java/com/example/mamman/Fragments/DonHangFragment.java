package com.example.mamman.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mamman.Adapters.DonHangAdapter;
import com.example.mamman.Adapters.MonAnAdapter;
import com.example.mamman.ChiTietDonHangActivity;
import com.example.mamman.HomeActivity;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.CategoryModel;
import com.example.mamman.Model.ChiTietDonHang;
import com.example.mamman.Model.DonHangModel;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DonHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DonHangFragment extends Fragment implements RecyclerViewClickInterface {
    private View view;
    RecyclerView recyclerViewdonhang;
    private DonHangAdapter donHangAdapter;
    Button bttrangthai,bttrangthaihuy;
    String tt;

    private DatabaseReference mData;
    FirebaseRecyclerAdapter<DonHangModel, DonHangAdapter.DonHangViewHolder> adapter;
    FirebaseRecyclerOptions<DonHangModel> options;
    List<DonHangModel> donhangtheotrangthaihoanthanh,getDonhangtheotrangthaihuy;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DonHangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DonHangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DonHangFragment newInstance(String param1, String param2) {
        DonHangFragment fragment = new DonHangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_don_hang, container, false);


        recyclerViewdonhang = (RecyclerView) view.findViewById(R.id.recyclerViewdonhang);
        bttrangthai= (Button) view.findViewById(R.id.bttrangthai);
        bttrangthaihuy=(Button) view.findViewById(R.id.bttrangthaihuy);
        mData= FirebaseDatabase.getInstance().getReference();

        //bttrangthai.setText("Hoàn thành");
        LinearLayoutManager layoutDonHang = new LinearLayoutManager(getContext());
        layoutDonHang.setOrientation(RecyclerView.VERTICAL);
        recyclerViewdonhang.setLayoutManager(layoutDonHang);


        donHangAdapter = new DonHangAdapter(HomeActivity.donHangModelList,getContext(),DonHangFragment.this);
        recyclerViewdonhang.setAdapter(donHangAdapter);

        mData.child("DonHang").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DonHangModel donhang = snapshot.getValue(DonHangModel.class);
                String madonhang= snapshot.getKey();
                HomeActivity.donHangModelList.add(new DonHangModel(madonhang,donhang.getUserID(),donhang.getTenkhachhang(),donhang.getSdt(),donhang.getDiachi(),donhang.getTrangthai()));
                donHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DonHangModel donhang = snapshot.getValue(DonHangModel.class);
                String madonhang= snapshot.getKey();
                HomeActivity.donHangModelList.add(new DonHangModel(madonhang,donhang.getUserID(),donhang.getTenkhachhang(),donhang.getSdt(),donhang.getDiachi(),donhang.getTrangthai()));
                donHangAdapter.notifyDataSetChanged();
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

        bttrangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangthai();
            }
        });
        bttrangthaihuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangthaihuy();
            }
        });



        return view;
    }
    private void trangthai(){
        if(donhangtheotrangthaihoanthanh !=null){

        }else {
            donhangtheotrangthaihoanthanh = new ArrayList<>();
            for (int i=0;i<HomeActivity.donHangModelList.size();i++){
                if(HomeActivity.donHangModelList.get(i).getTrangthai().equals("thanhcong")){
                    donhangtheotrangthaihoanthanh.add(HomeActivity.donHangModelList.get(i));
                }
            }
        }




        donHangAdapter = new DonHangAdapter(donhangtheotrangthaihoanthanh,getContext(),DonHangFragment.this);
        recyclerViewdonhang.setAdapter(donHangAdapter);
        donHangAdapter.notifyDataSetChanged();




    }
    private void trangthaihuy(){
        if(getDonhangtheotrangthaihuy !=null){

        }else {
            getDonhangtheotrangthaihuy = new ArrayList<>();
            for (int i=0;i<HomeActivity.donHangModelList.size();i++){
                if(HomeActivity.donHangModelList.get(i).getTrangthai().equals("huy")){
                    getDonhangtheotrangthaihuy.add(HomeActivity.donHangModelList.get(i));
                }
            }
        }
        donHangAdapter = new DonHangAdapter(getDonhangtheotrangthaihuy,getContext(),DonHangFragment.this);
        recyclerViewdonhang.setAdapter(donHangAdapter);
        donHangAdapter.notifyDataSetChanged();
    }



    @Override
    public void onItemClick(int position) {
        Intent intent= new Intent(getContext(), ChiTietDonHangActivity.class);
        intent.putExtra("madonhang",HomeActivity.donHangModelList.get(position).getMadonhang());
        startActivity(intent);
    }

    @Override
    public void onItemClickcat(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }
}