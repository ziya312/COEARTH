package com.example.coearth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView=findViewById(R.id.bottom_navigation);

        //첫 화면 띄우기
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,new FragmentMap()).commit();

        //case 함수를 통해 클릭 받을 때마다 화면 변경하기
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_map :
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentMap()).commit();
                        break;
                    case R.id.action_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentList()).commit();
                        break;
                    case R.id.action_mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FragmentMy()).commit();
                        break;

                }
                return true;
            }
        });
    }
}