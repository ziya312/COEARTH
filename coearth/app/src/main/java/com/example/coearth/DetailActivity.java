package com.example.coearth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    Intent ref_intent;
    Button btn_write;
    ListView reviewList;
    int position;
    ArrayList<String> list;
    FragmentManager fragmentManager;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView Title = findViewById(R.id.tv_title);
        TextView Category = findViewById(R.id.tv_category);
        TextView Address = findViewById(R.id.tv_address);
        TextView Tel = findViewById(R.id.tv_tel);
        ImageView Img = findViewById(R.id.iv_store);
        scrollView = findViewById(R.id.sc_Detail);

        ref_intent = getIntent();
        String title = ref_intent.getStringExtra("title");
        String category = ref_intent.getStringExtra("category");
        String address = ref_intent.getStringExtra("address");
        String tel = ref_intent.getStringExtra("tel");
        String image = ref_intent.getStringExtra("image");

        position = ref_intent.getIntExtra("position", 0) + 1;

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(image)
                .into(Img);

        Title.setText(title);
        Category.setText(category);
        Address.setText(address);
        Tel.setText(tel);



        //리뷰쓰기
        btn_write = findViewById(R.id.writeButton);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, WriteReviewActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        list = new ArrayList<>();



    }
}