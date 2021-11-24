package com.example.coearth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentList extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        final Button button_list = (Button) view.findViewById(R.id.list_btn);

        button_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StoreListActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
                Log.v("onclick","error");
            }
        });
        return view;
    }
}
