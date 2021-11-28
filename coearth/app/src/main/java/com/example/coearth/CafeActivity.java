package com.example.coearth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CafeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Store> storeList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference, caferef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // 성능강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        storeList = new ArrayList<>(); // Store 객체 담을 arraylist

        database = FirebaseDatabase.getInstance(); // 파이어베이스 DB 연동
        databaseReference = database.getReference("stores"); // DB 테이블 연결
        adapter = new StoreAdapter(storeList, this);
        recyclerView.setAdapter(adapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터 베이스의 데이터를 받아오는 곳
                storeList.clear(); // 기존 배열 리스트 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Store store = snapshot.getValue(Store.class);
                    Log.d("Store_category", store.getCategory());
                    storeList.add(store); // 데이터들을 배열리스트에 넣고 리사이클 뷰에 넣을 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
                Log.e("CafeActivity","Success DB");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB 가져오던 중 에러 발생 시
                Log.e("CafeActivity",String.valueOf(error.toException()));
            }
        });
    }
}
    /*
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_cafe, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        storeList = new ArrayList<>(); // User 객체를 담을 어레이 리스트 (어댑터쪽으로)

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("stores"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                storeList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Store store = snapshot.getValue(Store.class);
                    storeList.add(store);
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침해야 반영이 됨
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("CafeActivity",String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
        adapter = new StoreAdapter(storeList, getContext());
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        return view;
    }
}*/