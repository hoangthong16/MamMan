package com.example.mamman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mamman.Adapters.MonAnAdapter;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.CategoryModel;
import com.example.mamman.Model.MonAnModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements RecyclerViewClickInterface{
    ImageButton btback;
    TextView tvcategory,thongbao;
    RecyclerView recyclerViewCategory;

    private DatabaseReference mData;
    FirebaseRecyclerAdapter<MonAnModel, MonAnAdapter.MonAnViewHolder> adapter;
    FirebaseRecyclerOptions<MonAnModel> options;

    public static List<MonAnModel> monAnModelListcat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btback= (ImageButton) findViewById(R.id.btback);
        tvcategory=(TextView) findViewById(R.id.tvcategory);
        thongbao=(TextView) findViewById(R.id.thongbao);
        recyclerViewCategory=(RecyclerView) findViewById(R.id.recyclerViewCategory);



        String category=getIntent().getStringExtra("category");
        String title=getIntent().getStringExtra("cat_title");

        tvcategory.setText(title);

        Toast.makeText(getApplicationContext(),category,Toast.LENGTH_SHORT).show();
        recyclerViewCategory.setVisibility(View.VISIBLE);
        thongbao.setVisibility(View.INVISIBLE);



        if(monAnModelListcat != null){

        }else {
            monAnModelListcat = new ArrayList<>();
        }



        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CategoryActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


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



        LinearLayoutManager layoutManagerBanner = new LinearLayoutManager(this);
        layoutManagerBanner.setOrientation(RecyclerView.VERTICAL);
        recyclerViewCategory.setLayoutManager(layoutManagerBanner);
        adapter.startListening();
        recyclerViewCategory.setAdapter(adapter);

        search(category);
        //checkdata();


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkdata();
    }

    private void search(String s){
        /*
        String stringUpper= s.toString().toUpperCase();
        String stringLower= s.toString().toLowerCase();
        Query query= mData.orderByChild("tenmonan").startAt(stringUpper).endAt(stringLower +"\uf8ff");
         */
        //String stringLower= s.toString().toLowerCase();
        //Query query= mData.orderByChild("tenmonan").equalTo(s);
        Query query= mData.orderByChild("category").equalTo(s);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    monAnModelListcat.clear();
                    for (DataSnapshot dss: snapshot.getChildren()){
                        final MonAnModel monAnModel= dss.getValue(MonAnModel.class);
                        monAnModelListcat.add(monAnModel);
                        /*
                        if(dss.getKey() != null){
                            key.add(dss.getKey());
                        }

                         */
                    }

                    MonAnAdapter monAnAdapter=new MonAnAdapter(monAnModelListcat,getApplicationContext(), CategoryActivity.this);
                    recyclerViewCategory.setAdapter(monAnAdapter);
                    monAnAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkdata(){
        if(monAnModelListcat.size()==0){

            thongbao.setVisibility(View.VISIBLE);
            recyclerViewCategory.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(CategoryActivity.this,DetailsOfFoodActivity.class);
        //intent.putExtra("key",key.get(position));
        intent.putExtra("position",position);
        intent.putExtra("hinhanh",monAnModelListcat.get(position).link_hinh);
        intent.putExtra("ten",monAnModelListcat.get(position).tenmonan);
        intent.putExtra("gia",monAnModelListcat.get(position).dongia);
        startActivity(intent);
    }

    @Override
    public void onItemClickcat(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }



}