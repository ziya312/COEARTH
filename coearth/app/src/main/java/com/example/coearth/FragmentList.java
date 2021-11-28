package com.example.coearth;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class FragmentList extends Fragment {
    private GridLayout gridLayout;
    private CardView cd_category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);

        gridLayout = view.findViewById(R.id.grid);
        setClickEvent(gridLayout);
        return view;
    }

    private void setClickEvent(GridLayout gridLayout){
        for (int i = 0; i< gridLayout.getChildCount(); i++){
            final CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int index = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(index == 0){
                        Intent intent = new Intent(getActivity(),CafeActivity.class);
                        startActivity(intent);
                    }
                    else if(index == 1){
                        Intent intent = new Intent(getActivity(),FoodActivity.class);
                        startActivity(intent);
                    }
                    else if(index == 2){
                        Intent intent = new Intent(getActivity(),LifeActivity.class);
                        startActivity(intent);
                    }
                    else if(index == 3){
                        Intent intent = new Intent(getActivity(),ClothActivity.class);
                        startActivity(intent);
                    }
                    else if(index == 4){
                        Intent intent = new Intent(getActivity(),EtcActivity.class);
                        startActivity(intent);
                    }
                }
            });

        }

    }
}
