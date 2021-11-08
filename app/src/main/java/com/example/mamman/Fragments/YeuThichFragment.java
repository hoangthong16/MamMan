package com.example.mamman.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mamman.Adapters.MonAnAdapter;
import com.example.mamman.CategoryActivity;
import com.example.mamman.DetailsOfFoodActivity;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.R;
import com.example.mamman.sort;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YeuThichFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YeuThichFragment extends Fragment implements RecyclerViewClickInterface {
    private View view;
    RecyclerView recyclerViewyeuthich;

    private DatabaseReference mData;
    FirebaseRecyclerAdapter<MonAnModel, MonAnAdapter.MonAnViewHolder> adapter;
    FirebaseRecyclerOptions<MonAnModel> options;

    List<MonAnModel> monAnModelListYeuThich;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public YeuThichFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YeuThichFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YeuThichFragment newInstance(String param1, String param2) {
        YeuThichFragment fragment = new YeuThichFragment();
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
        view= inflater.inflate(R.layout.fragment_yeu_thich, container, false);

        recyclerViewyeuthich=(RecyclerView) view.findViewById(R.id.recyclerViewyeuthich);

        if(monAnModelListYeuThich != null){

        }else {
            monAnModelListYeuThich = new ArrayList<>();
        }
        mData = FirebaseDatabase.getInstance().getReference("MonAn");

        options = new FirebaseRecyclerOptions.Builder<MonAnModel>()
                .setQuery(mData,MonAnModel.class)
                .build();

        adapter=new FirebaseRecyclerAdapter<MonAnModel, MonAnAdapter.MonAnViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MonAnAdapter.MonAnViewHolder holder, int position, @NonNull MonAnModel model) {

            }

            @NonNull
            @Override
            public MonAnAdapter.MonAnViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_vertical_slider,viewGroup,false);
                return  new MonAnAdapter.MonAnViewHolder(view);
            }
        };

        LinearLayoutManager layoutManagerBanner = new LinearLayoutManager(getContext());
        layoutManagerBanner.setOrientation(RecyclerView.VERTICAL);
        recyclerViewyeuthich.setLayoutManager(layoutManagerBanner);
        adapter.startListening();
        recyclerViewyeuthich.setAdapter(adapter);

        Query query= mData.orderByChild("yeuthich").equalTo(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    monAnModelListYeuThich.clear();
                    for (DataSnapshot dss: snapshot.getChildren()){
                        final MonAnModel monAnModel= dss.getValue(MonAnModel.class);
                        monAnModelListYeuThich.add(monAnModel);
                        /*
                        if(dss.getKey() != null){
                            key.add(dss.getKey());
                        }

                         */
                    }

                    sapxepdanhgiagiamdan();

                    MonAnAdapter monAnAdapter=new MonAnAdapter(monAnModelListYeuThich,getContext(), YeuThichFragment.this);
                    recyclerViewyeuthich.setAdapter(monAnAdapter);
                    monAnAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


    public void sapxepdanhgiagiamdan() {
        Collections.sort(monAnModelListYeuThich,new sort());
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailsOfFoodActivity.class);
        intent.putExtra("key",monAnModelListYeuThich.get(position).mamonan);
        intent.putExtra("position",position);
        intent.putExtra("hinhanh",monAnModelListYeuThich.get(position).link_hinh);
        intent.putExtra("ten",monAnModelListYeuThich.get(position).tenmonan);
        intent.putExtra("gia",monAnModelListYeuThich.get(position).dongia);
        intent.putExtra("yeuthich",monAnModelListYeuThich.get(position).yeuthich);
        intent.putExtra("cate",monAnModelListYeuThich.get(position).getCategory());
        intent.putExtra("giamgia",monAnModelListYeuThich.get(position).getGiamgia());
        intent.putExtra("danhgia",monAnModelListYeuThich.get(position).getDanhgia());
        startActivity(intent);

    }

    @Override
    public void onItemClickcat(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }
}