package com.example.coearth;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EtcActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Store> storeList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference, caferef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

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
                    if(store.getCategory().equals("화장품") || store.getCategory().equals("주류") || store.getCategory().equals("꽃집")) {
                        storeList.add(store); // 데이터들을 배열리스트에 넣고 리사이클 뷰에 넣을 준비
                    }
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
                Log.e("EtcActivity","Success DB");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // DB 가져오던 중 에러 발생 시
                Log.e("EtcActivity",String.valueOf(error.toException()));
            }
        });
    }
}
