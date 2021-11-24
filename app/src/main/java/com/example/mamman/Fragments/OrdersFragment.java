    package com.example.mamman.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mamman.Adapters.BannerAdapter;
import com.example.mamman.Adapters.CatAdapter;
import com.example.mamman.Adapters.MonAnAdapter;
import com.example.mamman.Adapters.MonAnBanChayAdapter;
import com.example.mamman.CategoryActivity;
import com.example.mamman.DetailsOfFoodActivity;
import com.example.mamman.GioHangActivity;
import com.example.mamman.HomeActivity;
import com.example.mamman.Interface.MonAnClickInterface;
import com.example.mamman.Interface.RecyclerViewClickInterface;
import com.example.mamman.MainActivity;
import com.example.mamman.Model.CategoryModel;
import com.example.mamman.Model.GioHang;
import com.example.mamman.Model.MonAnBanChayModel;
import com.example.mamman.Model.MonAnModel;
import com.example.mamman.R;
import com.example.mamman.SearchActivity;
import com.example.mamman.ThongTinTaiKhoanActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment implements View.OnClickListener, RecyclerViewClickInterface, MonAnClickInterface {



    private String mobile;


    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("OrderFragment","onCreate");
    }

    public interface SendDataInterface{
        public void sendData(String data);
    }
    SendDataInterface sendDataInterface;

    private List<String> key;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseDatabase database;
    DatabaseReference mData;
    FirebaseRecyclerAdapter<MonAnModel, MonAnAdapter.MonAnViewHolder> adapter;
    FirebaseRecyclerOptions<MonAnModel> options;

    TextView location,bacham;
    EditText search;
    DrawerLayout drawerLayout;
    ImageView navigationBar;
    NavigationView navigationView;
    private View view;
    private RelativeLayout loginSignUp,bookmarks, eightMMGold;
    private TextView your_orders,online_ordering_help,address_book,favourite_orders,send_feedback,report_safety_emergency,rate_playstore;



    //category slider start
    private RecyclerView recyclerViewCategory;
    private CatAdapter catAdapter;
    private List<CategoryModel> categoryModelList;
    //category slider end

    //banner slider start
    private RecyclerView recyclerViewBanner;
    private BannerAdapter bannerAdapter;
    //private List<BannerModel> bannerModelList;
    //banner slide end

    //simple vertical slider start
    private RecyclerView recyclerViewMonAn;
    private MonAnAdapter monAnAdapter;
    //public static List<MonAnModel> monAnModelList;
    //simple vertical slider end

    //great offer horizontal start
    private RecyclerView greatGreatOffersHorizontal;
    private List<MonAnBanChayModel> monAnBanChayModelList;
    private MonAnBanChayAdapter monAnBanChayAdapter;
    //great offer horizontal end

    //great offers vertical slider start
    private RecyclerView greatOffersRecyclerViewVertical;
    //great offers vertical slider end

    //new Arraivals horizontal slider start
    private RecyclerView newArrivalHorizontalRecyclerview;
    //new Arraivals horizontal slider end

    //new Arraivals vertical slider start
    private RecyclerView newArrivalVerticalRecyclerview;
    //new Arraivals vertical slider end

    //Mammam exclusive horizontal slider start
    private RecyclerView exclusiveHorizontalRecyclerview;
    //Mammam exclusive horizontal slider end

    //Mammam exclusive vertical slider start
    private RecyclerView exclusiveVerticalRecyclerview;
    //Mammam exclusive vertical slider end
    private TextView thongtin;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_orders, container, false);
        mData= FirebaseDatabase.getInstance().getReference();

        thongtin = (TextView) view.findViewById(R.id.thongtin);
        location= (TextView) view.findViewById(R.id.location);
        bacham= (TextView) view.findViewById(R.id.bacham);
        search= (EditText) view.findViewById(R.id.search);
        /*
        if(getArguments() != null){
            mobile = getArguments().getString("codangnhap");
            thongtin.setText("Thông tin tài khoản");
        }else {
            thongtin.setText("Đăng nhập");
        }

         */
        if (HomeActivity.listkhachhang.size()>0){
            thongtin.setText("Thông tin tài khoản");
        }else {
            thongtin.setText("Đăng nhập");
        }

        Log.e("OrderFragment","onCreateView");


        onSetNavigationDrawerEvents();
        init();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ThongTinTaiKhoanActivity.class);
                startActivity(intent);
            }
        });


        //LoadData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(HomeActivity.listkhachhang.size()>0){
            //Toast.makeText(getContext(),HomeActivity.listkhachhang.get(0).getEmail(),Toast.LENGTH_LONG).show();
            String diachi= HomeActivity.listkhachhang.get(0).getAddress();
            Toast.makeText(getContext(),"onResume",Toast.LENGTH_LONG).show();

            if(diachi != null){
                location.setText(HomeActivity.listkhachhang.get(0).getAddress());

            }else {
                location.setText("Trống");
            }

        }

    }

    private void init() {
        //Category model list start
        recyclerViewCategory = (RecyclerView) view.findViewById(R.id.recyclerViewCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewCategory.setLayoutManager(layoutManager);

        categoryModelList = new ArrayList<>();
        catAdapter = new CatAdapter(categoryModelList,getContext(),OrdersFragment.this);
        recyclerViewCategory.setAdapter(catAdapter);

        LoadCategory();
        //category model list end


        //banner model list start
        recyclerViewBanner =(RecyclerView) view.findViewById(R.id.recyclerViewBanner);
        LinearLayoutManager layoutManagerBanner = new LinearLayoutManager(getContext());
        layoutManagerBanner.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewBanner.setLayoutManager(layoutManagerBanner);
        /*
        bannerModelList = new ArrayList<>();
        bannerModelList.add(new BannerModel(R.drawable.category_test));
        bannerModelList.add(new BannerModel(R.drawable.category_test));
        bannerModelList.add(new BannerModel(R.drawable.category_test));
        bannerModelList.add(new BannerModel(R.drawable.category_test));
        bannerModelList.add(new BannerModel(R.drawable.category_test));
        bannerModelList.add(new BannerModel(R.drawable.category_test));
        bannerModelList.add(new BannerModel(R.drawable.category_test));
        bannerModelList.add(new BannerModel(R.drawable.category_test));
        bannerModelList.add(new BannerModel(R.drawable.category_test));
        bannerModelList.add(new BannerModel(R.drawable.category_test));

         */


        bannerAdapter = new BannerAdapter(HomeActivity.bannerModelList,getContext());
        recyclerViewBanner.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();
        //banner model list end
/**
        //simple vertical model list start
        recyclerViewMonAn =(RecyclerView) view.findViewById(R.id.recyclerViewSimple);

        LinearLayoutManager layoutManagerSimpleVerticalSlider = new LinearLayoutManager(getContext());
        layoutManagerSimpleVerticalSlider.setOrientation(RecyclerView.VERTICAL);
        recyclerViewMonAn.setLayoutManager(layoutManagerSimpleVerticalSlider);

        monAnModelList = new ArrayList<>();
        //monAnModelList.add(new MonAnModel("https://firebasestorage.googleapis.com/v0/b/mammamtest.appspot.com/o/%C4%83n%20v%E1%BA%B7t.png?alt=media&token=7d3b8a90-db54-4510-bff8-de519b06dff5","monn","Cơm",1,2,3));

        monAnAdapter = new MonAnAdapter(monAnModelList,getContext(),this);
        recyclerViewMonAn.setAdapter(monAnAdapter);
        LoadMonAn();

        //simple vertical model list end

        //great offers model list start
        greatGreatOffersHorizontal =(RecyclerView) view.findViewById(R.id.recyclerViewgreatOffersHorizontal);
        LinearLayoutManager layoutManagerGreatOffers = new LinearLayoutManager(getContext());
        layoutManagerGreatOffers.setOrientation(RecyclerView.HORIZONTAL);
        greatGreatOffersHorizontal.setLayoutManager(layoutManagerGreatOffers);

        monAnBanChayModelList = new ArrayList<>();

        monAnBanChayAdapter = new MonAnBanChayAdapter(monAnBanChayModelList,getContext());
        greatGreatOffersHorizontal.setAdapter(monAnBanChayAdapter);
        LoadMonAnBanChay();
        //great offers model list end

        //new great offers vertical slider start
        greatOffersRecyclerViewVertical =(RecyclerView) view.findViewById(R.id.greatOffersRecyclerViewVertical);
        LinearLayoutManager layoutManagerVerticalGreatOffers= new LinearLayoutManager(getContext());
        layoutManagerVerticalGreatOffers.setOrientation(RecyclerView.VERTICAL);
        greatOffersRecyclerViewVertical.setLayoutManager(layoutManagerVerticalGreatOffers);

        monAnModelList = new ArrayList<>();


        monAnAdapter = new MonAnAdapter(monAnModelList,getContext(),this);
        greatOffersRecyclerViewVertical.setAdapter(monAnAdapter);
        LoadMonAn();
        //new great offers vertical slider end

        //new arrival horizontal model list start
        newArrivalHorizontalRecyclerview =(RecyclerView) view.findViewById(R.id.newArrivalHorizontalRecyclerview);
        LinearLayoutManager layoutManagerhorizonNewArrival = new LinearLayoutManager(getContext());
        layoutManagerhorizonNewArrival.setOrientation(RecyclerView.HORIZONTAL);
        newArrivalHorizontalRecyclerview.setLayoutManager(layoutManagerhorizonNewArrival);

        monAnBanChayModelList = new ArrayList<>();
        monAnBanChayAdapter = new MonAnBanChayAdapter(monAnBanChayModelList,getContext());
        newArrivalHorizontalRecyclerview.setAdapter(monAnBanChayAdapter);
        LoadMonAnBanChay();
        //new arrival horizontal model list end

        //new arrival vertical slider start
        newArrivalVerticalRecyclerview =(RecyclerView) view.findViewById(R.id.newArrivalVerticalRecyclerview);
        LinearLayoutManager layoutManagerVerticalNewArrival= new LinearLayoutManager(getContext());
        layoutManagerVerticalNewArrival.setOrientation(RecyclerView.VERTICAL);
        newArrivalVerticalRecyclerview.setLayoutManager(layoutManagerVerticalNewArrival);

        monAnModelList = new ArrayList<>();


        monAnAdapter = new MonAnAdapter(monAnModelList,getContext(),this);
        newArrivalVerticalRecyclerview.setAdapter(monAnAdapter);
        //LoadMonAn();

        //new arrival vertical slider end
**/
        //new arrival horizontal model list start
        exclusiveHorizontalRecyclerview =(RecyclerView) view.findViewById(R.id.exclusiveHorizontalRecyclerview);
        LinearLayoutManager layoutManagerexclusiveHorizon = new LinearLayoutManager(getContext());
        layoutManagerexclusiveHorizon.setOrientation(RecyclerView.HORIZONTAL);
        exclusiveHorizontalRecyclerview.setLayoutManager(layoutManagerexclusiveHorizon);

        monAnBanChayModelList = new ArrayList<>();

        monAnBanChayAdapter = new MonAnBanChayAdapter(monAnBanChayModelList,getContext(),OrdersFragment.this);
        exclusiveHorizontalRecyclerview.setAdapter(monAnBanChayAdapter);
        LoadMonAnBanChay();
        //new arrival horizontal model list end

        //new arrival vertical slider start
        exclusiveVerticalRecyclerview =(RecyclerView) view.findViewById(R.id.exclusiveVerticalRecyclerview);
        LinearLayoutManager layoutManagerexclusiveVertical= new LinearLayoutManager(getContext());
        layoutManagerexclusiveVertical.setOrientation(RecyclerView.VERTICAL);
        exclusiveVerticalRecyclerview.setLayoutManager(layoutManagerexclusiveVertical);

        HomeActivity.monAnModelList = new ArrayList<>();


        monAnAdapter = new MonAnAdapter(HomeActivity.monAnModelList,getContext(),OrdersFragment.this, OrdersFragment.this);
        exclusiveVerticalRecyclerview.setAdapter(monAnAdapter);
        LoadMonAn();


        //new arrival vertical slider end
    }


    private void onSetNavigationDrawerEvents() {
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);


        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);

        loginSignUp = (RelativeLayout) view.findViewById(R.id.relativeLayout2);
        bookmarks = (RelativeLayout) view.findViewById(R.id.relativeLayout3);
        eightMMGold = (RelativeLayout) view.findViewById(R.id.relativeLayout4);

        your_orders= (TextView) view.findViewById(R.id.your_orders);
        online_ordering_help= (TextView) view.findViewById(R.id.online_ordering_help);
        address_book= (TextView) view.findViewById(R.id.address_book);
        favourite_orders= (TextView) view.findViewById(R.id.favourite_orders);
        send_feedback= (TextView) view.findViewById(R.id.send_feedback);
        report_safety_emergency= (TextView) view.findViewById(R.id.report_safety_emergency);
        rate_playstore= (TextView) view.findViewById(R.id.rate_playstore);

        navigationBar.setOnClickListener(this);
        loginSignUp.setOnClickListener(this);
        bookmarks.setOnClickListener(this);
        eightMMGold.setOnClickListener(this);

        your_orders.setOnClickListener(this);
        online_ordering_help.setOnClickListener(this);
        address_book.setOnClickListener(this);
        favourite_orders.setOnClickListener(this);
        send_feedback.setOnClickListener(this);
        report_safety_emergency.setOnClickListener(this);
        rate_playstore.setOnClickListener(this);



    }
/*
    public void update(String s){
        this.s = s;

        if(s != null){
            Toast.makeText(getContext(),s.toString(),Toast.LENGTH_LONG).show();
            thongtin.setText("Thông tin tài khoản");
            Intent intent = new Intent(getContext(), ThongTinTaiKhoanActivity.class);
            startActivity(intent);
        }
    }

 */


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigationBar:
                drawerLayout.openDrawer(navigationView, true);
                if (HomeActivity.listkhachhang.size()>0){
                    thongtin.setText("Thông tin tài khoản");
                }else {
                    thongtin.setText("Đăng nhập");
                }
                break;
            case R.id.relativeLayout2:

                if(HomeActivity.listkhachhang.size()>0){
                    if(HomeActivity.listkhachhang.get(0).uid!=null){
                        Intent intent = new Intent(getContext(), ThongTinTaiKhoanActivity.class);
                        intent.putExtra("sdt",mobile);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getContext(),"loginsignup",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(getContext(),"loginsignup",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
                /*
                if(mobile!=null){
                    Intent intent = new Intent(getContext(), ThongTinTaiKhoanActivity.class);
                    intent.putExtra("sdt",mobile);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(),"loginsignup",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }

                 */

                break;
            case R.id.relativeLayout3:
                Toast.makeText(getContext(),"bookmarks",Toast.LENGTH_SHORT).show();
                break;
            case R.id.relativeLayout4:
                Toast.makeText(getContext(),"MamMam",Toast.LENGTH_SHORT).show();
                break;
            case R.id.your_orders:
                Toast.makeText(getContext(),"your orders",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), GioHangActivity.class);
                startActivity(intent);
                break;
            case R.id.address_book:
                Toast.makeText(getContext(),"address_book",Toast.LENGTH_SHORT).show();
                break;
            case R.id.favourite_orders:
                Toast.makeText(getContext(),"favourite_orders",Toast.LENGTH_SHORT).show();
                break;
            case R.id.send_feedback:
                Toast.makeText(getContext(),"send_feedback",Toast.LENGTH_SHORT).show();
                break;
            case R.id.report_safety_emergency:
                Toast.makeText(getContext(),"report_safety_emergency",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rate_playstore:
                Toast.makeText(getContext(),"rate_playstore",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void LoadCategory(){
        mData.child("Category").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CategoryModel cat = snapshot.getValue(CategoryModel.class);
                String key = snapshot.getKey();
                categoryModelList.add(new CategoryModel(key, cat.link_hinh,cat.cat_title));
                catAdapter.notifyDataSetChanged();
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
    }
    private void LoadMonAn() {

        mData.child("MonAn").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MonAnModel mon = snapshot.getValue(MonAnModel.class);
                String key= snapshot.getKey();
                HomeActivity.monAnModelList.add(new MonAnModel(key,mon.link_hinh,mon.tenmonan,mon.category, mon.giamgia,mon.danhgia,mon.dongia,mon.yeuthich));
                monAnAdapter.notifyDataSetChanged();
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
    }

    private void LoadMonAnBanChay(){
        mData.child("MonAn").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MonAnBanChayModel mon=snapshot.getValue(MonAnBanChayModel.class);
                String key= snapshot.getKey();

                monAnBanChayModelList.add(new MonAnBanChayModel(key,mon.link_hinh,mon.tenmonan,mon.giamgia,mon.dongia,mon.danhgia));
                monAnBanChayAdapter.notifyDataSetChanged();
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
    }



    @Override
    public void onItemClick(int position) {

        //Toast.makeText(getContext(),position + monAnModelList.get(position).link_hinh + monAnModelList.get(position).tenmonan+"" +monAnModelList.get(position).dongia,Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), DetailsOfFoodActivity.class);
        /*
        intent.putExtra("key",HomeActivity.monAnModelList.get(position).mamonan);
        intent.putExtra("position",position);
        intent.putExtra("hinhanh",HomeActivity.monAnModelList.get(position).link_hinh);
        intent.putExtra("ten",HomeActivity.monAnModelList.get(position).tenmonan);
        intent.putExtra("gia",HomeActivity.monAnModelList.get(position).dongia);
        intent.putExtra("yeuthich",HomeActivity.monAnModelList.get(position).yeuthich);
        intent.putExtra("cate",HomeActivity.monAnModelList.get(position).getCategory());
        intent.putExtra("giamgia",HomeActivity.monAnModelList.get(position).getGiamgia());
        intent.putExtra("danhgia",HomeActivity.monAnModelList.get(position).getDanhgia());

         */
        intent.putExtra("position",position);

        startActivity(intent);


    }
    @Override
    public void onItemClickcat(int position) {

        //Toast.makeText(getContext(),position + monAnModelList.get(position).link_hinh + monAnModelList.get(position).tenmonan+"" +monAnModelList.get(position).dongia,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra("category",categoryModelList.get(position).getMa_cate());
        intent.putExtra("cat_title",categoryModelList.get(position).getCat_title());

        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {

    }

    @Override
    public void onButtonclick(int id, int position) {
        int idlistgiohang=-1;

        switch (id){
            case R.id.btn_cong:
                boolean daco=false;


                for (int i=0; i< HomeActivity.listgiohang.size(); i++){
                    if(HomeActivity.monAnModelList.get(position).mamonan == HomeActivity.listgiohang.get(i).getIdsp()){
                        daco = true;
                        idlistgiohang = i;
                        break;
                    }
                }

                if(!daco){
                    HomeActivity.listgiohang.add(new GioHang(HomeActivity.monAnModelList.get(position).mamonan,HomeActivity.monAnModelList.get(position).tenmonan,HomeActivity.monAnModelList.get(position).dongia,HomeActivity.monAnModelList.get(position).link_hinh,1));


                }else {
                    if(idlistgiohang>=0){
                        int soluong;
                        soluong = HomeActivity.listgiohang.get(idlistgiohang).getSoluongsp() + 1;
                        HomeActivity.listgiohang.get(idlistgiohang).setSoluongsp(soluong);
                    }

                }

                break;
            case R.id.btn_tru:
                for (int i=0; i< HomeActivity.listgiohang.size(); i++){
                    if(HomeActivity.monAnModelList.get(position).mamonan == HomeActivity.listgiohang.get(i).getIdsp()){
                        idlistgiohang = i;
                        break;
                    }
                }
                if(idlistgiohang >= 0){
                    int soluong;
                    soluong = HomeActivity.listgiohang.get(idlistgiohang).getSoluongsp();
                    if (soluong>0){
                        soluong =soluong-1;
                        HomeActivity.listgiohang.get(idlistgiohang).setSoluongsp(soluong);
                    }else {
                        HomeActivity.listgiohang.remove(idlistgiohang);
                    }

                }
                break;
            default:
                break;
        }

        Fragment f = new GioHangFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_dh, f).commit();
    }


    /*
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        try{
            sendDataInterface=(SendDataInterface) activity;
        }catch (RuntimeException a){
            throw new RuntimeException(activity.toString()+"Must Implement Methord");
        }
    }

 */
}

