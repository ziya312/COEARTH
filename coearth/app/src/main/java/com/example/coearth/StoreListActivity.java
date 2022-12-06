package com.example.coearth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StoreListActivity extends AppCompatActivity {
        private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        private DatabaseReference databaseReference = firebaseDatabase.getReference();
        private List<Store> storeList = new ArrayList<>();
        private StoreAdapter storeAdapter;
        private RecyclerView recyclerView;
        private String title;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.recycler_view);

            Intent intent = getIntent();
            title = intent.getStringExtra("title");     // 메인 페이지에서 item을 클릭하면 title값 intent로 넘어옴

            recyclerView = findViewById(R.id.recycler_view);
            storeAdapter = new StoreAdapter(this, storeList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(storeAdapter);
        }
}
