package com.example.casestudy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.util.Locale;


public class homeFragment extends Fragment {


    private LinearLayout lBreakfast, lPunjabi, lGujarati, lSouthIndian, lItalian, lChinese, lColdDrinks, lHotDrinks;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Layout
        linearLayout = view.findViewById(R.id.homeFragmentLayout);

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

                String CategoryName = "breakfast";
                Intent intent = new Intent(getContext(), CategoriesViewActivity.class);
                intent.putExtra("Category", CategoryName);
                startActivity(intent);

            }
        });

        lPunjabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Punjabi Food  is ready", Toast.LENGTH_SHORT).show();
                String CategoryName = "punjabi";
                Intent intent = new Intent(getContext(), CategoriesViewActivity.class);
                intent.putExtra("Category", CategoryName);
                startActivity(intent);
            }
        });

        lGujarati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Gujarati Food  is ready", Toast.LENGTH_SHORT).show();
                String CategoryName = "gujarati";
                Intent intent = new Intent(getContext(), CategoriesViewActivity.class);
                intent.putExtra("Category", CategoryName);
                startActivity(intent);
            }
        });

        lSouthIndian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "SouthIndian Food is ready", Toast.LENGTH_SHORT).show();
                String CategoryName = "southindian";
                Intent intent = new Intent(getContext(), CategoriesViewActivity.class);
                intent.putExtra("Category", CategoryName);
                startActivity(intent);
            }
        });
        lItalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Italian Food is ready", Toast.LENGTH_SHORT).show();
                String CategoryName = "italian";
                Intent intent = new Intent(getContext(), CategoriesViewActivity.class);
                intent.putExtra("Category", CategoryName);
                startActivity(intent);
            }
        });

        lChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Chinese Food is ready", Toast.LENGTH_SHORT).show();
                String CategoryName = "chinese";
                Intent intent = new Intent(getContext(), CategoriesViewActivity.class);
                intent.putExtra("Category", CategoryName);
                startActivity(intent);
            }
        });

        lColdDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "CoolDrink is ready", Toast.LENGTH_SHORT).show();
                String CategoryName = "cooldrink";
                Intent intent = new Intent(getContext(), CategoriesViewActivity.class);
                intent.putExtra("Category", CategoryName);
                startActivity(intent);
            }
        });

        lHotDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "HotDrink is ready ", Toast.LENGTH_SHORT).show();
                String CategoryName = "hotdrink";
                Intent intent = new Intent(getContext(), CategoriesViewActivity.class);
                intent.putExtra("Category", CategoryName);
                startActivity(intent);
            }
        });


        //Snackbar

        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                "Order Via Call Click here ", Snackbar.LENGTH_LONG).setAction("Click To Call", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9726036668"));
                startActivity(intent);
            }
        }).setDuration(3000);
        snackBar.show();


        return view;

    }


}