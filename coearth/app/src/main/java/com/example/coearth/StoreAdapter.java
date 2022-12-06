package com.example.coearth;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private Context context;
    private List<Store> storeList;
    private StoreAdapter storelistActivity;
    Bundle extras = new Bundle();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, category, detail, star, views;
        public ImageView image;
        public RatingBar ratingBar;

        public ViewHolder(@NonNull View v) {
            super(v);
            image = v.findViewById(R.id.image);
            title = v.findViewById(R.id.stName);
            category = v.findViewById(R.id.stCategory);
            detail = v.findViewById(R.id.stDetail);
            star = v.findViewById(R.id.star);
            views = v.findViewById(R.id.views);
            ratingBar = v.findViewById(R.id.ratingbar);
        }
    }

    //생성자
    public StoreAdapter(Context context, List<Store> list) {
        this.context = context;
        storeList = list;
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        return new StoreAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Store stores = storeList.get(position);
        /** 여기가 데이터베이스에서 이미지 받아와서 세팅하는 곳 **/
        String url = stores.getImg();
        Glide.with(holder.itemView.getContext()).load(url).into(holder.image);
        holder.image.setColorFilter(Color.parseColor("#6F000000"), PorterDuff.Mode.SRC_ATOP);
        holder.category.setText(stores.getCategory());
        holder.detail.setText(stores.getDetail());
        holder.title.setText(stores.getTitle());
        holder.star.setText(stores.getScore());
        holder.views.setText(stores.getViews());
        holder.ratingBar.setRating(Float.valueOf(stores.getScore()));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference(storeList.get(position).getTitle() + '/');
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("views", String.valueOf(Integer.parseInt(stores.getViews())+1));

                String title = stores.getTitle();
                String detail = stores.getDetail();
                String tel = stores.getTel();
                String category = stores.getCategory();
                String star = stores.getScore();
                String views = stores.getViews();

                extras.putString("title", title);
                extras.putString("detail", detail);
                extras.putString("category", category);
                extras.putString("tel", tel);
                extras.putString("star", star);
                extras.putString("views", views);

                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtras(extras);
                view.getContext().startActivity(intent);
            }
        });
    }
}