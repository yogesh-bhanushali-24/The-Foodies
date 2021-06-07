package com.example.casestudy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.casestudy.model.OrderModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class myorderFragment extends Fragment {

    RecyclerView ShowOrderRecycler;
    orderdisplayadapter orderadapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myorder, container, false);
        ShowOrderRecycler = view.findViewById(R.id.OrderRecycler);
        ShowOrderRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        String CurrentNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().replace("+91", "");

        FirebaseRecyclerOptions<OrderModel> options =
                new FirebaseRecyclerOptions.Builder<OrderModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Order").orderByChild("CustomerMobile").equalTo(CurrentNumber), OrderModel.class)
                        .build();


        orderadapter = new orderdisplayadapter(options);
        ShowOrderRecycler.setAdapter(orderadapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        orderadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        orderadapter.stopListening();
    }


}