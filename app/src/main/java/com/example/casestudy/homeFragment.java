package com.example.casestudy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


public class homeFragment extends Fragment {


    private LinearLayout lBreakfast, lPunjabi, lGujarati, lSouthIndian, lItalian, lChinese, lColdDrinks, lHotDrinks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //onclick event on Horizontal food categories

        lBreakfast = view.findViewById(R.id.breakfast);
        lPunjabi = view.findViewById(R.id.punjabi);
        lGujarati = view.findViewById(R.id.gujarati);
        lSouthIndian = view.findViewById(R.id.southindian);
        lItalian = view.findViewById(R.id.italian);
        lChinese = view.findViewById(R.id.chinese);
        lColdDrinks = view.findViewById(R.id.colddrinks);
        lHotDrinks = view.findViewById(R.id.hotdrinks);


        lBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Breakfast is ready", Toast.LENGTH_SHORT).show();
            }
        });

        lPunjabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Punjabi Food  is ready", Toast.LENGTH_SHORT).show();
            }
        });

        lGujarati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Gujarati Food  is ready", Toast.LENGTH_SHORT).show();
            }
        });

        lSouthIndian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "SouthIndian Food is ready", Toast.LENGTH_SHORT).show();
            }
        });
        lItalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Italian Food is ready", Toast.LENGTH_SHORT).show();
            }
        });

        lChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Chinese Food is ready", Toast.LENGTH_SHORT).show();
            }
        });

        lColdDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "CoolDrink is ready", Toast.LENGTH_SHORT).show();
            }
        });

        lHotDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "HotDrink is ready ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }


}