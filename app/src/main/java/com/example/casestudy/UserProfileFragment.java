package com.example.casestudy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileFragment extends Fragment {

    String Email, Name;
    private TextView UserNameTv, UserNumberTv, UserEmailTv;
    // FirebaseDatabase userDb = FirebaseDatabase.getInstance();
    //DatabaseReference databaseReference = userDb.getReference().child("UserDetail");
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);


        UserNumberTv = view.findViewById(R.id.tvUserNumber);
        UserEmailTv = view.findViewById(R.id.tvUserEmail);
        UserNameTv = view.findViewById(R.id.tvUserName);

        user = auth.getCurrentUser();
        String phoneNumber = auth.getCurrentUser().getPhoneNumber();
        UserNumberTv.setText(phoneNumber);

        reference = FirebaseDatabase.getInstance().getReference().child("UserDetail").child(user.getUid());


//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot)
//            {
//                Name=snapshot.child("name").getValue().toString();
//                Email=snapshot.child("email").getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        UserNameTv.setText(Name);
//        UserEmailTv.setText(Email);


        return view;


    }


}