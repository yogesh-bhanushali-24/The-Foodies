package com.example.casestudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.casestudy.model.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FirstUserDetail extends AppCompatActivity {

    EditText etName, etNumber, etEmail;
    MaterialButton SubDetail;
    FirebaseDatabase userDb = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = userDb.getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference checkUser = userDb.getReference().child("UserDetail");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_user_detail);
        getSupportActionBar().hide();

        auth.addAuthStateListener(authStateListener);


//      the Existing user value Focus false

        etName = findViewById(R.id.Username);
        etNumber = findViewById(R.id.existingNumber);
        etEmail = findViewById(R.id.UserEmail);
        SubDetail = findViewById(R.id.btnGoOn);
//        e1.setText("9726036668");

        String mobile = getIntent().getStringExtra("mobile");
        etNumber.setText(mobile);

        SubDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NamePattern = "^[a-zA-Z ]*$";
                // String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";

                if (etName.getText().toString().trim().isEmpty()) {
                    etName.setError("Enter Your Name");
                    return;

                }
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Enter Your Email");
                    return;
                } else if (!etName.getText().toString().isEmpty() && !etEmail.getText().toString().isEmpty()) {
                    if (etName.getText().toString().trim().matches(NamePattern) &&
                            Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                        String uName = etName.getText().toString();
                        String uNumber = etNumber.getText().toString();
                        String uEmail = etEmail.getText().toString();
                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("Name", uName);
                        userMap.put("Mobile", uNumber);
                        userMap.put("Email", uEmail);
//                        databaseReference.push().setValue(userMap);
                        UserModel userModel = new UserModel(uName, uNumber, uEmail);
                        databaseReference.child("UserDetail").child(auth.getUid()).setValue(userModel);

                        Intent intent = new Intent(FirstUserDetail.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(FirstUserDetail.this, "Invalid", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }


    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth auth) {

            String checkNumber = auth.getCurrentUser().getPhoneNumber();
            checkUser.orderByChild("mobile").equalTo(checkNumber).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    String existName=snapshot.child("name").getValue().toString();
//                    String existEmail=snapshot.child("email").getValue().toString();
//                    etName.setText(existName);
//                    etEmail.setText(existEmail);
                    Intent intent = new Intent(FirstUserDetail.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(FirstUserDetail.this, "Register your self", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };


}