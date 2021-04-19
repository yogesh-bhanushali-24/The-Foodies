package com.example.casestudy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileFragment extends Fragment {

    private TextView UserNameTv, UserNumberTv, UserEmailTv;
    FirebaseDatabase userDb = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = userDb.getReference().child("UserDetail");
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        String phoneNumber = auth.getCurrentUser().getPhoneNumber();
        UserNumberTv = view.findViewById(R.id.tvUserNumber);
        UserNumberTv.setText(phoneNumber);


        return view;


    }


}