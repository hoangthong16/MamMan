package com.example.mamman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.TintableBackgroundView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.telecom.RemoteConference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mamman.Adapters.MonAnAdapter;
import com.example.mamman.Interface.MonAnClickInterface;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.Model.MonAnModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements RecyclerViewClickInterface, MonAnClickInterface {

    private RecyclerView recyclerViewSearch;
    private ImageButton btback;
    private EditText search;
    private ImageButton btn_nghe;
    List<MonAnModel> monAnModelList;
    private DatabaseReference mData;
    FirebaseRecyclerAdapter<MonAnModel, MonAnAdapter.MonAnViewHolder> adapter;
    FirebaseRecyclerOptions<MonAnModel> options;
    List<String> key;

    private final int REQ_CODE_SPEECH= 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerViewSearch= (RecyclerView)findViewById(R.id.recyclerViewSearch);
        btback=(ImageButton)findViewById(R.id.btback);
        search=(EditText)findViewById(R.id.search);
        btn_nghe = (ImageButton)findViewById(R.id.btn_nghe);
        monAnModelList = new ArrayList<>();

        LinearLayoutManager layoutManagerBanner = new LinearLayoutManager(this);
        layoutManagerBanner.setOrientation(RecyclerView.VERTICAL);
        recyclerViewSearch.setLayoutManager(layoutManagerBanner);
        recyclerViewSearch.setVisibility(View.INVISIBLE);

        btn_nghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speech_to_text();
            }
        });

        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    recyclerViewSearch.setVisibility(View.VISIBLE);
                    Search(s.toString());

                }else {
                    recyclerViewSearch.setVisibility(View.INVISIBLE);
                }
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
        adapter.startListening();
        recyclerViewSearch.setAdapter(adapter);
    }

    private void speech_to_text(){
        //gọi nhận dạng giọng nói




        try {
            Log.e("nhandan","da an");
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            // lấy ngôn ngữ mặc định trên máy
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Đang nghe");
            startActivityForResult(intent,REQ_CODE_SPEECH);
        }catch (ActivityNotFoundException e){
            Log.e("nhận diện", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CODE_SPEECH: {

                if(resultCode ==RESULT_OK && data != null){

                    List<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    String first = text.substring(0,1);
                    String conlai= text.substring(1,text.length());
                    first = first.toUpperCase();
                    text = first+conlai;

                    search.setText(text+"");
                    //Search(text+"");

                }
                break;
            }
            default:
                break;
        }
    }

    private void Search(String s){
        key = new ArrayList<>();
        /*
        String stringUpper= s.toString().toUpperCase();
        String stringLower= s.toString().toLowerCase();
        Query query= mData.orderByChild("tenmonan").startAt(stringUpper).endAt(stringLower +"\uf8ff");
         */
        //String stringLower= s.toString().toLowerCase();
        //Query query= mData.orderByChild("tenmonan").equalTo(s);
        Query query= mData.orderByChild("tenmonan").startAt(s).endAt(s +"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(SearchActivity.this,DetailsOfFoodActivity.class);
        intent.putExtra("key",key.get(position));
        intent.putExtra("position",position);
        intent.putExtra("hinhanh",monAnModelList.get(position).link_hinh);
        intent.putExtra("ten",monAnModelList.get(position).tenmonan);
        intent.putExtra("gia",monAnModelList.get(position).dongia);
        startActivity(intent);
    }

    @Override
    public void onItemClickcat(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }

    @Override
    public void onButtonclick(int id, int position) {

    }
}