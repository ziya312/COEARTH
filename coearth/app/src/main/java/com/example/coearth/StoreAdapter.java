package com.example.coearth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {


    private ArrayList<Store> storeList;
    private Context context;


    public StoreAdapter(ArrayList<Store> storeList, Context context) {
        this.storeList = storeList;
        this.context = context;

    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        StoreViewHolder holder = new StoreViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        if("카페".equals(storeList.get(position).getCategory())) {
            Glide.with(holder.itemView)
                    .load(storeList.get(position).getImg())
                    .into(holder.iv_store);
            holder.tv_title.setText(storeList.get(position).getTitle());
            holder.tv_category.setText(storeList.get(position).getCategory());
            holder.tv_address.setText(storeList.get(position).getDetail());
            holder.tv_views.setText(storeList.get(position).getViews());
            holder.tv_star.setText(storeList.get(position).getScore());
        }

    }


    @Override
    public int getItemCount() {
        return (storeList != null ? storeList.size() : 0);
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_store;
        TextView tv_title, tv_category, tv_views, tv_address, tv_star;
        RatingBar iv_ratingbar;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iv_store = itemView.findViewById(R.id.iv_store);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_category = itemView.findViewById(R.id.tv_category);
            this.tv_views = itemView.findViewById(R.id.tv_views);
            this.tv_address = itemView.findViewById(R.id.tv_address);
            this.tv_star = itemView.findViewById(R.id.tv_star);
            this.iv_ratingbar = itemView.findViewById(R.id.iv_ratingbar);
        }
    }
}
