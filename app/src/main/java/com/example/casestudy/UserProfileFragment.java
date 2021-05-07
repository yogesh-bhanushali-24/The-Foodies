package com.example.casestudy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileFragment extends Fragment {


    private TextView UserNameTv, UserNumberTv, UserEmailTv;
    private MaterialButton UpdateDetailBtn, LogoutUserBtn;
    FirebaseDatabase userDb;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    //ProgressDialog progressDialog=new ProgressDialog(getActivity().getApplicationContext());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);


        UserNumberTv = view.findViewById(R.id.tvUserNumber);
        UserEmailTv = view.findViewById(R.id.tvUserEmail);
        UserNameTv = view.findViewById(R.id.tvUserName);
        UpdateDetailBtn = view.findViewById(R.id.UpdateExistDetail);
        LogoutUserBtn = view.findViewById(R.id.UserLogout);
        //  progressDialog.setTitle("Updating");
        //progressDialog.setMessage("Please wait while data is update.");


        userDb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        String Userid = auth.getCurrentUser().getUid();
        databaseReference = userDb.getReference().child("UserDetail").child(Userid);

        //Show Login User Detail
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Email = snapshot.child("email").getValue().toString();
                String Name = snapshot.child("name").getValue().toString();
                String Mobile = snapshot.child("mobile").getValue().toString();
                UserEmailTv.setText(Email);
                UserNameTv.setText(Name);
                UserNumberTv.setText(Mobile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        UpdateDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //progressDialog.show();

                String updatedEmail = UserEmailTv.getText().toString();
                String updatedName = UserNameTv.getText().toString();
                String updatedNumber = UserNumberTv.getText().toString();


                if (!updatedEmail.isEmpty() || !updatedName.isEmpty() || !updatedNumber.isEmpty()) {
                    databaseReference.child("email").setValue(updatedEmail);
                    databaseReference.child("name").setValue(updatedName);
                    databaseReference.child("mobile").setValue(updatedNumber);
                    UpdateDetailBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    Toast.makeText(getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Empty Field Not Valid", Toast.LENGTH_SHORT).show();
                }

                // progressDialog.dismiss();

            }
        });

        LogoutUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are your sure want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        auth.signOut();
                        Intent intent = new Intent(getContext(), SplashScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();


            }
        });


        return view;


    }


}