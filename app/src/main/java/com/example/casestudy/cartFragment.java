package com.example.casestudy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.casestudy.model.CartModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class cartFragment extends Fragment {
    private RecyclerView cartDisplayRecycler;
    // FirebaseAuth auth;
    cartdisplayadapter cartdisplayadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        //recycler view
        cartDisplayRecycler = view.findViewById(R.id.cartRecycler);
        cartDisplayRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //end recycler view


        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()), CartModel.class)
                        .build();

        cartdisplayadapter = new cartdisplayadapter(options);
        cartDisplayRecycler.setAdapter(cartdisplayadapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cartdisplayadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cartdisplayadapter.stopListening();
    }


}