package com.example.casestudy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.casestudy.model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import static java.lang.String.CASE_INSENSITIVE_ORDER;


public class searchFragment extends Fragment {

    androidx.appcompat.widget.SearchView searchView;
    RecyclerView foodSearchRecycler;
    fooddisplayadapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.searchfoods);

        //recyclerview
        foodSearchRecycler = view.findViewById(R.id.SearchFoodShowRecycler);
        foodSearchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<FoodModel> options =
                new FirebaseRecyclerOptions.Builder<FoodModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food"), FoodModel.class)
                        .build();

        adapter = new fooddisplayadapter(options);
        foodSearchRecycler.setAdapter(adapter);

        //end


        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                processsearch(query);
                return false;
            }
        });


        return view;
    }

    private void processsearch(String query) {
        FirebaseRecyclerOptions<FoodModel> options =
                new FirebaseRecyclerOptions.Builder<FoodModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Food").orderByChild("name").startAt(query.toLowerCase().toUpperCase()).endAt(query + "\uf8ff"), FoodModel.class)
                        .build();

        adapter = new fooddisplayadapter(options);
        adapter.startListening();
        foodSearchRecycler.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}