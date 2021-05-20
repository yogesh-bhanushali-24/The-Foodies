package com.example.casestudy;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.casestudy.model.CartModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class cartFragment extends Fragment {
    private RecyclerView cartDisplayRecycler;
    cartdisplayadapter cartdisplayadapter;
    private TextView GrandTotalTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        GrandTotalTv = view.findViewById(R.id.GrandTotal);


        //recycler view
        cartDisplayRecycler = view.findViewById(R.id.cartRecycler);
        cartDisplayRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //end recycler view

        //show cart view data
        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()), CartModel.class)
                        .build();

        cartdisplayadapter = new cartdisplayadapter(options);
        cartDisplayRecycler.setAdapter(cartdisplayadapter);
        //end show cart view data


        //total bill
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            int countTotal = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    String total = ds.child("total").getValue().toString();
                    int total1 = Integer.parseInt(total);
                    countTotal = countTotal + total1;
                }
                GrandTotalTv.setText(String.valueOf(countTotal) + "₹");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


        reference.addListenerForSingleValueEvent(valueEventListener);

        //end total bill


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